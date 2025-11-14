package com.crookedcoder.habitjournal.journal.entries;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crookedcoder.habitjournal.service.EntryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/entries")
@RequiredArgsConstructor
@Slf4j
public class EntriesController {

    private final EntryService entryService;

    /**
     * Create a new entry
     * POST /api/entries
     */
    @PostMapping
    public ResponseEntity<Entry> createEntry(@Valid @RequestBody Entry entry) {
        log.info("Creating new entry for journal: {}", entry.getJournalId());
        Entry createdEntry = entryService.createEntry(entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
    }

    /**
     * Get entry by ID
     * GET /api/entries/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable String id) {
        log.info("Fetching entry by ID: {}", id);
        Entry entry = entryService.getEntryById(id);
        return ResponseEntity.ok(entry);
    }

    /**
     * Get all entries (optionally filter by journal or habit)
     * GET /api/entries?journalId={journalId}&habitId={habitId}&startDate={startDate}&endDate={endDate}
     */
    @GetMapping
    public ResponseEntity<List<Entry>> getEntries(
            @RequestParam(required = false) String journalId,
            @RequestParam(required = false) String habitId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        log.info("Fetching entries - journalId: {}, habitId: {}, startDate: {}, endDate: {}",
                journalId, habitId, startDate, endDate);

        List<Entry> entries;

        if (habitId != null) {
            entries = entryService.getEntriesByHabitId(habitId);
        } else if (journalId != null && startDate != null && endDate != null) {
            entries = entryService.getEntriesByDateRange(journalId, startDate, endDate);
        } else if (journalId != null) {
            entries = entryService.getEntriesByJournalId(journalId);
        } else {
            entries = entryService.getAllEntries();
        }

        return ResponseEntity.ok(entries);
    }

    /**
     * Update entry
     * PUT /api/entries/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable String id, @Valid @RequestBody Entry entry) {
        log.info("Updating entry: {}", id);
        Entry updatedEntry = entryService.updateEntry(id, entry);
        return ResponseEntity.ok(updatedEntry);
    }

    /**
     * Delete entry
     * DELETE /api/entries/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
        log.info("Deleting entry: {}", id);
        entryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete multiple entries
     * DELETE /api/entries/bulk?ids=id1,id2,id3
     */
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteEntries(@RequestParam List<String> ids) {
        log.info("Deleting {} entries", ids.size());
        entryService.deleteEntries(ids);
        return ResponseEntity.noContent().build();
    }
}
