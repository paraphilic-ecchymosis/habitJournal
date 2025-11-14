package com.crookedcoder.habitjournal.service.impl;

import com.crookedcoder.habitjournal.dto.AIInsightResponse;
import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.model.Entry;
import com.crookedcoder.habitjournal.model.Habit;
import com.crookedcoder.habitjournal.repository.EntriesRepository;
import com.crookedcoder.habitjournal.repository.HabitRepository;
import com.crookedcoder.habitjournal.service.AIInsightService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of AI Insight Service using Spring AI.
 */
@Service
public class AIInsightServiceImpl implements AIInsightService {

    private final ChatClient chatClient;
    private final HabitRepository habitRepository;
    private final EntriesRepository entriesRepository;

    public AIInsightServiceImpl(
            ChatClient chatClient,
            HabitRepository habitRepository,
            EntriesRepository entriesRepository) {
        this.chatClient = chatClient;
        this.habitRepository = habitRepository;
        this.entriesRepository = entriesRepository;
    }

    @Override
    @Async("virtualThreadExecutor")
    public AIInsightResponse generateHabitInsight(String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        List<Entry> entries = entriesRepository.findByHabitId(habitId);

        String prompt = buildHabitAnalysisPrompt(habit, entries);

        String insight = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return new AIInsightResponse(insight, "HABIT_ANALYSIS");
    }

    @Override
    @Async("virtualThreadExecutor")
    public AIInsightResponse generateOverallInsights(String userId) {
        // For now, we'll analyze all habits (could be filtered by user)
        List<Habit> habits = habitRepository.findAll();
        List<Entry> entries = entriesRepository.findAll();

        String prompt = buildOverallAnalysisPrompt(habits, entries);

        String insight = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return new AIInsightResponse(insight, "OVERALL_ANALYSIS");
    }

    @Override
    @Async("virtualThreadExecutor")
    public AIInsightResponse analyzeCompletionPatterns(String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        List<Entry> entries = entriesRepository.findByHabitId(habitId);

        String prompt = buildPatternAnalysisPrompt(habit, entries);

        String insight = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return new AIInsightResponse(insight, "PATTERN_ANALYSIS");
    }

    private String buildHabitAnalysisPrompt(Habit habit, List<Entry> entries) {
        long completedCount = entries.stream().filter(Entry::isCompleted).count();
        double completionRate = entries.isEmpty() ? 0 : (double) completedCount / entries.size() * 100;

        int currentStreak = calculateCurrentStreak(entries);

        return String.format("""
            Analyze this habit and provide a concise insight:

            Habit: %s
            Description: %s
            Frequency: %s
            Total Entries: %d
            Completed: %d (%.1f%%)
            Current Streak: %d days

            Provide a brief, actionable insight about their progress.
            """,
            habit.getName(),
            habit.getDescription(),
            habit.getFrequency(),
            entries.size(),
            completedCount,
            completionRate,
            currentStreak
        );
    }

    private String buildOverallAnalysisPrompt(List<Habit> habits, List<Entry> entries) {
        return String.format("""
            Analyze the user's overall habit tracking performance:

            Total Habits: %d
            Total Entries: %d

            Provide a brief insight about their overall commitment and suggest one area for improvement.
            """,
            habits.size(),
            entries.size()
        );
    }

    private String buildPatternAnalysisPrompt(Habit habit, List<Entry> entries) {
        long recentCompleted = entries.stream()
                .filter(e -> e.getEntryDate() != null &&
                       e.getEntryDate().isAfter(LocalDate.now().minusDays(7)))
                .filter(Entry::isCompleted)
                .count();

        return String.format("""
            Analyze completion patterns for this habit:

            Habit: %s
            Entries in last 7 days: %d

            Identify any patterns and suggest how to maintain or improve consistency.
            """,
            habit.getName(),
            recentCompleted
        );
    }

    private int calculateCurrentStreak(List<Entry> entries) {
        if (entries.isEmpty()) return 0;

        List<Entry> sortedEntries = entries.stream()
                .filter(Entry::isCompleted)
                .filter(e -> e.getEntryDate() != null)
                .sorted((a, b) -> b.getEntryDate().compareTo(a.getEntryDate()))
                .toList();

        if (sortedEntries.isEmpty()) return 0;

        int streak = 1;
        LocalDate previousDate = sortedEntries.get(0).getEntryDate();

        for (int i = 1; i < sortedEntries.size(); i++) {
            LocalDate currentDate = sortedEntries.get(i).getEntryDate();
            long daysDifference = ChronoUnit.DAYS.between(currentDate, previousDate);

            if (daysDifference == 1) {
                streak++;
                previousDate = currentDate;
            } else {
                break;
            }
        }

        return streak;
    }
}
