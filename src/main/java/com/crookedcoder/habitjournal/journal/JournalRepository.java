package com.crookedcoder.habitjournal.journal;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Journal, String> {

}