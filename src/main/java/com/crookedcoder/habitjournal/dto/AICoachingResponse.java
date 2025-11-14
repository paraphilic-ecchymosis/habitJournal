package com.crookedcoder.habitjournal.dto;

/**
 * Response DTO for AI-generated coaching advice.
 */
public record AICoachingResponse(
    String advice,
    String motivationalQuote,
    String actionableStep
) {}
