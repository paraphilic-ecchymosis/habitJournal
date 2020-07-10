package com.croojaco.habitjournal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.croojaco.habitjournal.entities.Habit;
import com.croojaco.habitjournal.model.HabitDto;
import com.croojaco.habitjournal.repositories.HabitRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HabitQueryServiceNoSql implements HabitQueryService {

        @Autowired
        private HabitRepository habitRepository;
        private Map<String, HabitDto> habits = null;
    
        @Override
        public List<HabitDto> getHabits() {
            if(this.habits == null) {
                this.habits = new HashMap<>();
                for(Habit habit : habitRepository.findAll()) {
                    this.habits.put(habit.getName(), new HabitDto());
                }
            }
            return new ArrayList<>(habits.values());
        }
    
        @Override
        public HabitDto getHabit(String name) {
            return habits.get(name);
        }

        private Habit createHabitEntity(HabitDto request) {
            Habit habit = new Habit(request.getName());
            return habit;
        }


		public void save(Habit habit) {
            
            habitRepository.insert(habit);

		}

}