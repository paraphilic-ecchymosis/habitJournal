package com.crookedcoder.habitjournal.journal.milestones;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MilestoneRepository extends MongoRepository<Milestone, String> {

	
}