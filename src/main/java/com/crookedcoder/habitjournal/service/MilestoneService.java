package com.crookedcoder.habitjournal.service;

import java.util.List;

import com.crookedcoder.habitjournal.journal.milestones.Milestone;
import com.crookedcoder.habitjournal.journal.milestones.MilestoneStatus;

public interface MilestoneService {

    /**
     * Create a new milestone
     */
    Milestone createMilestone(Milestone milestone);

    /**
     * Get milestone by ID
     */
    Milestone getMilestoneById(String id);

    /**
     * Get all milestones for a journal
     */
    List<Milestone> getMilestonesByJournalId(String journalId);

    /**
     * Get all milestones for a habit
     */
    List<Milestone> getMilestonesByHabitId(String habitId);

    /**
     * Get milestones by status
     */
    List<Milestone> getMilestonesByStatus(String journalId, MilestoneStatus status);

    /**
     * Update milestone
     */
    Milestone updateMilestone(String id, Milestone milestone);

    /**
     * Increment milestone progress
     */
    Milestone incrementProgress(String id);

    /**
     * Update milestone status
     */
    Milestone updateStatus(String id, MilestoneStatus status);

    /**
     * Delete milestone
     */
    void deleteMilestone(String id);

    /**
     * Get all milestones (admin)
     */
    List<Milestone> getAllMilestones();
}
