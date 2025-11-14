package com.crookedcoder.habitjournal.journal.habits;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HabitRepository extends MongoRepository<Habit, String> {

    List<Habit> findByJournalId(String journalId);

    boolean existsByName(String name);

}