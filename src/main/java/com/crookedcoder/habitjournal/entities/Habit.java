package com.crookedcoder.habitjournal.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Document(collection = "habits")
public class Habit {

    //@Id
	private String id;
	//@Indexed(unique=true)
	private final String name;
    //@NonNull
    private String description;
    private String goal;
    private String recurring;
    private String minimum;
    private String isActive;

}