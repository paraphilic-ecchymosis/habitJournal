package com.crookedcoder.habitjournal.journal.habits;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HabitRepository extends MongoRepository<Habit, String>{
	
	
}