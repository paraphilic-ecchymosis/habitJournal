package com.crookedcoder.habitjournal.journal.entries;

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

	@NonNull
	private String name;
	@NonNull
	private String unitsCompleted;
	@NonNull
	private String timeStamp;
	@NonNull
	private String type;
	@NonNull
	private String username;
	
}