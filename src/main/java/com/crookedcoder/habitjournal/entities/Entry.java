package com.crookedcoder.habitjournal.entities;

import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "entries")
public class Entry {
    
    @Id
    private String id;
    private final String username;
    @DBRef
    private final String habit;
	private final String unitsCompleted;
	private final String timestamp;

	public Entry(String username, String habit, String unitsCompleted, String timestamp) {
        this.username=username;
        this.habit=habit;
		this.unitsCompleted=unitsCompleted;
		this.timestamp=timestamp;
		this.id = new ObjectId().toHexString();
	}
}