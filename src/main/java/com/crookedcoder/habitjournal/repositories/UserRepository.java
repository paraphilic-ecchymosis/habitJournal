package com.crookedcoder.habitjournal.repositories;

import com.crookedcoder.habitjournal.entities.User;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);

	User findByEmail(String email);

	
}