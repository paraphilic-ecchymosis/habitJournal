package com.crookedcoder.behaviorapp.journal;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.DateTime;

import lombok.Data;


public @Data class Journal {
    
    private List<Entry> entries = new ArrayList<Entry>();

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(int id, DateTime date, int units) {
        entries.add(new Entry(id, date, units));
    }

    @Data
    public class Entry {
    
        private int id;
        private DateTime dateLogged;
        private int unitsCompleted;

        public Entry(int id, DateTime date, int units) {
            this.id=id;
            this.dateLogged=date;
            this.unitsCompleted=units;
        }

    }

}