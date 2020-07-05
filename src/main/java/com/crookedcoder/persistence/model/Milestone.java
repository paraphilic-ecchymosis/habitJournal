package com.crookedcoder.persistence.model;

import com.google.api.client.util.DateTime;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public @Data class Milestone {
    
    private int id;
    private String goal;
    private DateTime dueDate;
}
    