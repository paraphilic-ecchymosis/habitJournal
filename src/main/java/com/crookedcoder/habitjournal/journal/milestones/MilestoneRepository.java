package com.crookedcoder.habitjournal.journal.milestones;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MilestoneRepository extends MongoRepository<Milestone, String> {

    List<Milestone> findByJournalId(String journalId);

    List<Milestone> findByHabitId(String habitId);

    List<Milestone> findByStatus(MilestoneStatus status);

    boolean existsByName(String name);

}