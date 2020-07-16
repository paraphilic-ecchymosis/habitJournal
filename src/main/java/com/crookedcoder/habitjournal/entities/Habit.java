package com.crookedcoder.habitjournal.entities;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty (message = "Name is required.")
	@Indexed(unique=true)
	private String name;
    @NotEmpty (message = "Description is required.")
    private String description;
    private String goal;
    //@NotEmpty (message = "Selection is required.")
    private Boolean recurring;
    private Boolean minimum;
    private Boolean active;

    public Boolean getRecurring()
    {
        return recurring == null ? false : recurring;
    }

}