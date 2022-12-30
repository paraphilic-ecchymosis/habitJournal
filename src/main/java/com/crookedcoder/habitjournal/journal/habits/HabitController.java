package com.crookedcoder.habitjournal.journal.habits;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crookedcoder.habitjournal.journal.Journal;
import com.crookedcoder.habitjournal.journal.JournalRepository;
import com.crookedcoder.habitjournal.users.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HabitController {

    private final HabitRepository habitRepository;
    private final JournalRepository journalRepository;
    
    @PostMapping("/journal/habits")
	public String createHabit(User user, @Valid @ModelAttribute("habit") Habit habit, BindingResult result) {
        if(result.hasErrors()) {
            return "redirect:/journal/habits";
		}
        // TODO: Not quite right.
        // This should be wrapped in a service.
        if (journalRepository.existsById(habit.getJournalId())) {
            Journal journal = new Journal(user.getUsername());
            habitRepository.save(habit);
        }
        return "redirect:/journal/habits?success";
        
    }

    // Bad, bad, bad, bad, BAD.
    @GetMapping("/journal/habits")
    public String getHabitAdmin(Model model, String journalId) {
        Habit habit = new Habit();
        habit.setJournalId(journalId);
        model.addAttribute("habit", habit);

        return "habits/habits";
    }

    // TODO: Review Thymeleaf doc for this. 
    /* 
        Better approach would be to map the request for current habits
        in own method pulling List
    */
    @GetMapping("/api/users/habits")
	public @ResponseBody List<Habit> getUserHabits(String journalId) {
        List<Habit> userHabits = habitRepository.findAll();

        return userHabits.stream()
        .filter(x -> x.getJournalId()
                .equalsIgnoreCase(journalId))
        .collect(Collectors.toList());
    }

    @GetMapping("/api/habits")
    public @ResponseBody List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

}