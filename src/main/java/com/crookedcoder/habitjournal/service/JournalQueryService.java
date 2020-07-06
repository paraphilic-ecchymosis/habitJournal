package com.crookedcoder.habitjournal.service;

import java.util.List;

import com.crookedcoder.habitjournal.model.JournalDto;

public interface JournalQueryService {
    
    List<JournalDto> getJournal();
    JournalDto getJournal(String userName);

}