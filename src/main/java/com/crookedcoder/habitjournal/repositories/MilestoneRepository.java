package com.crookedcoder.habitjournal.repositories;

import com.crookedcoder.habitjournal.entities.Milestone;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MilestoneRepository extends MongoRepository<Milestone, String> {

	
}