package com.crookedcoder.habitjournal.controller;

import java.util.List;

import com.crookedcoder.habitjournal.entity.HjUser;
import com.crookedcoder.habitjournal.service.AdminService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JournalApiController {
	
	private AdminService adminService;
	
	@GetMapping("/api/users")
	public List<HjUser> getUsers() {
		return this.adminService.getAllUsers();
	}	
	
}