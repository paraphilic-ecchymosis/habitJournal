package com.crookedcoder.habitjournal.dto;

/**
 * Response DTO for AI-generated insights about user habits.
 */
public record AIInsightResponse(
    String insight,
    String category,
    double confidenceScore
) {
    public AIInsightResponse(String insight, String category) {
        this(insight, category, 0.85);
    }
}
