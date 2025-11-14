package com.crookedcoder.habitjournal.service;

import com.crookedcoder.habitjournal.dto.AICoachingResponse;

/**
 * Service for generating personalized AI coaching advice.
 */
public interface AICoachingService {

    /**
     * Generate personalized coaching for a specific habit.
     */
    AICoachingResponse generateCoaching(String habitId);

    /**
     * Provide motivation when user is struggling with consistency.
     */
    AICoachingResponse generateMotivation(String habitId);

    /**
     * Suggest next steps for habit improvement.
     */
    AICoachingResponse suggestNextSteps(String userId);
}
