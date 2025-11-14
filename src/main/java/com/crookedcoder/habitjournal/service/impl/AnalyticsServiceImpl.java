package com.crookedcoder.habitjournal.service.impl;

import com.crookedcoder.habitjournal.dto.AnalyticsResponse;
import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.model.Entry;
import com.crookedcoder.habitjournal.model.Habit;
import com.crookedcoder.habitjournal.model.Milestone;
import com.crookedcoder.habitjournal.model.MilestoneStatus;
import com.crookedcoder.habitjournal.repository.EntriesRepository;
import com.crookedcoder.habitjournal.repository.HabitRepository;
import com.crookedcoder.habitjournal.repository.MilestoneRepository;
import com.crookedcoder.habitjournal.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of Analytics Service.
 */
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final HabitRepository habitRepository;
    private final EntriesRepository entriesRepository;
    private final MilestoneRepository milestoneRepository;

    public AnalyticsServiceImpl(
            HabitRepository habitRepository,
            EntriesRepository entriesRepository,
            MilestoneRepository milestoneRepository) {
        this.habitRepository = habitRepository;
        this.entriesRepository = entriesRepository;
        this.milestoneRepository = milestoneRepository;
    }

    @Override
    public AnalyticsResponse getAnalytics() {
        List<Habit> allHabits = habitRepository.findAll();
        List<Entry> allEntries = entriesRepository.findAll();
        List<Milestone> allMilestones = milestoneRepository.findAll();

        // Calculate habit stats
        int totalHabits = allHabits.size();
        int activeHabits = (int) allHabits.stream()
                .filter(h -> hasRecentActivity(h.getId()))
                .count();
        int totalEntries = allEntries.size();
        int completedMilestones = (int) allMilestones.stream()
                .filter(m -> m.getStatus() == MilestoneStatus.COMPLETED)
                .count();

        AnalyticsResponse.HabitStats habitStats = new AnalyticsResponse.HabitStats(
                totalHabits, activeHabits, totalEntries, completedMilestones
        );

        // Calculate streaks for all habits
        List<AnalyticsResponse.StreakInfo> streaks = allHabits.stream()
                .map(habit -> getHabitStreak(habit.getId()))
                .collect(Collectors.toList());

        // Calculate completion rates
        List<AnalyticsResponse.CompletionRate> completionRates = allHabits.stream()
                .map(habit -> getHabitCompletionRate(habit.getId()))
                .collect(Collectors.toList());

        // Calculate progress summary
        AnalyticsResponse.ProgressSummary progressSummary = calculateProgressSummary(
                allHabits, allEntries, completionRates
        );

        return new AnalyticsResponse(habitStats, streaks, completionRates, progressSummary);
    }

    @Override
    public AnalyticsResponse.StreakInfo getHabitStreak(String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        List<Entry> entries = entriesRepository.findByHabitId(habitId);

        List<Entry> completedEntries = entries.stream()
                .filter(Entry::isCompleted)
                .filter(e -> e.getEntryDate() != null)
                .sorted((a, b) -> b.getEntryDate().compareTo(a.getEntryDate()))
                .toList();

        int currentStreak = calculateCurrentStreak(completedEntries);
        int longestStreak = calculateLongestStreak(completedEntries);
        String lastEntryDate = completedEntries.isEmpty() ? "Never" :
                completedEntries.get(0).getEntryDate().toString();

        return new AnalyticsResponse.StreakInfo(
                habitId,
                habit.getName(),
                currentStreak,
                longestStreak,
                lastEntryDate
        );
    }

    @Override
    public AnalyticsResponse.CompletionRate getHabitCompletionRate(String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        List<Entry> entries = entriesRepository.findByHabitId(habitId);

        int totalEntries = entries.size();
        int completedEntries = (int) entries.stream()
                .filter(Entry::isCompleted)
                .count();

        double rate = totalEntries == 0 ? 0.0 :
                (double) completedEntries / totalEntries * 100;

        return new AnalyticsResponse.CompletionRate(
                habitId,
                habit.getName(),
                Math.round(rate * 100.0) / 100.0, // Round to 2 decimal places
                totalEntries,
                completedEntries
        );
    }

    private boolean hasRecentActivity(String habitId) {
        List<Entry> entries = entriesRepository.findByHabitId(habitId);
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);

        return entries.stream()
                .anyMatch(e -> e.getEntryDate() != null &&
                        e.getEntryDate().isAfter(sevenDaysAgo));
    }

    private int calculateCurrentStreak(List<Entry> sortedCompletedEntries) {
        if (sortedCompletedEntries.isEmpty()) return 0;

        int streak = 1;
        LocalDate previousDate = sortedCompletedEntries.get(0).getEntryDate();

        // Check if the most recent entry was today or yesterday
        long daysSinceLastEntry = ChronoUnit.DAYS.between(previousDate, LocalDate.now());
        if (daysSinceLastEntry > 1) {
            return 0; // Streak is broken
        }

        for (int i = 1; i < sortedCompletedEntries.size(); i++) {
            LocalDate currentDate = sortedCompletedEntries.get(i).getEntryDate();
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

    private int calculateLongestStreak(List<Entry> sortedCompletedEntries) {
        if (sortedCompletedEntries.isEmpty()) return 0;

        int longestStreak = 1;
        int currentStreak = 1;
        LocalDate previousDate = sortedCompletedEntries.get(0).getEntryDate();

        for (int i = 1; i < sortedCompletedEntries.size(); i++) {
            LocalDate currentDate = sortedCompletedEntries.get(i).getEntryDate();
            long daysDifference = ChronoUnit.DAYS.between(currentDate, previousDate);

            if (daysDifference == 1) {
                currentStreak++;
                longestStreak = Math.max(longestStreak, currentStreak);
            } else {
                currentStreak = 1;
            }
            previousDate = currentDate;
        }

        return longestStreak;
    }

    private AnalyticsResponse.ProgressSummary calculateProgressSummary(
            List<Habit> habits,
            List<Entry> entries,
            List<AnalyticsResponse.CompletionRate> completionRates) {

        // Calculate overall completion rate
        int totalEntries = entries.size();
        int completedEntries = (int) entries.stream()
                .filter(Entry::isCompleted)
                .count();

        double overallCompletionRate = totalEntries == 0 ? 0.0 :
                (double) completedEntries / totalEntries * 100;

        // Calculate total days tracked
        Set<LocalDate> uniqueDates = entries.stream()
                .map(Entry::getEntryDate)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        int totalDaysTracked = uniqueDates.size();

        // Find most consistent habit
        String mostConsistent = completionRates.stream()
                .filter(cr -> cr.totalEntries() >= 3) // At least 3 entries
                .max(Comparator.comparingDouble(AnalyticsResponse.CompletionRate::rate))
                .map(AnalyticsResponse.CompletionRate::habitName)
                .orElse("None yet");

        // Find habit that needs attention
        String needsAttention = completionRates.stream()
                .filter(cr -> cr.totalEntries() >= 3)
                .min(Comparator.comparingDouble(AnalyticsResponse.CompletionRate::rate))
                .map(AnalyticsResponse.CompletionRate::habitName)
                .orElse("None");

        return new AnalyticsResponse.ProgressSummary(
                Math.round(overallCompletionRate * 100.0) / 100.0,
                totalDaysTracked,
                mostConsistent,
                needsAttention
        );
    }
}
