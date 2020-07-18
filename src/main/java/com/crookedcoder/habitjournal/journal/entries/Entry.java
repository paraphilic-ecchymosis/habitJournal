package com.crookedcoder.habitjournal.journal.entries;

import java.util.Date;

import com.mongodb.lang.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Document(collection = "entries")
public @Data class Entry {
    
    @Id
    private String id;
    private String body;
    @NonNull
	private Date timestamp;

}