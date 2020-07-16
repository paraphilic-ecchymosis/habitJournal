package com.crookedcoder.habitjournal.entities;


import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Document(collection = "milestone")
public @Data class Milestone {

    // TODO: This isn't right.
    // Should have a related habit 
    // to track units completed
    // while Milestone is active.
    
    @Id
    private String id;
    @NotEmpty
	@Indexed(unique=true)
	private String name;
    @NotEmpty
    private String description;
    private String goal;
    @NotEmpty
    private String habitId;
    private String dueDate;
    // TODO: Maybe enum for this now that
    // I have renamed it.....derp.
    private String completionStatus;
}
    