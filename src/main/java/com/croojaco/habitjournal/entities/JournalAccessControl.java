package com.croojaco.habitjournal.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Document(collection = "journalaccesscontrol")
@Getter
@RequiredArgsConstructor
public class JournalAccessControl {

	private String username;
	private String journalId;
	private String type;
	
}
