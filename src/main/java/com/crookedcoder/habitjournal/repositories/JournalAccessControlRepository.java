package com.crookedcoder.habitjournal.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entities.JournalAccessControl;

public interface JournalAccessControlRepository extends MongoRepository<JournalAccessControl, String> {

	List<JournalAccessControl> findAllByUsername(String username);
	
}