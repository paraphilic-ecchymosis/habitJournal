package com.crookedcoder.habitjournal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crookedcoder.habitjournal.entity.HjUser;
import com.crookedcoder.habitjournal.repository.HjUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {

	private HjUserRepository userRepository;
	
	//@PreAuthorize("hasRole('ADMIN')")
	public List<HjUser> getAllUsers() {
		return this.userRepository.findAll();
	}
	
}