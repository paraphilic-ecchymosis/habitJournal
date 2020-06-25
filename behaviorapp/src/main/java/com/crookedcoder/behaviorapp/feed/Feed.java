package com.crookedcoder.behaviorapp.feed;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.DateTime;

import lombok.Data;

@Data
public class Feed {
    
    private List<Entry> entries = new ArrayList<Entry>();

    @Data
    public class Entry {
    
        private int id;
        private DateTime dateLogged;
        private int unitsCompleted;

    }

}