package com.crookedcoder.habitjournal.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entity.Milestone;

public interface MilestoneRepository extends MongoRepository<Milestone, String> {
	
    Milestone findByID(String id);
    
    Milestone findByName(String Name);
	
}