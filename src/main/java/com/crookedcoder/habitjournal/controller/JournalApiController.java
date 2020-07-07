package com.crookedcoder.habitjournal.controller;

import java.util.List;

import com.crookedcoder.habitjournal.entity.HabitJournalUser;
import com.crookedcoder.habitjournal.service.AdminService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JournalApiController {
	
	private final AdminService adminService;
	
	@GetMapping("/api/users")
	public List<HabitJournalUser> getUsers() {
		return this.adminService.getAllUsers();
	}	
	
}