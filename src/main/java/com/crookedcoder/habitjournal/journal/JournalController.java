package com.crookedcoder.habitjournal.journal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crookedcoder.habitjournal.journal.entries.Entry;
import com.crookedcoder.habitjournal.journal.habits.Habit;
import com.crookedcoder.habitjournal.journal.milestones.Milestone;
import com.crookedcoder.habitjournal.service.JournalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
@Slf4j
public class JournalController {

    private final JournalService journalService;

    /**
     * Create a new journal for a user
     * POST /api/journals?username={username}
     */
    @PostMapping
    public ResponseEntity<Journal> createJournal(@RequestParam String username) {
        log.info("Creating new journal for user: {}", username);
        Journal journal = journalService.createJournal(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(journal);
    }

    /**
     * Get journal by ID
     * GET /api/journals/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable String id) {
        log.info("Fetching journal by ID: {}", id);
        Journal journal = journalService.getJournalById(id);
        return ResponseEntity.ok(journal);
    }

    /**
     * Get journal by username
     * GET /api/journals/user/{username}
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<Journal> getJournalByUsername(@PathVariable String username) {
        log.info("Fetching journal by username: {}", username);
        Journal journal = journalService.getJournalByUsername(username);
        return ResponseEntity.ok(journal);
    }

    /**
     * Get all habits in journal
     * GET /api/journals/{id}/habits
     */
    @GetMapping("/{id}/habits")
    public ResponseEntity<List<Habit>> getJournalHabits(@PathVariable String id) {
        log.info("Fetching all habits for journal: {}", id);
        List<Habit> habits = journalService.getAllHabits(id);
        return ResponseEntity.ok(habits);
    }

    /**
     * Get all entries in journal
     * GET /api/journals/{id}/entries
     */
    @GetMapping("/{id}/entries")
    public ResponseEntity<List<Entry>> getJournalEntries(@PathVariable String id) {
        log.info("Fetching all entries for journal: {}", id);
        List<Entry> entries = journalService.getAllEntries(id);
        return ResponseEntity.ok(entries);
    }

    /**
     * Get all milestones in journal
     * GET /api/journals/{id}/milestones
     */
    @GetMapping("/{id}/milestones")
    public ResponseEntity<List<Milestone>> getJournalMilestones(@PathVariable String id) {
        log.info("Fetching all milestones for journal: {}", id);
        List<Milestone> milestones = journalService.getAllMilestones(id);
        return ResponseEntity.ok(milestones);
    }

    /**
     * Delete journal
     * DELETE /api/journals/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournal(@PathVariable String id) {
        log.info("Deleting journal: {}", id);
        journalService.deleteJournal(id);
        return ResponseEntity.noContent().build();
    }
}
