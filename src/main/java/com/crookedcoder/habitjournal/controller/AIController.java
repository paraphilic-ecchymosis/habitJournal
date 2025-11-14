package com.crookedcoder.habitjournal.controller;

import com.crookedcoder.habitjournal.dto.AICoachingResponse;
import com.crookedcoder.habitjournal.dto.AIInsightResponse;
import com.crookedcoder.habitjournal.service.AICoachingService;
import com.crookedcoder.habitjournal.service.AIInsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for AI-powered insights and coaching.
 */
@RestController
@RequestMapping("/api/ai")
@PreAuthorize("isAuthenticated()")
public class AIController {

    private final AIInsightService insightService;
    private final AICoachingService coachingService;

    public AIController(AIInsightService insightService, AICoachingService coachingService) {
        this.insightService = insightService;
        this.coachingService = coachingService;
    }

    /**
     * GET /api/ai/insights/habit/{habitId}
     * Generate AI insights for a specific habit.
     */
    @GetMapping("/insights/habit/{habitId}")
    public ResponseEntity<AIInsightResponse> getHabitInsights(@PathVariable String habitId) {
        AIInsightResponse insights = insightService.generateHabitInsight(habitId);
        return ResponseEntity.ok(insights);
    }

    /**
     * GET /api/ai/insights/overall
     * Generate overall insights across all user habits.
     */
    @GetMapping("/insights/overall")
    public ResponseEntity<AIInsightResponse> getOverallInsights() {
        // In a real app, get userId from authenticated user
        AIInsightResponse insights = insightService.generateOverallInsights("current-user");
        return ResponseEntity.ok(insights);
    }

    /**
     * GET /api/ai/insights/patterns/{habitId}
     * Analyze completion patterns for a habit.
     */
    @GetMapping("/insights/patterns/{habitId}")
    public ResponseEntity<AIInsightResponse> analyzePatterns(@PathVariable String habitId) {
        AIInsightResponse insights = insightService.analyzeCompletionPatterns(habitId);
        return ResponseEntity.ok(insights);
    }

    /**
     * GET /api/ai/coaching/habit/{habitId}
     * Get personalized coaching for a specific habit.
     */
    @GetMapping("/coaching/habit/{habitId}")
    public ResponseEntity<AICoachingResponse> getCoaching(@PathVariable String habitId) {
        AICoachingResponse coaching = coachingService.generateCoaching(habitId);
        return ResponseEntity.ok(coaching);
    }

    /**
     * GET /api/ai/coaching/motivation/{habitId}
     * Get motivational coaching when struggling.
     */
    @GetMapping("/coaching/motivation/{habitId}")
    public ResponseEntity<AICoachingResponse> getMotivation(@PathVariable String habitId) {
        AICoachingResponse motivation = coachingService.generateMotivation(habitId);
        return ResponseEntity.ok(motivation);
    }

    /**
     * GET /api/ai/coaching/next-steps
     * Get suggestions for next steps in habit improvement.
     */
    @GetMapping("/coaching/next-steps")
    public ResponseEntity<AICoachingResponse> getNextSteps() {
        // In a real app, get userId from authenticated user
        AICoachingResponse nextSteps = coachingService.suggestNextSteps("current-user");
        return ResponseEntity.ok(nextSteps);
    }
}
