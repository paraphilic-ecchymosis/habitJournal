package com.crookedcoder.habitjournal.controller;

import java.util.List;

import com.crookedcoder.habitjournal.entities.Habit;
import com.crookedcoder.habitjournal.repositories.HabitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hj")
public class HabitJournalController {

    @Autowired
    private HabitRepository habitRepository;


    @RequestMapping(value = "/habits", method = RequestMethod.GET)
    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    @RequestMapping(value = "/habits", method = RequestMethod.POST)
    public Habit addNewHabit(@RequestBody Habit habit){
        return habitRepository.save(habit);
    }

    
}