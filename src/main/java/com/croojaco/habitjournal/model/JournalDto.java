package com.croojaco.habitjournal.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class JournalDto {
    
	private final String id;
    private final String username;
    private final String[] entries;
    private final String[] milestones;
    private final String[] habits;

}