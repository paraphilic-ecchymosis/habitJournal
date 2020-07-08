package com.crookedcoder.habitjournal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.crookedcoder.habitjournal.entity.Habit;
import com.crookedcoder.habitjournal.model.HabitDto;
import com.crookedcoder.habitjournal.repository.HabitRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HabitQueryServiceNoSql implements HabitQueryService {
    
        private HabitRepository habitRepository;
        private Map<String, HabitDto> habits = null;
    
        @Override
        public List<HabitDto> getHabits() {
            if(this.habits == null) {
                this.habits = new HashMap<>();
                for(Habit habit : habitRepository.findAll()) {
                    this.habits.put(habit.getName(), new HabitDto(habit.getId(), habit.getName()));
                }
            }
            return new ArrayList<>(habits.values());
        }
    
        @Override
        public HabitDto getHabit(String name) {
            return habits.get(name);
        }


		public void save(Habit habit) {
            
            habitRepository.insert(habit);

		}

}