package com.crookedcoder.habitjournal.journal.habits;

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
    @NotEmpty (message = "Journal ID not found.")
    private String journalId;
    @NotEmpty (message = "Description is required.")
    private String description;
    private String goal;
    private Boolean recurring;
    private Boolean active;

    public Boolean getRecurring()
    {
        return recurring == null ? false : recurring;
    }

    public Boolean getActive()
    {
        return active == null ? false : active;
    }

}