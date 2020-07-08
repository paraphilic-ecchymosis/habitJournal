package com.crookedcoder.habitjournal.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.crookedcoder.habitjournal.repository.HjUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	private HjUserRepository repository;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return username != null && repository.findByUsername(username) == null ;
	}

}
