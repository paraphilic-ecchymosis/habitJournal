package com.crookedcoder.habitjournal.service;

import java.util.List;

import com.crookedcoder.habitjournal.model.JournalDto;
import com.crookedcoder.habitjournal.model.ListEntriesDto;

public interface JournalQueryService {
    
    JournalDto getJournal(String userName);
    ListEntriesDto getJournalEntries();

}