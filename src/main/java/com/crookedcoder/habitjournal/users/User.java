package com.crookedcoder.habitjournal.users;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public @Data class User {

	@Id
	private String id;

	@NotEmpty(message = "Username is required")
	@Indexed(unique = true)
	private String username;

	@NotEmpty(message = "First name is required")
	private String firstName;

	@NotEmpty(message = "Last name is required")
	private String lastName;

	@Email(message = "Email must be valid")
	@NotEmpty(message = "Email is required")
	@Indexed(unique = true)
	private String email;

	@NotEmpty(message = "Password is required")
	private String password; // Will be BCrypt hashed

	private Set<String> roles = new HashSet<>(); // e.g., "ROLE_USER", "ROLE_ADMIN"

	private boolean enabled = true;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked = true;

	private boolean credentialsNonExpired = true;

	private LocalDateTime createdAt;

	private LocalDateTime lastLogin;

	public User(String username, String firstName, String lastName, String email, String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles.add("ROLE_USER");
		this.createdAt = LocalDateTime.now();
		this.enabled = true;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
	}

}