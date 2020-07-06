package com.crookedcoder.habitjournal.event;

import com.crookedcoder.habitjournal.entity.HabitJournalUser;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4113549487933175429L;
	private final HabitJournalUser user;
	
	public UserRegistrationEvent(HabitJournalUser user) {
		super(user);
		this.user=user;
		
	}

}