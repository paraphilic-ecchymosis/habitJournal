package com.croojaco.habitjournal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.croojaco.habitjournal.entities.Journal;

public interface JournalRepository extends MongoRepository<Journal, String> {

}