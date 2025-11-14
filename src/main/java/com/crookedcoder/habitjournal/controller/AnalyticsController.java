package com.crookedcoder.habitjournal.controller;

import com.crookedcoder.habitjournal.dto.AnalyticsResponse;
import com.crookedcoder.habitjournal.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for analytics and statistics.
 */
@RestController
@RequestMapping("/api/analytics")
@PreAuthorize("isAuthenticated()")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * GET /api/analytics
     * Get comprehensive analytics for the user.
     */
    @GetMapping
    public ResponseEntity<AnalyticsResponse> getAnalytics() {
        AnalyticsResponse analytics = analyticsService.getAnalytics();
        return ResponseEntity.ok(analytics);
    }

    /**
     * GET /api/analytics/streak/{habitId}
     * Get streak information for a specific habit.
     */
    @GetMapping("/streak/{habitId}")
    public ResponseEntity<AnalyticsResponse.StreakInfo> getHabitStreak(@PathVariable String habitId) {
        AnalyticsResponse.StreakInfo streak = analyticsService.getHabitStreak(habitId);
        return ResponseEntity.ok(streak);
    }

    /**
     * GET /api/analytics/completion/{habitId}
     * Get completion rate for a specific habit.
     */
    @GetMapping("/completion/{habitId}")
    public ResponseEntity<AnalyticsResponse.CompletionRate> getCompletionRate(@PathVariable String habitId) {
        AnalyticsResponse.CompletionRate rate = analyticsService.getHabitCompletionRate(habitId);
        return ResponseEntity.ok(rate);
    }
}
