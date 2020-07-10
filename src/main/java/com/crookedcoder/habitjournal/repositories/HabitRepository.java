package com.crookedcoder.habitjournal.repositories;

import com.crookedcoder.habitjournal.entities.Habit;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HabitRepository extends MongoRepository<Habit, String>{
	
	
}