package com.croojaco.habitjournal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.croojaco.habitjournal.entities.HjUser;

public interface HjUserRepository extends MongoRepository<HjUser, String> {

	
}