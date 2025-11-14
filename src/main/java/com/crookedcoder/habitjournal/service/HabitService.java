package com.crookedcoder.habitjournal.service;

import java.util.List;

import com.crookedcoder.habitjournal.journal.habits.Habit;

public interface HabitService {

    /**
     * Create a new habit
     */
    Habit createHabit(Habit habit);

    /**
     * Get habit by ID
     */
    Habit getHabitById(String id);

    /**
     * Get all habits for a journal
     */
    List<Habit> getHabitsByJournalId(String journalId);

    /**
     * Get all active habits for a journal
     */
    List<Habit> getActiveHabitsByJournalId(String journalId);

    /**
     * Get all recurring habits for a journal
     */
    List<Habit> getRecurringHabitsByJournalId(String journalId);

    /**
     * Update habit
     */
    Habit updateHabit(String id, Habit habit);

    /**
     * Toggle habit active status
     */
    Habit toggleHabitActive(String id);

    /**
     * Delete habit
     */
    void deleteHabit(String id);

    /**
     * Get all habits (admin)
     */
    List<Habit> getAllHabits();

    /**
     * Check if habit exists by name
     */
    boolean existsByName(String name);
}
