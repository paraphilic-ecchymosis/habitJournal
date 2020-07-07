package com.crookedcoder.habitjournal.controller;

import com.crookedcoder.habitjournal.model.AddEntryToJournalDto;
import com.crookedcoder.habitjournal.model.DeleteEntriesDto;
import com.crookedcoder.habitjournal.model.ListEntriesDto;
import com.crookedcoder.habitjournal.service.JournalQueryService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JournalQueryController {
    
    private final JournalQueryService journalService;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/journal";
	}
	
	@GetMapping("/journal")
	public ModelAndView entries() {
		ModelAndView model = new ModelAndView();
		return model;
	}
	
	@GetMapping("/journal/{id}")
	public ModelAndView userJournalEntries(@PathVariable String id) {
		ModelAndView model = new ModelAndView();
		model.addObject("entriesResponse", journalService.getJournalEntries());
		model.addObject("entry", new AddEntryToJournalDto());
		model.setViewName("journal");
		return model;
	}
	
	@GetMapping(value = {"/journal/{id}"})
	public ModelAndView listEntriesForJournal(@PathVariable String id) {
		ListEntriesDto entries = journalService.getJournalEntries();
		ModelAndView model = new ModelAndView();
		model.addObject("entries", entries.getEntries());
		model.addObject("selected",new DeleteEntriesDto());
		model.setViewName("entries");
		return model;
	}
	
}