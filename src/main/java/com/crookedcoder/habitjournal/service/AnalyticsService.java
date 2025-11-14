package com.crookedcoder.habitjournal.service;

import com.crookedcoder.habitjournal.dto.AnalyticsResponse;

/**
 * Service for generating analytics and statistics.
 */
public interface AnalyticsService {

    /**
     * Get comprehensive analytics for the user.
     */
    AnalyticsResponse getAnalytics();

    /**
     * Get analytics for a specific habit.
     */
    AnalyticsResponse.StreakInfo getHabitStreak(String habitId);

    /**
     * Get completion rate for a specific habit.
     */
    AnalyticsResponse.CompletionRate getHabitCompletionRate(String habitId);
}
