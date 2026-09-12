# CodeMentor AI -- Personalized Coding Learning Assistant

**Team:** Nightbuild\
**Primary Language:** Java

## 1. Project Overview

CodeMentor AI is an AI-powered coding learning assistant designed to
help beginner programmers understand and fix their own coding mistakes.

Instead of simply providing a complete solution, CodeMentor AI uses
Google Gemini to analyze a student's code, identify problems, explain
the underlying concept, provide progressive hints, and give
learning-oriented feedback.

### Core Learning Flow

``` text
Choose Problem → Write Java Code → Analyze with Gemini → Receive Feedback
                         ↓
                 Get Progressive Hints
                         ↓
                    Fix the Code
                         ↓
                 Review Score/Feedback
                         ↓
                 Practice Similar Problems
```

The key educational idea is **progressive hints**: guide the student
toward the solution instead of immediately giving the answer.

## 2. Problem Statement

Beginner programmers often struggle to understand why their code is
incorrect. Generic AI tools can provide complete solutions without
helping students understand their mistakes.

CodeMentor AI aims to make AI-assisted learning more interactive by
helping students:

-   Understand errors in their own code
-   Learn the concept behind the error
-   Receive hints progressively
-   Improve problem-solving ability
-   Practice similar coding problems
-   Learn from mistakes rather than simply copying solutions

## 3. Key Features

### AI Code Analysis

Students submit Java code and CodeMentor AI uses Gemini to provide:

-   Error identification
-   Explanation of the problem
-   Conceptual guidance
-   Feedback on the submitted solution
-   Score/assessment

### Progressive AI Hints

Instead of revealing the complete solution immediately, the AI mentor
provides increasingly specific hints.

Example:

``` text
Hint 1 → Think about the loop condition.
Hint 2 → Check the valid range of the index.
Hint 3 → Compare the loop condition with String.length().
Then → Explanation of the issue.
```

### Problem Library

Coding problems are stored in MySQL and loaded by the backend API.

Each problem can contain:

-   Title
-   Difficulty
-   Concept
-   Description

### AI Mentor

The AI Mentor provides contextual feedback based on:

-   Selected problem
-   Programming language
-   Student's submitted code
-   Current hint/attempt level

### Personalized Practice

The application is designed to generate practice problems based on areas
where the student needs improvement.

## 4. Gemini API Integration

Google Gemini is the core AI engine of CodeMentor AI.

The backend uses Google's **GenAI Java SDK** and invokes Gemini's
`generateContent` capability.

This capability powers different AI-assisted workflows, including:

-   Code analysis
-   Error explanation
-   Progressive hints
-   Learning feedback
-   Personalized practice generation

The same Gemini generation capability can produce different results
depending on the prompt and contextual information provided by the
backend.

### API Key Security

The Gemini API key is kept on the backend/server side.

**Never place the Gemini API key in the React frontend or commit it to
GitHub.**

The application reads the key from:

``` text
GEMINI_API_KEY
```

## 5. System Architecture

``` text
┌───────────────────────────────┐
│        React Frontend         │
│                               │
│  Problem List                 │
│  Monaco Code Editor           │
│  AI Mentor Panel              │
│  Hints / Feedback / Results   │
└───────────────┬───────────────┘
                │ REST API
                ▼
┌───────────────────────────────┐
│      Spring Boot Backend      │
│                               │
│  Problem APIs                 │
│  Mentor APIs                  │
│  Gemini Integration           │
│  Business Logic               │
└───────────┬───────────┬───────┘
            │           │
            │           │ Google GenAI SDK
            │           ▼
            │    ┌──────────────────┐
            │    │   Google Gemini  │
            │    │   generateContent│
            │    └──────────────────┘
            │
            ▼
┌───────────────────────────────┐
│            MySQL              │
│       Coding Problems         │
└───────────────────────────────┘
```

## 6. Technology Stack

  Layer                Technology
  -------------------- -----------------------
  Frontend             React
  Build Tool           Vite
  Code Editor          Monaco Editor
  HTTP Client          Axios
  Backend              Java + Spring Boot
  AI                   Google Gemini API
  Gemini Integration   Google GenAI Java SDK
  Database             MySQL
  API Style            REST
  Version Control      Git + GitHub

## 7. Project Structure

``` text
CodeMentorAI/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   ├── pom.xml
│   └── ...
├── frontend/
│   ├── src/
│   ├── package.json
│   ├── vite.config.js
│   └── ...
├── .gitignore
└── README.md
```

