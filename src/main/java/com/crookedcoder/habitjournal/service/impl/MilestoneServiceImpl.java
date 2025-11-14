package com.crookedcoder.habitjournal.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crookedcoder.habitjournal.exception.DuplicateResourceException;
import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.journal.milestones.Milestone;
import com.crookedcoder.habitjournal.journal.milestones.MilestoneRepository;
import com.crookedcoder.habitjournal.journal.milestones.MilestoneStatus;
import com.crookedcoder.habitjournal.service.MilestoneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;

    @Override
    public Milestone createMilestone(Milestone milestone) {
        log.info("Creating new milestone: {}", milestone.getName());

        // Check if milestone with same name already exists
        if (milestoneRepository.existsByName(milestone.getName())) {
            throw new DuplicateResourceException("Milestone", "name", milestone.getName());
        }

        // Set default values if not provided
        if (milestone.getStartDate() == null) {
            milestone.setStartDate(LocalDate.now());
        }
        if (milestone.getStatus() == null) {
            milestone.setStatus(MilestoneStatus.NOT_STARTED);
        }
        if (milestone.getCompletedUnits() == null) {
            milestone.setCompletedUnits(0);
        }

        Milestone savedMilestone = milestoneRepository.save(milestone);
        log.info("Milestone created successfully: {}", savedMilestone.getId());
        return savedMilestone;
    }

    @Override
    @Transactional(readOnly = true)
    public Milestone getMilestoneById(String id) {
        return milestoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Milestone", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Milestone> getMilestonesByJournalId(String journalId) {
        return milestoneRepository.findAll().stream()
                .filter(milestone -> milestone.getJournalId().equals(journalId))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Milestone> getMilestonesByHabitId(String habitId) {
        return milestoneRepository.findAll().stream()
                .filter(milestone -> milestone.getHabitId().equals(habitId))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Milestone> getMilestonesByStatus(String journalId, MilestoneStatus status) {
        return milestoneRepository.findAll().stream()
                .filter(milestone -> milestone.getJournalId().equals(journalId))
                .filter(milestone -> milestone.getStatus() == status)
                .toList();
    }

    @Override
    public Milestone updateMilestone(String id, Milestone updatedMilestone) {
        Milestone existingMilestone = getMilestoneById(id);

        // Update fields
        if (updatedMilestone.getName() != null && !updatedMilestone.getName().equals(existingMilestone.getName())) {
            if (milestoneRepository.existsByName(updatedMilestone.getName())) {
                throw new DuplicateResourceException("Milestone", "name", updatedMilestone.getName());
            }
            existingMilestone.setName(updatedMilestone.getName());
        }
        if (updatedMilestone.getDescription() != null) {
            existingMilestone.setDescription(updatedMilestone.getDescription());
        }
        if (updatedMilestone.getGoalUnits() != null) {
            existingMilestone.setGoalUnits(updatedMilestone.getGoalUnits());
        }
        if (updatedMilestone.getDueDate() != null) {
            existingMilestone.setDueDate(updatedMilestone.getDueDate());
        }
        if (updatedMilestone.getStatus() != null) {
            existingMilestone.setStatus(updatedMilestone.getStatus());
        }

        Milestone saved = milestoneRepository.save(existingMilestone);
        log.info("Milestone updated successfully: {}", saved.getId());
        return saved;
    }

    @Override
    public Milestone incrementProgress(String id) {
        Milestone milestone = getMilestoneById(id);
        milestone.incrementProgress();
        Milestone saved = milestoneRepository.save(milestone);
        log.info("Milestone progress incremented: {} -> {}/{}",
                saved.getId(), saved.getCompletedUnits(), saved.getGoalUnits());
        return saved;
    }

    @Override
    public Milestone updateStatus(String id, MilestoneStatus status) {
        Milestone milestone = getMilestoneById(id);
        milestone.setStatus(status);

        if (status == MilestoneStatus.COMPLETED && milestone.getCompletedDate() == null) {
            milestone.setCompletedDate(LocalDate.now());
        }

        Milestone saved = milestoneRepository.save(milestone);
        log.info("Milestone status updated: {} -> {}", saved.getId(), saved.getStatus());
        return saved;
    }

    @Override
    public void deleteMilestone(String id) {
        Milestone milestone = getMilestoneById(id);
        milestoneRepository.delete(milestone);
        log.info("Milestone deleted successfully: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Milestone> getAllMilestones() {
        return milestoneRepository.findAll();
    }
}
