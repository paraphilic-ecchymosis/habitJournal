package com.crookedcoder.habitjournal.journal.milestones;

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

import com.crookedcoder.habitjournal.service.MilestoneService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/milestones")
@RequiredArgsConstructor
@Slf4j
public class MilestonesController {

    private final MilestoneService milestoneService;

    /**
     * Create a new milestone
     * POST /api/milestones
     */
    @PostMapping
    public ResponseEntity<Milestone> createMilestone(@Valid @RequestBody Milestone milestone) {
        log.info("Creating new milestone: {}", milestone.getName());
        Milestone createdMilestone = milestoneService.createMilestone(milestone);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMilestone);
    }

    /**
     * Get milestone by ID
     * GET /api/milestones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Milestone> getMilestoneById(@PathVariable String id) {
        log.info("Fetching milestone by ID: {}", id);
        Milestone milestone = milestoneService.getMilestoneById(id);
        return ResponseEntity.ok(milestone);
    }

    /**
     * Get all milestones (optionally filter by journal, habit, or status)
     * GET /api/milestones?journalId={journalId}&habitId={habitId}&status={status}
     */
    @GetMapping
    public ResponseEntity<List<Milestone>> getMilestones(
            @RequestParam(required = false) String journalId,
            @RequestParam(required = false) String habitId,
            @RequestParam(required = false) MilestoneStatus status) {

        log.info("Fetching milestones - journalId: {}, habitId: {}, status: {}",
                journalId, habitId, status);

        List<Milestone> milestones;

        if (habitId != null) {
            milestones = milestoneService.getMilestonesByHabitId(habitId);
        } else if (journalId != null && status != null) {
            milestones = milestoneService.getMilestonesByStatus(journalId, status);
        } else if (journalId != null) {
            milestones = milestoneService.getMilestonesByJournalId(journalId);
        } else {
            milestones = milestoneService.getAllMilestones();
        }

        return ResponseEntity.ok(milestones);
    }

    /**
     * Update milestone
     * PUT /api/milestones/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Milestone> updateMilestone(@PathVariable String id, @Valid @RequestBody Milestone milestone) {
        log.info("Updating milestone: {}", id);
        Milestone updatedMilestone = milestoneService.updateMilestone(id, milestone);
        return ResponseEntity.ok(updatedMilestone);
    }

    /**
     * Increment milestone progress
     * PATCH /api/milestones/{id}/increment
     */
    @PatchMapping("/{id}/increment")
    public ResponseEntity<Milestone> incrementProgress(@PathVariable String id) {
        log.info("Incrementing progress for milestone: {}", id);
        Milestone milestone = milestoneService.incrementProgress(id);
        return ResponseEntity.ok(milestone);
    }

    /**
     * Update milestone status
     * PATCH /api/milestones/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Milestone> updateStatus(@PathVariable String id, @RequestBody MilestoneStatus status) {
        log.info("Updating status for milestone: {} to {}", id, status);
        Milestone milestone = milestoneService.updateStatus(id, status);
        return ResponseEntity.ok(milestone);
    }

    /**
     * Delete milestone
     * DELETE /api/milestones/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable String id) {
        log.info("Deleting milestone: {}", id);
        milestoneService.deleteMilestone(id);
        return ResponseEntity.noContent().build();
    }
}
