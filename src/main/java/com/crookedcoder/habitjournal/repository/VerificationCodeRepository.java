package com.crookedcoder.habitjournal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crookedcoder.habitjournal.entity.Verification;

public interface VerificationCodeRepository extends MongoRepository<Verification, String>{
	
	Verification findByUsername(String username);
	boolean existsByUsername(String username);
}
