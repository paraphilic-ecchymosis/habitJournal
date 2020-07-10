package com.crookedcoder.habitjournal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entities.Verification;

public interface VerificationCodeRepository extends MongoRepository<Verification, String>{
	
	Verification findByUsername(String username);
	boolean existsByUsername(String username);
}
