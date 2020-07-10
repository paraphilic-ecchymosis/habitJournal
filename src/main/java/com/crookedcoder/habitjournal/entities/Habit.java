package com.crookedcoder.habitjournal.entities;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Document(collection = "habits")
public @Data class Habit {

    @Id
	private String id;
	@Indexed(unique=true)
	private final String name;
    @NonNull
    private String description;
    private String goal;
    private String recurring;
    private String minimum;
    private String isActive;

}