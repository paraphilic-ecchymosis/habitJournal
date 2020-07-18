package com.crookedcoder.habitjournal.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	@NotEmpty(message="Please enter your firstname")
	private String firstname;
	@NotEmpty(message="Please enter your lastname")
	private String lastname;
	@NotEmpty(message="Please enter a username")
	private String username;
	@NotEmpty(message="Please enter an email")
	@Email(message="Email is not valid")
	private String email;
	@NotEmpty(message="Please enter in a password")
	private String password;
	@NotEmpty(message="Please confirm your password")
	private String confirmPassword;

}