package com.croojaco.habitjournal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.croojaco.habitjournal.entities.Habit;

public interface HabitRepository extends MongoRepository<Habit, String>{
	
	
}