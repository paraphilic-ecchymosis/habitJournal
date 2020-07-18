package com.crookedcoder.habitjournal.entities;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "users")
@RequiredArgsConstructor
@Getter
@ToString
public class User {
    	
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
	@Setter
	private boolean verified;
	@NonNull
	private String securityPin;
	@Setter
	private boolean totpEnabled;

	public User(String username, String firstname2, String lastname2, String email2, String encode, String encode2,
			boolean tOTP_ENABLED) {
	}
	
}