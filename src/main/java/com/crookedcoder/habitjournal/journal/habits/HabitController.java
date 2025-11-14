package com.crookedcoder.habitjournal.journal.habits;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crookedcoder.habitjournal.service.HabitService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
@Slf4j
public class HabitController {

    private final HabitService habitService;

    /**
     * Create a new habit
     * POST /api/habits
     */
    @PostMapping
    public ResponseEntity<Habit> createHabit(@Valid @RequestBody Habit habit) {
        log.info("Creating new habit: {}", habit.getName());
        Habit createdHabit = habitService.createHabit(habit);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHabit);
    }

    /**
     * Get habit by ID
     * GET /api/habits/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Habit> getHabitById(@PathVariable String id) {
        log.info("Fetching habit by ID: {}", id);
        Habit habit = habitService.getHabitById(id);
        return ResponseEntity.ok(habit);
    }

    /**
     * Get all habits (optionally filter by journal)
     * GET /api/habits?journalId={journalId}&activeOnly={true/false}&recurringOnly={true/false}
     */
    @GetMapping
    public ResponseEntity<List<Habit>> getHabits(
            @RequestParam(required = false) String journalId,
            @RequestParam(required = false, defaultValue = "false") boolean activeOnly,
            @RequestParam(required = false, defaultValue = "false") boolean recurringOnly) {

        log.info("Fetching habits - journalId: {}, activeOnly: {}, recurringOnly: {}",
                journalId, activeOnly, recurringOnly);

        List<Habit> habits;

        if (journalId != null) {
            if (activeOnly) {
                habits = habitService.getActiveHabitsByJournalId(journalId);
            } else if (recurringOnly) {
                habits = habitService.getRecurringHabitsByJournalId(journalId);
            } else {
                habits = habitService.getHabitsByJournalId(journalId);
            }
        } else {
            habits = habitService.getAllHabits();
        }

        return ResponseEntity.ok(habits);
    }

    /**
     * Update habit
     * PUT /api/habits/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable String id, @Valid @RequestBody Habit habit) {
        log.info("Updating habit: {}", id);
        Habit updatedHabit = habitService.updateHabit(id, habit);
        return ResponseEntity.ok(updatedHabit);
    }

    /**
     * Toggle habit active status
     * PATCH /api/habits/{id}/toggle-active
     */
    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<Habit> toggleHabitActive(@PathVariable String id) {
        log.info("Toggling active status for habit: {}", id);
        Habit habit = habitService.toggleHabitActive(id);
        return ResponseEntity.ok(habit);
    }

    /**
     * Delete habit
     * DELETE /api/habits/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable String id) {
        log.info("Deleting habit: {}", id);
        habitService.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }
}
