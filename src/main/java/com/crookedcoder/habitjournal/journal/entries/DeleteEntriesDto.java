package com.crookedcoder.habitjournal.journal.entries;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DeleteEntriesDto {

	private String[] id;
	
}