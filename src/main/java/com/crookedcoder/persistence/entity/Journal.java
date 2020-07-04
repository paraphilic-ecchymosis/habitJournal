package com.crookedcoder.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.DateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;


public class Journal {
    
    private List<Entry> entries = new ArrayList<Entry>();

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(int id, DateTime date, int units) {
        entries.add(new Entry());
    }

    
    @RequiredArgsConstructor
    public @Data class Entry {
    
        private int id;
        private DateTime dateLogged;
        private int unitsCompleted;

    }

}