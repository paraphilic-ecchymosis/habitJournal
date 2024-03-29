package com.crookedcoder.habitjournal.users;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Document(collection = "users")
@RequiredArgsConstructor
public @Data class User {
    	
	@Id
	private String id;
	@NonNull
	@Indexed(unique=true)
	private String username;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@Email
	@NonNull
	private String email;
	@NonNull
	private String password;
	
}