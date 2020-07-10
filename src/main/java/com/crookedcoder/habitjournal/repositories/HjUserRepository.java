package com.crookedcoder.habitjournal.repositories;

import com.crookedcoder.habitjournal.entities.HjUser;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface HjUserRepository extends MongoRepository<HjUser, String> {

	
}