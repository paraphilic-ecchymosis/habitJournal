package com.croojaco.habitjournal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddEntryToJournalDto {

    // TODO: Name or ID?
	@NonNull
	private String habitID;
	@NonNull
	private String unitsCompleted;
	@NonNull
	private String timeStamp;
	@NonNull
	private String type;
	@NonNull
	private String username;
	
}