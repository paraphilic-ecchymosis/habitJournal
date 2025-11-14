package com.crookedcoder.habitjournal.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.journal.entries.EntriesRepository;
import com.crookedcoder.habitjournal.journal.entries.Entry;
import com.crookedcoder.habitjournal.service.EntryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EntryServiceImpl implements EntryService {

    private final EntriesRepository entriesRepository;

    @Override
    public Entry createEntry(Entry entry) {
        log.info("Creating new entry for journal: {}", entry.getJournalId());

        // Set timestamp if not provided
        if (entry.getTimestamp() == null) {
            entry.setTimestamp(LocalDateTime.now());
        }

        Entry savedEntry = entriesRepository.save(entry);
        log.info("Entry created successfully: {}", savedEntry.getId());
        return savedEntry;
    }

    @Override
    @Transactional(readOnly = true)
    public Entry getEntryById(String id) {
        return entriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entry", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> getEntriesByJournalId(String journalId) {
        return entriesRepository.findAll().stream()
                .filter(entry -> entry.getJournalId().equals(journalId))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> getEntriesByHabitId(String habitId) {
        return entriesRepository.findByHabitId(habitId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> getEntriesByDateRange(String journalId, LocalDateTime startDate, LocalDateTime endDate) {
        return entriesRepository.findAll().stream()
                .filter(entry -> entry.getJournalId().equals(journalId))
                .filter(entry -> !entry.getTimestamp().isBefore(startDate) && !entry.getTimestamp().isAfter(endDate))
                .toList();
    }

    @Override
    public Entry updateEntry(String id, Entry updatedEntry) {
        Entry existingEntry = getEntryById(id);

        // Update fields
        if (updatedEntry.getBody() != null) {
            existingEntry.setBody(updatedEntry.getBody());
        }
        if (updatedEntry.getHabitId() != null) {
            existingEntry.setHabitId(updatedEntry.getHabitId());
        }
        if (updatedEntry.getTimestamp() != null) {
            existingEntry.setTimestamp(updatedEntry.getTimestamp());
        }

        Entry saved = entriesRepository.save(existingEntry);
        log.info("Entry updated successfully: {}", saved.getId());
        return saved;
    }

    @Override
    public void deleteEntry(String id) {
        Entry entry = getEntryById(id);
        entriesRepository.delete(entry);
        log.info("Entry deleted successfully: {}", id);
    }

    @Override
    public void deleteEntries(List<String> ids) {
        log.info("Deleting {} entries", ids.size());
        entriesRepository.deleteAllById(ids);
        log.info("Entries deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> getAllEntries() {
        return entriesRepository.findAll();
    }
}
