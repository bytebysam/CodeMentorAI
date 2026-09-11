# Nestediff CodeMentor AI — Gemini Integrated Backend

Java/Spring Boot backend for the CodeMentor AI hackathon MVP. This version is prepared for **JDK 25** and includes the real Google GenAI Java SDK integration.

## Requirements
- JDK 25 (tested target: Java 25.x)
- Internet connection
- Gemini API key
- IntelliJ IDEA recommended

## Java version
The project targets Java 25 and uses Spring Boot 3.5.16.

Check Java:

    java --version

## Gemini API key
Set the key as an environment variable. Do NOT put the key in source code or GitHub.

Windows PowerShell:

    $env:GEMINI_API_KEY="YOUR_REAL_KEY"

Windows Command Prompt:

    set GEMINI_API_KEY=YOUR_REAL_KEY

The environment variable must be set in the same terminal/session used to start the backend.

## Run without installing Maven
This ZIP includes `mvnw.cmd`, a small Windows bootstrap script. If Maven is not already installed, it downloads Maven 3.9.9 into your user profile and then runs Maven.

From this folder in Command Prompt:

    mvnw.cmd spring-boot:run

The first run can take a few minutes while Maven downloads dependencies.

Alternatively, open the folder in IntelliJ IDEA as a Maven project and let IntelliJ import the dependencies.

## Backend URL

    http://localhost:8080

Health check:

    http://localhost:8080/api/health

Mentor endpoints:

    POST /api/mentor/analyze
    POST /api/mentor/hint

## Frontend CORS
The backend allows the CodeMentor frontend on:
- http://localhost:5174
- http://127.0.0.1:5174
- http://localhost:5173
- http://127.0.0.1:5173

## Architecture

React/Vite → Spring Boot → Gemini API → structured JSON → React

## Important
- Never commit `GEMINI_API_KEY` to GitHub.
- Gemini analyzes Java code; this MVP does not execute untrusted student Java code on the server.
- The Gemini model can be changed in `src/main/resources/application.properties`.
