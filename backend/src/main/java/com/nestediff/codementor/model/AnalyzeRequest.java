package com.nestediff.codementor.model;
public record AnalyzeRequest(Integer problemId,String language,String code,Integer attemptNumber) {}