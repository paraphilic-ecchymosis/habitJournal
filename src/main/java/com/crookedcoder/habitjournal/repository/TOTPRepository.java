package com.crookedcoder.habitjournal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entity.TOTPDetails;

public interface TOTPRepository extends MongoRepository<TOTPDetails, String>{

	TOTPDetails findByUsername(String username);
	boolean existsByUsername(String username);
	Long deleteByUsername(String username);
	
}