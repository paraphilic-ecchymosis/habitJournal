package com.crookedcoder.habitjournal.journal.entries;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public @Data class EntryDetailsDto {

    
    private String id;
    private String username;
    private String habitID;
	private String unitsCompleted;
    // TODO: Questionable.
    private String timestamp;
}