package com.crookedcoder.habitjournal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entity.HabitJournalUser;

public interface UserRepository extends MongoRepository<HabitJournalUser, String> {

	HabitJournalUser findByUsername(String username);
	HabitJournalUser findByEmail(String email);
	
}