// package com.crookedcoder.habitjournal.validation;

// import javax.validation.ConstraintValidator;
// import javax.validation.ConstraintValidatorContext;

// import com.crookedcoder.habitjournal.repository.HjUserRepository;

// import lombok.AllArgsConstructor;

// @AllArgsConstructor
// public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

// 	private HjUserRepository repository;
	
// 	@Override
// 	public boolean isValid(String email, ConstraintValidatorContext context) {
// 		return email != null && repository.findByEmail(email) == null ;
// 	}

// }