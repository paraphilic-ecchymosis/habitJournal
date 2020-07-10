package com.crookedcoder.habitjournal.service;

import com.crookedcoder.habitjournal.model.AddEntryToJournalDto;

public interface JournalCommandService {
    
    void addEntryToJournal(AddEntryToJournalDto request);
	void removeEntryFromJournal(String entryId);
	void createNewJournal(String username);
	boolean userHasAJournal(String username);
}