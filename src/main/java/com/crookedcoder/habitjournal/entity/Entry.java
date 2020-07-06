package com.crookedcoder.habitjournal.entity;

import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Entry {
    
    @Id
    private String id;
    private final String username;
    @DBRef
    private final Habit habit;
	private final int unitsCompleted;
	private final long timestamp;

	public Entry(String username, Habit habit, int unitsCompleted, long timestamp) {
        this.username=username;
        this.habit=habit;
		this.unitsCompleted=unitsCompleted;
		this.timestamp=timestamp;
		this.id = new ObjectId().toHexString();
	}
}