package com.crookedcoder.habitjournal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crookedcoder.habitjournal.entity.HabitJournalUser;
import com.crookedcoder.habitjournal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {

	private final UserRepository userRepository;
	
	//@PreAuthorize("hasRole('ADMIN')")
	public List<HabitJournalUser> getAllUsers() {
		return this.userRepository.findAll();
	}
	
}