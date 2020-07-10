package com.crookedcoder.habitjournal.event;

import com.crookedcoder.habitjournal.entities.HjUser;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4113549487933175429L;
	private final HjUser user;
	
	public UserRegistrationEvent(HjUser user) {
		super(user);
		this.user=user;
		
	}

}