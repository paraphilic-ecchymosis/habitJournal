package com.crookedcoder.habitjournal.journal;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Journal, String> {

    Optional<Journal> findByUsername(String username);

    boolean existsByUsername(String username);

}