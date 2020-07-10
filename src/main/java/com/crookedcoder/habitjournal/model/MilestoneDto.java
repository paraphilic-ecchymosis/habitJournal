package com.crookedcoder.habitjournal.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MilestoneDto {
    
    private final String id;
    private final String name;
    private final String description;
    private final String goal;
    private final String dueDate;
    private final String completionStatus;
    
}