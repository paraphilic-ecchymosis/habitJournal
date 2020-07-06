package com.crookedcoder.habitjournal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PostAuthorize;

import com.crookedcoder.habitjournal.entity.Journal;

public interface JournalRepository extends MongoRepository<Journal, String> {
	@PostAuthorize("hasPermission(returnObject, 'READ')")
	Journal findByUsername(String username);
	boolean existsByUsername(String username);
}