package com.croojaco.habitjournal.entities;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Document(collection = "habits")
public class Habit {

    @Id
	private String id;
    @Indexed(unique=true)
    private final String name;
    // TODO: May be a problem.
    @NonNull
    private String description;
    private int goal;
    private boolean recurring;
    private boolean minimum;
    private boolean isActive;

}