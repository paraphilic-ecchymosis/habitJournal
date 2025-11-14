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

}
    