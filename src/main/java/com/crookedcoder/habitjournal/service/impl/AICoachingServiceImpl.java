package com.crookedcoder.habitjournal.service.impl;

import com.crookedcoder.habitjournal.dto.AICoachingResponse;
import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.model.Entry;
import com.crookedcoder.habitjournal.model.Habit;
import com.crookedcoder.habitjournal.repository.EntriesRepository;
import com.crookedcoder.habitjournal.repository.HabitRepository;
import com.crookedcoder.habitjournal.service.AICoachingService;
import com.crookedcoder.habitjournal.service.PersonaFrameworkClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of AI Coaching Service using Persona-Framework.
 */
@Service
public class AICoachingServiceImpl implements AICoachingService {

    private final PersonaFrameworkClient personaFrameworkClient;
    private final HabitRepository habitRepository;
    private final EntriesRepository entriesRepository;

    public AICoachingServiceImpl(
            PersonaFrameworkClient personaFrameworkClient,
            HabitRepository habitRepository,
            EntriesRepository entriesRepository) {
        this.personaFrameworkClient = personaFrameworkClient;
        this.habitRepository = habitRepository;
        this.entriesRepository = entriesRepository;
    }

    @Override
    @Async("virtualThreadExecutor")
    public AICoachingResponse generateCoaching(String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        List<Entry> entries = entriesRepository.findByHabitId(habitId);

        String userPrompt = buildCoachingPrompt(habit, entries);
        String systemPrompt = buildSystemPrompt();

        String response = personaFrameworkClient.sendPrompt(systemPrompt, userPrompt);

        return parseCoachingResponse(response);
    }

    @Override
    @Async("virtualThreadExecutor")
    public AICoachingResponse generateMotivation(String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        List<Entry> entries = entriesRepository.findByHabitId(habitId);

        String userPrompt = buildMotivationPrompt(habit, entries);
        String systemPrompt = buildSystemPrompt();

        String response = personaFrameworkClient.sendPrompt(systemPrompt, userPrompt);

        return parseCoachingResponse(response);
    }

    @Override
    @Async("virtualThreadExecutor")
    public AICoachingResponse suggestNextSteps(String userId) {
        List<Habit> habits = habitRepository.findAll();
        List<Entry> entries = entriesRepository.findAll();

        String userPrompt = buildNextStepsPrompt(habits, entries);
        String systemPrompt = buildSystemPrompt();

        String response = personaFrameworkClient.sendPrompt(systemPrompt, userPrompt);

        return parseCoachingResponse(response);
    }

    private String buildCoachingPrompt(Habit habit, List<Entry> entries) {
        long completedCount = entries.stream().filter(Entry::isCompleted).count();
        double completionRate = entries.isEmpty() ? 0 : (double) completedCount / entries.size() * 100;

        return String.format("""
            Provide personalized coaching for this habit:

            Habit: %s
            Frequency: %s
            Completion Rate: %.1f%%
            Total Entries: %d

            Format your response as:
            ADVICE: [one specific actionable advice]
            QUOTE: [a relevant motivational quote]
            STEP: [one concrete next step they should take]
            """,
            habit.getName(),
            habit.getFrequency(),
            completionRate,
            entries.size()
        );
    }

    private String buildMotivationPrompt(Habit habit, List<Entry> entries) {
        long recentEntries = entries.stream()
                .filter(e -> e.getEntryDate() != null &&
                       e.getEntryDate().isAfter(LocalDate.now().minusDays(7)))
                .count();

        return String.format("""
            Provide encouragement for someone struggling with this habit:

            Habit: %s
            Recent entries (last 7 days): %d

            Format your response as:
            ADVICE: [empathetic and encouraging advice]
            QUOTE: [an inspiring quote about perseverance]
            STEP: [one small, achievable step to restart]
            """,
            habit.getName(),
            recentEntries
        );
    }

    private String buildNextStepsPrompt(List<Habit> habits, List<Entry> entries) {
        return String.format("""
            Suggest next steps for habit improvement:

            Total Habits: %d
            Total Entries: %d

            Format your response as:
            ADVICE: [general advice for improving habit consistency]
            QUOTE: [motivational quote about growth]
            STEP: [specific next action to take]
            """,
            habits.size(),
            entries.size()
        );
    }

    private String buildSystemPrompt() {
        return """
            You are an expert habit coach and behavioral psychologist provided by the Persona-Framework.
            Your role is to provide personalized coaching and motivation for habit formation.
            Provide supportive, actionable, and encouraging guidance.
            Keep responses structured and concise.
            """;
    }

    private AICoachingResponse parseCoachingResponse(String response) {
        String advice = "";
        String quote = "";
        String step = "";

        String[] lines = response.split("\n");
        for (String line : lines) {
            if (line.startsWith("ADVICE:")) {
                advice = line.substring(7).trim();
            } else if (line.startsWith("QUOTE:")) {
                quote = line.substring(6).trim();
            } else if (line.startsWith("STEP:")) {
                step = line.substring(5).trim();
            }
        }

        // Fallback if parsing fails
        if (advice.isEmpty()) {
            advice = response;
        }
        if (quote.isEmpty()) {
            quote = "The secret of getting ahead is getting started. - Mark Twain";
        }
        if (step.isEmpty()) {
            step = "Focus on completing your habit once today.";
        }

        return new AICoachingResponse(advice, quote, step);
    }
}
