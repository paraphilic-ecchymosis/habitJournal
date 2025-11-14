package com.crookedcoder.habitjournal.service;

import com.crookedcoder.habitjournal.dto.AIInsightResponse;

/**
 * Service for generating AI-powered insights about user habits.
 */
public interface AIInsightService {

    /**
     * Generate insights for a specific habit based on user's entry history.
     */
    AIInsightResponse generateHabitInsight(String habitId);

    /**
     * Generate overall insights across all user habits.
     */
    AIInsightResponse generateOverallInsights(String userId);

    /**
     * Analyze completion patterns and suggest improvements.
     */
    AIInsightResponse analyzeCompletionPatterns(String habitId);
}
