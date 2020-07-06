package com.crookedcoder.habitjournal.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Document
@Getter
@RequiredArgsConstructor
public class JournalAccessControl {

	private final String username;
	private final String journalId;
	private final String type;
	
}
