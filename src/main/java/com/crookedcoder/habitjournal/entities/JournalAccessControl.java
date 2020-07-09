package com.crookedcoder.habitjournal.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//@Document(collection = "journalaccesscontrol")
@Getter
@RequiredArgsConstructor
public class JournalAccessControl {

	private final String username;
	private final String journalId;
	private final String type;
	
}
