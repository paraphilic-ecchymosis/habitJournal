package com.crookedcoder.habitjournal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entities.TOTPDetails;

public interface TOTPDetailsRepository extends MongoRepository<TOTPDetails, String>{

	TOTPDetails findByUsername(String username);
	boolean existsByUsername(String username);
	Long deleteByUsername(String username);
	
}