package com.crookedcoder.habitjournal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.crookedcoder.habitjournal.entities.Habit;

@Repository
public interface HabitRepository extends MongoRepository<Habit, String>{
	
	
}