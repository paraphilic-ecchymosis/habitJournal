package com.crookedcoder.habitjournal.controller;

import javax.validation.Valid;

import com.crookedcoder.habitjournal.users.UserDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HabitJournalController {

    @GetMapping("/register")
	public String register(Model model) {
        model.addAttribute(new UserDto());

		return "register";
    }
    
    @PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		//registrationService.registerNewUser(user);
		//journalService.createNewPortfolio(user.getUsername());
		
		return "redirect:register?success";
	}
    
}