package com.crookedcoder.habitjournal.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Document(collection = "habit")
public class Habit {

    @Id
	private String id;
	@Indexed(unique=true)
	private final String name;
    @NonNull
    private String description;
    private int goal;
    private boolean recurring;
    private boolean minimum;
    private boolean isActive;

}