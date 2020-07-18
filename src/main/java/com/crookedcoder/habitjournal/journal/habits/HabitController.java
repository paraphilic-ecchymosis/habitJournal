package com.crookedcoder.habitjournal.journal.habits;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HabitController {

    private final HabitRepository habitRepository;
    
    @PostMapping("/habit")
	public String createHabit(@Valid @ModelAttribute("habit") Habit habit, BindingResult result) {
		if(result.hasErrors()) {
            return "habits/habits";
		}

        habitRepository.save(habit);
        return "redirect:/habit?success";
        
    }
    

    // TODO: Review Thymeleaf doc for this. 
    /* 
        Better approach would be to map the request for current habits
        in own method pulling List
    */
    @GetMapping("/habit")
	public String getUserHabit(Model model) {
        model.addAttribute("habit",new Habit());

		return "habits/habits";
    }
    
    @GetMapping("/habits")
    public @ResponseBody List<Habit> getUserHabits() {
        return habitRepository.findAll();
    }

}