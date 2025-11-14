package com.crookedcoder.habitjournal.service;

import java.util.List;

import com.crookedcoder.habitjournal.journal.Journal;
import com.crookedcoder.habitjournal.journal.entries.Entry;
import com.crookedcoder.habitjournal.journal.habits.Habit;
import com.crookedcoder.habitjournal.journal.milestones.Milestone;

public interface JournalService {

    /**
     * Create a new journal for a user
     */
    Journal createJournal(String username);

    /**
     * Get journal by username
     */
    Journal getJournalByUsername(String username);

    /**
     * Get journal by ID
     */
    Journal getJournalById(String id);

    /**
     * Add habit to journal
     */
    Journal addHabitToJournal(String journalId, Habit habit);

    /**
     * Add entry to journal
     */
    Journal addEntryToJournal(String journalId, Entry entry);

    /**
     * Add milestone to journal
     */
    Journal addMilestoneToJournal(String journalId, Milestone milestone);

    /**
     * Remove habit from journal
     */
    Journal removeHabitFromJournal(String journalId, String habitId);

    /**
     * Remove entry from journal
     */
    Journal removeEntryFromJournal(String journalId, String entryId);

    /**
     * Remove milestone from journal
     */
    Journal removeMilestoneFromJournal(String journalId, String milestoneId);

    /**
     * Get all entries in journal
     */
    List<Entry> getAllEntries(String journalId);

    /**
     * Get all habits in journal
     */
    List<Habit> getAllHabits(String journalId);

    /**
     * Get all milestones in journal
     */
    List<Milestone> getAllMilestones(String journalId);

    /**
     * Delete journal
     */
    void deleteJournal(String id);

    /**
     * Check if journal exists for user
     */
    boolean existsByUsername(String username);
}
