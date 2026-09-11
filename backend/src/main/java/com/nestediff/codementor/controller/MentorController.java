package com.nestediff.codementor.controller;

import com.nestediff.codementor.model.*;
import com.nestediff.codementor.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/mentor")
@CrossOrigin(origins={"http://localhost:5174","http://127.0.0.1:5174","http://localhost:5173","http://127.0.0.1:5173"})
public class MentorController {
 private final GeminiService service;
 public MentorController(GeminiService service){this.service=service;}

 @PostMapping("/analyze")
 public ResponseEntity<MentorResponse> analyze(@RequestBody AnalyzeRequest r){
  return ResponseEntity.ok(service.analyze(problemText(r.problemId()),r.code(),attempt(r.attemptNumber())));
 }
 @PostMapping("/hint")
 public ResponseEntity<Map<String,String>> hint(@RequestBody HintRequest r){
  return ResponseEntity.ok(Map.of("hint",service.hint(problemText(r.problemId()),r.code(),attempt(r.attemptNumber()))));
 }
 private int attempt(Integer a){return a==null||a<1?1:a;}
 private String problemText(Integer id){
  return switch(id==null?1:id){
   case 2 -> "Find the largest number in an integer array.";
   case 3 -> "Check whether a String is a palindrome.";
   case 4 -> "Count vowels in a String.";
   case 5 -> "Solve the Two Sum problem.";
   default -> "Write a Java program to reverse a given String without using StringBuilder.reverse().";
  };
 }
}