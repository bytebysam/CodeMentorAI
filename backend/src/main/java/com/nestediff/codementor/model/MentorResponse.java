package com.nestediff.codementor.model;
public record MentorResponse(String status,String concept,String severity,String hint,String explanation,Integer score,String nextAction) {}