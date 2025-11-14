package com.crookedcoder.habitjournal.service;

import java.time.LocalDateTime;
import java.util.List;

import com.crookedcoder.habitjournal.journal.entries.Entry;

public interface EntryService {

    /**
     * Create a new entry
     */
    Entry createEntry(Entry entry);

    /**
     * Get entry by ID
     */
    Entry getEntryById(String id);

    /**
     * Get all entries for a journal
     */
    List<Entry> getEntriesByJournalId(String journalId);

    /**
     * Get all entries for a habit
     */
    List<Entry> getEntriesByHabitId(String habitId);

    /**
     * Get entries by date range
     */
    List<Entry> getEntriesByDateRange(String journalId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Update entry
     */
    Entry updateEntry(String id, Entry entry);

    /**
     * Delete entry
     */
    void deleteEntry(String id);

    /**
     * Delete multiple entries
     */
    void deleteEntries(List<String> ids);

    /**
     * Get all entries (admin)
     */
    List<Entry> getAllEntries();
}
