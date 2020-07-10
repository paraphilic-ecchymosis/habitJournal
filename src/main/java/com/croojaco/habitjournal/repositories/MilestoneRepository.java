package com.croojaco.habitjournal.repositories;

import com.croojaco.habitjournal.entities.Milestone;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MilestoneRepository extends MongoRepository<Milestone, String> {

	
}