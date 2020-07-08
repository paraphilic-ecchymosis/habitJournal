package com.crookedcoder.habitjournal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.crookedcoder.habitjournal.model.AddEntryToJournalDto;
import com.crookedcoder.habitjournal.model.DeleteEntriesDto;
import com.crookedcoder.habitjournal.service.JournalCommandService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JournalCommandController {

	private JournalCommandService commandService;
	
	@PostMapping("/journal/entries")
	public ModelAndView AddEntryToJournal(@ModelAttribute("entry") AddEntryToJournalDto request) {
		commandService.addEntryToJournal(request);
		return new ModelAndView("redirect:/journal");
	}
	
	
	
	@DeleteMapping("/journal/entries")
	public ModelAndView deleteEntryFromJournal(@ModelAttribute("selected") DeleteEntriesDto request) {
		for(String id : request.getId()) {
			commandService.removeEntryFromJournal(id);
		}
		return new ModelAndView("redirect:/journal");
	}
	
}