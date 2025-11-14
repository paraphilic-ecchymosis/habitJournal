package com.crookedcoder.habitjournal.journal.entries;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EntriesRepository extends MongoRepository<Entry, String> {

    List<Entry> findByHabitId(String habitId);

}