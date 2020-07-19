package com.crookedcoder.habitjournal.journal.entries;

import com.crookedcoder.habitjournal.journal.habits.Habit;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntriesRepository extends MongoRepository<Habit, String> {
    
}