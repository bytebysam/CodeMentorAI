package com.nestediff.codementor.service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.Schema;
import com.nestediff.codementor.model.MentorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
    private final Client client;
    private final String model;
    private final ObjectMapper mapper = new ObjectMapper();

    public GeminiService(@Value("${GEMINI_API_KEY:}") String apiKey,
                         @Value("${gemini.model:gemini-3.6-flash}") String model) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY is not set.");
        }
        this.client = Client.builder().apiKey(apiKey).build();
        this.model = model;
    }

    public MentorResponse analyze(String problem, String code, int attempt) {
        String prompt = """
        You are CodeMentor AI, a patient Java programming tutor.
        Analyze the student's solution without simply giving the corrected code.
        Encourage independent problem solving. If incorrect, give one progressive hint.
        If correct, explain briefly why it is correct.
        Return ONLY JSON matching the requested schema.

        Problem:
        %s

        Student Java code:
        ```java
        %s
        ```

        Attempt number: %d
        """.formatted(problem, code, attempt);

        GenerateContentConfig config = GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .responseSchema(Schema.fromJson("""
            {
              "type":"object",
              "properties":{
                "status":{"type":"string","enum":["CORRECT","NEEDS_IMPROVEMENT"]},
                "concept":{"type":"string"},
                "severity":{"type":"string","enum":["LOW","MEDIUM","HIGH"]},
                "hint":{"type":"string"},
                "explanation":{"type":"string"},
                "score":{"type":"integer","minimum":0,"maximum":10},
                "nextAction":{"type":"string","enum":["TRY_AGAIN","MASTERED"]}
              },
              "required":["status","concept","severity","hint","explanation","score","nextAction"]
            }
            """)).build();

        GenerateContentResponse response = client.models.generateContent(
            model, Content.fromParts(Part.fromText(prompt)), config);

        try {
            return mapper.readValue(response.text(), MentorResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("Could not parse Gemini JSON response: " + response.text(), e);
        }
    }

    public String hint(String problem, String code, int attempt) {
        String prompt = """
        You are CodeMentor AI, a Java programming tutor.
        Give exactly ONE progressive hint for this student.
        Do not provide final code or the complete solution.
        Problem: %s
        Student code:
        ```java
        %s
        ```
        Attempt number: %d
        Return only the hint text.
        """.formatted(problem, code, attempt);

        GenerateContentResponse response = client.models.generateContent(
            model, Content.fromParts(Part.fromText(prompt)), null);
        return response.text();
    }
}