package com.crookedcoder.habitjournal.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crookedcoder.habitjournal.exception.DuplicateResourceException;
import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.journal.Journal;
import com.crookedcoder.habitjournal.journal.JournalRepository;
import com.crookedcoder.habitjournal.journal.entries.Entry;
import com.crookedcoder.habitjournal.journal.habits.Habit;
import com.crookedcoder.habitjournal.journal.milestones.Milestone;
import com.crookedcoder.habitjournal.service.JournalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;

    @Override
    public Journal createJournal(String username) {
        log.info("Creating new journal for user: {}", username);

        // Check if journal already exists for this user
        if (journalRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Journal", "username", username);
        }

        Journal journal = new Journal(username);
        Journal savedJournal = journalRepository.save(journal);
        log.info("Journal created successfully: {}", savedJournal.getId());
        return savedJournal;
    }

    @Override
    @Transactional(readOnly = true)
    public Journal getJournalByUsername(String username) {
        return journalRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Journal", "username", username));
    }

    @Override
    @Transactional(readOnly = true)
    public Journal getJournalById(String id) {
        return journalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal", "id", id));
    }

    @Override
    public Journal addHabitToJournal(String journalId, Habit habit) {
        Journal journal = getJournalById(journalId);
        journal.addHabit(habit.getId());
        Journal saved = journalRepository.save(journal);
        log.info("Habit added to journal: {} -> {}", habit.getId(), journal.getId());
        return saved;
    }

    @Override
    public Journal addEntryToJournal(String journalId, Entry entry) {
        Journal journal = getJournalById(journalId);
        journal.addEntry(entry.getId());
        Journal saved = journalRepository.save(journal);
        log.info("Entry added to journal: {} -> {}", entry.getId(), journal.getId());
        return saved;
    }

    @Override
    public Journal addMilestoneToJournal(String journalId, Milestone milestone) {
        Journal journal = getJournalById(journalId);
        journal.addMilestone(milestone.getId());
        Journal saved = journalRepository.save(journal);
        log.info("Milestone added to journal: {} -> {}", milestone.getId(), journal.getId());
        return saved;
    }

    @Override
    public Journal removeHabitFromJournal(String journalId, String habitId) {
        Journal journal = getJournalById(journalId);
        journal.deleteHabit(habitId);
        Journal saved = journalRepository.save(journal);
        log.info("Habit removed from journal: {} <- {}", habitId, journal.getId());
        return saved;
    }

    @Override
    public Journal removeEntryFromJournal(String journalId, String entryId) {
        Journal journal = getJournalById(journalId);
        journal.deleteEntry(entryId);
        Journal saved = journalRepository.save(journal);
        log.info("Entry removed from journal: {} <- {}", entryId, journal.getId());
        return saved;
    }

    @Override
    public Journal removeMilestoneFromJournal(String journalId, String milestoneId) {
        Journal journal = getJournalById(journalId);
        journal.deleteMilestone(milestoneId);
        Journal saved = journalRepository.save(journal);
        log.info("Milestone removed from journal: {} <- {}", milestoneId, journal.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entry> getAllEntries(String journalId) {
        Journal journal = getJournalById(journalId);
        return journal.getEntries();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habit> getAllHabits(String journalId) {
        Journal journal = getJournalById(journalId);
        return journal.getHabits();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Milestone> getAllMilestones(String journalId) {
        Journal journal = getJournalById(journalId);
        return journal.getMilestones();
    }

    @Override
    public void deleteJournal(String id) {
        Journal journal = getJournalById(id);
        journalRepository.delete(journal);
        log.info("Journal deleted successfully: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return journalRepository.existsByUsername(username);
    }
}
