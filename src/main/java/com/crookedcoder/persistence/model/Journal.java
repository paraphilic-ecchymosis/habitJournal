package com.crookedcoder.persistence.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.DateTime;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Journal {
    
    // TODO: This class is crap. Probably split them apart.
    
    private List<Entry> entries = new ArrayList<Entry>();

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(int id, DateTime date, int units) {
        entries.add(new Entry());
    }

    @Component
    @RequiredArgsConstructor
    public @Data class Entry {
    
        private int id;
        private DateTime dateLogged;
        private int unitsCompleted;

    }

}