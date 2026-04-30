package com.analyzer;

/**
 * Data Transfer Object (DTO) to hold results for JSON conversion.
 */
public class AnalysisResult {
    public String risk;
    public int confidence;
    public String explanation;

    public AnalysisResult(String risk, int confidence, String explanation) {
        this.risk = risk;
        this.confidence = confidence;
        this.explanation = explanation;
    }

    // Manual JSON serialization for simplicity
    public String toJson() {
        return String.format(
                "{\"risk\": \"%s\", \"confidence\": %d, \"explanation\": \"%s\"}",
                risk, confidence, explanation);
    }
}
