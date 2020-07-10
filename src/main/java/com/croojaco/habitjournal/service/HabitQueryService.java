package com.croojaco.habitjournal.service;

import java.util.List;

import com.croojaco.habitjournal.model.HabitDto;

public interface HabitQueryService {

	List<HabitDto> getHabits();
	HabitDto getHabit(String name);
}