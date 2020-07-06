package com.crookedcoder.habitjournal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entity.Habit;

public interface HabitRepository extends MongoRepository<Habit, String>{
	
    Habit findByID(String id);
    
    Habit findByName(String Name);
	
}