## 8. Backend APIs

### Health Check

``` http
GET /api/health
```

### Problem Library

``` http
GET /api/problems
```

Returns coding problems stored in MySQL.

### AI Code Analysis

``` http
POST /api/mentor/analyze
```

Sends the selected problem and student's code to the backend for
Gemini-powered analysis.

### AI Hint

``` http
POST /api/mentor/hint
```

Requests a progressive hint based on the problem, student's code, and
current attempt/hint level.

## 9. Prerequisites

Install:

-   Java JDK 25
-   Maven / Maven Wrapper
-   Node.js and npm
-   MySQL
-   Git

A Google Gemini API key is required for AI functionality.

## 10. Database Setup

Create the MySQL database:

``` sql
CREATE DATABASE codementorai;
```

Create/configure the required problem table according to the backend
entity configuration.

Problem data is stored in MySQL and retrieved through the Spring Boot
backend.

## 11. Backend Configuration

The Gemini API key should be provided through an environment variable.

### Windows PowerShell

``` powershell
[Environment]::SetEnvironmentVariable(
    "GEMINI_API_KEY",
    "YOUR_GEMINI_API_KEY",
    "User"
)
```

Verify it in a new PowerShell session:

``` powershell
$env:GEMINI_API_KEY
```

**Never put the real API key in this README or in GitHub.**

## 12. Run the Backend

Open a terminal in `backend`:

``` powershell
.\mvnw.cmd spring-boot:run
```

The backend runs on:

``` text
http://localhost:8080
```

Test:

``` text
http://localhost:8080/api/health
```

Expected response:

``` json
{
  "status": "OK",
  "application": "Nestediff CodeMentor AI"
}
```

## 13. Run the Frontend

Open another terminal in `frontend`:

``` powershell
npm install
npm run dev
```

Vite will display the local development URL.

The frontend communicates with:

``` text
http://localhost:8080/api
```

## 14. Example User Journey

1.  Student selects a Java coding problem.
2.  Problem details are displayed.
3.  Student writes code using Monaco Editor.
4.  Student selects **Analyze Code**.
5.  Backend sends the relevant context to Gemini.
6.  Gemini analyzes the submitted code.
7.  Frontend displays AI analysis, explanation, and score.
8.  Student requests a hint.
9.  Gemini generates a progressive hint.
10. Student fixes the code.
11. Student can request another hint if required.
12. The frontend updates the hint section with the latest AI response.
13. Student continues practicing.

## 15. Example Problem

### Reverse a String

A student may submit code containing an indexing error such as an
incorrect loop boundary.

CodeMentor AI can guide the student progressively:

``` text
Analysis:
The loop condition may allow an invalid index.

Hint 1:
Check the valid index range of a Java String.

Hint 2:
Compare your loop condition with String.length().

Hint 3:
Remember that the last valid index is one less than the length.

Explanation:
String indexes start at 0, so accessing index String.length()
would be outside the valid range.
```

The objective is to help the student understand and fix the problem
themselves.

## 16. Security

-   Gemini API key is stored as an environment variable.
-   API keys are excluded from Git using `.gitignore`.
-   API keys must never be committed to GitHub.
-   The frontend does not contain the Gemini API key.
-   AI requests are routed through the Spring Boot backend.

## 17. Git Workflow

The main development branch is:

``` text
main
```

For future changes:

``` powershell
git status
git add .
git commit -m "Describe the change"
git push
```

## 18. Future Enhancements

Possible enhancements include:

-   Additional programming languages
-   Detailed student progress tracking
-   Weak-concept detection
-   Difficulty adaptation
-   Expanded personalized practice
-   Automated test-case generation
-   Authentication and individual student profiles
-   Secure code execution/sandboxing

## 19. Hackathon Value

CodeMentor AI demonstrates a meaningful use of Gemini beyond a
general-purpose chatbot.

Gemini is used as an interactive learning engine to:

-   Understand student code
-   Explain mistakes
-   Provide progressive guidance
-   Give contextual feedback
-   Support personalized learning

### Core Differentiator

> **Don't just give the student the answer --- help the student learn
> how to find it.**

## 20. Team

**Nestediff**

CodeMentor AI is a collaborative hackathon project combining frontend
development, backend/API development, database integration, and
Gemini-powered AI functionality.
