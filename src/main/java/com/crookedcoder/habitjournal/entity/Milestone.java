package com.crookedcoder.habitjournal.entity;

import com.google.api.client.util.DateTime;

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
@Document
public class Milestone {

    // TODO: This isn't right.
    // Should have a related habit 
    // to track units completed
    // while Milestone is active.
    
    @Id
	private String id;
	@Indexed(unique=true)
	private final String name;
    @NonNull
    private String description;
    @NonNull
    private String goal;
    @NonNull
    private DateTime dueDate;
    // TODO: Maybe enum for this now that
    // I have renamed it.....derp.
    private boolean completionStatus;
}
    