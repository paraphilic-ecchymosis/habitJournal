package com.crookedcoder.habitjournal.journal;

import java.util.List;

import org.springframework.stereotype.Controller;
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