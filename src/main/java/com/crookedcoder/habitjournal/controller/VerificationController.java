package com.crookedcoder.habitjournal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crookedcoder.habitjournal.entity.HabitJournalUser;
import com.crookedcoder.habitjournal.repository.UserRepository;
import com.crookedcoder.habitjournal.service.VerificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class VerificationController {

	private final VerificationService verificationService;
	private final UserRepository userRepository;
	
	@GetMapping("/verify/email")
	public String verifyEmail(@RequestParam String id) {
		String username = verificationService.getUsernameForId(id);
		if(username != null) {
			HabitJournalUser user = userRepository.findByUsername(username);
			user.setVerified(true);
			userRepository.save(user);
		}
		return "redirect:/login-verified";
	}
	
}