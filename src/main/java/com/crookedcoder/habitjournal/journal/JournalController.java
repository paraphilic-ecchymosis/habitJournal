package com.crookedcoder.habitjournal.journal;

import java.util.List;

import javax.validation.Valid;

import com.crookedcoder.habitjournal.users.UserDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JournalController {

	private final JournalRepository journalRepository;

	@RequestMapping("/journal")
	public @ResponseBody List<Journal> getJournals() {
		return journalRepository.findAll();
	}
    
}