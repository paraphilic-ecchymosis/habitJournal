package com.crookedcoder.habitjournal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crookedcoder.habitjournal.entity.Journal;
import com.crookedcoder.habitjournal.model.JournalDto;
import com.crookedcoder.habitjournal.repository.JournalRepository;

public class JournalQueryServiceNoSql implements JournalQueryService {
    
    //private final JournalRepository journalRepository;
    private Map<String, JournalDto> journal = null;

    @Override
    public JournalDto getJournal(String userName) {
        return journal.get(userName);
    }

    @Override
    public List<JournalDto> getJournal() {
        // TODO Auto-generated method stub
        return null;
    }

}