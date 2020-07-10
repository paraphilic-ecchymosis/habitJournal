package com.crookedcoder.habitjournal.controller;

import javax.validation.Valid;

import com.crookedcoder.habitjournal.entities.Habit;
import com.crookedcoder.habitjournal.model.HabitDto;
import com.crookedcoder.habitjournal.service.HabitQueryServiceNoSql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HabitJournalController {

    @Autowired
    private HabitQueryServiceNoSql habitQueryServiceNoSql;
	
	@PostMapping("/habits")
	public String createHabit(@Valid @ModelAttribute("habit") HabitDto habitDto, BindingResult result) {
		if(result.hasErrors()) {
            return "habits";
		}
        Habit habit = new Habit(habitDto.getName());
        this.habitQueryServiceNoSql.save(habit);
        return "redirect:habits?success";
        
    }
    
    @GetMapping("/habits")
	public String createHabit(Model model) {
        model.addAttribute("habit",new HabitDto());

		return "habits";
	}

    
}