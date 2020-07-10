package com.croojaco.habitjournal.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class HabitDto {

	private String id;
	private String name;
	private String description;
	private String goal;
    private String recurring;
    private String minimum;
    private String isActive;
	
}