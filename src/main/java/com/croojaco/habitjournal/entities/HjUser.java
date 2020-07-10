package com.croojaco.habitjournal.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "hjusers")
@RequiredArgsConstructor
@Getter
@ToString
public class HjUser {
    	
	@Id
	private String id;
	@NonNull
	@Indexed(unique=true)
	private final String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@Setter
	private boolean verified;
	private String securityPin;
	@Setter
	private boolean totpEnabled;
	
}