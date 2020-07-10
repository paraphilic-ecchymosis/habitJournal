package com.crookedcoder.habitjournal.model;

import com.crookedcoder.habitjournal.entities.Habit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public @Data class HabitDto {

	public HabitDto(Habit habit) {
        
	}
	private String id;
	private String name;
	private String description;
	private String goal;
    private String recurring;
    private String minimum;
    private String isActive;
	
}