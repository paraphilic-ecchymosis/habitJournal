package com.crookedcoder.habitjournal.dto;

import java.util.List;

/**
 * Response DTO for analytics data.
 */
public record AnalyticsResponse(
    HabitStats habitStats,
    List<StreakInfo> streaks,
    List<CompletionRate> completionRates,
    ProgressSummary progressSummary
) {
    public record HabitStats(
        int totalHabits,
        int activeHabits,
        int totalEntries,
        int completedMilestones
    ) {}

    public record StreakInfo(
        String habitId,
        String habitName,
        int currentStreak,
        int longestStreak,
        String lastEntryDate
    ) {}

    public record CompletionRate(
        String habitId,
        String habitName,
        double rate,
        int totalEntries,
        int completedEntries
    ) {}

    public record ProgressSummary(
        double overallCompletionRate,
        int totalDaysTracked,
        String mostConsistentHabit,
        String needsAttention
    ) {}
}
