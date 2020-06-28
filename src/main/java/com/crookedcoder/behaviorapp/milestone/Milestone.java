package com.crookedcoder.behaviorapp.milestone;

import com.google.api.client.util.DateTime;

import lombok.Data;

@Data
public class Milestone {
    
    private int id;
    private String goal;
    private DateTime dueDate;
    
}