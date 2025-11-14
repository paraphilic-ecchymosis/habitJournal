package com.crookedcoder.habitjournal.journal.milestones;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "milestone")
public @Data class Milestone {

    @Id
    private String id;

    @NotEmpty(message = "Name required.")
    @Indexed(unique = true)
    private String name;

    @NotEmpty(message = "Description required.")
    private String description;

    @NotNull(message = "Habit ID required.")
    @Indexed
    private String habitId; // Link to associated habit

    @Indexed
    private String journalId; // Link to journal

    private Integer goalUnits; // Target number of habit completions

    private Integer completedUnits; // Current progress

    private LocalDate startDate;

    private LocalDate dueDate;

    @NotNull(message = "Status required.")
    private MilestoneStatus status;

    private LocalDate completedDate; // When milestone was achieved

    public Milestone(String name, String description, String habitId, String journalId, Integer goalUnits, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.habitId = habitId;
        this.journalId = journalId;
        this.goalUnits = goalUnits;
        this.completedUnits = 0;
        this.startDate = LocalDate.now();
        this.dueDate = dueDate;
        this.status = MilestoneStatus.NOT_STARTED;
    }

    /**
     * Increments completed units and updates status
     */
    public void incrementProgress() {
        if (this.completedUnits == null) {
            this.completedUnits = 0;
        }
        this.completedUnits++;

        if (this.status == MilestoneStatus.NOT_STARTED) {
            this.status = MilestoneStatus.IN_PROGRESS;
        }

        if (this.goalUnits != null && this.completedUnits >= this.goalUnits) {
            this.status = MilestoneStatus.COMPLETED;
            this.completedDate = LocalDate.now();
        }
    }

    /**
     * Calculates completion percentage
     */
    public double getProgressPercentage() {
        if (goalUnits == null || goalUnits == 0) {
            return 0.0;
        }
        return ((double) (completedUnits != null ? completedUnits : 0) / goalUnits) * 100;
    }

    /**
     * Check if milestone is overdue using Java 21 pattern matching
     */
    public boolean isOverdue() {
        return switch (status) {
            case COMPLETED, CANCELLED, FAILED -> false;
            case NOT_STARTED, IN_PROGRESS -> dueDate != null && LocalDate.now().isAfter(dueDate);
        };
    }

    /**
     * Get status description using Java 21 switch expressions
     */
    public String getStatusDescription() {
        return switch (status) {
            case NOT_STARTED -> "Milestone has not been started yet";
            case IN_PROGRESS -> "Milestone is in progress (%d/%d completed)".formatted(
                completedUnits != null ? completedUnits : 0,
                goalUnits != null ? goalUnits : 0
            );
            case COMPLETED -> "Milestone completed on " + completedDate;
            case FAILED -> "Milestone failed to complete by due date";
            case CANCELLED -> "Milestone was cancelled";
        };
    }

    /**
     * Validate status transition using pattern matching (Java 21)
     */
    public boolean canTransitionTo(MilestoneStatus newStatus) {
        return switch (this.status) {
            case NOT_STARTED -> newStatus == MilestoneStatus.IN_PROGRESS ||
                                newStatus == MilestoneStatus.CANCELLED;
            case IN_PROGRESS -> newStatus == MilestoneStatus.COMPLETED ||
                                newStatus == MilestoneStatus.FAILED ||
                                newStatus == MilestoneStatus.CANCELLED;
            case COMPLETED, FAILED, CANCELLED -> false; // Final states
        };
    }

}
    