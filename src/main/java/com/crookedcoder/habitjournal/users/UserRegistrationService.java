// package com.crookedcoder.habitjournal.service;

// import com.crookedcoder.habitjournal.entity.HjUser;
// import com.crookedcoder.habitjournal.event.UserRegistrationEvent;
// import com.crookedcoder.habitjournal.model.UserDto;
// import com.crookedcoder.habitjournal.repository.HjUserRepository;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.ApplicationEventPublisher;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class UserRegistrationService {
    
//     private HjUserRepository repository;
// 	private final PasswordEncoder encoder;
// 	private final ApplicationEventPublisher eventPublisher;
// 	private final boolean TOTP_ENABLED = false;
// 	private boolean disableEmailVerification = true;
	
// 	public void registerNewUser(UserDto user) {
// 		HjUser habitJournalUser = new HjUser(
// 				user.getUsername(), 
// 				user.getFirstname(), 
// 				user.getLastname(),
// 				user.getEmail(), 
// 				encoder.encode(user.getPassword()),
// 				encoder.encode(String.valueOf(user.getSecurityPin())),
// 				TOTP_ENABLED
// 		);
// 		habitJournalUser.setVerified(disableEmailVerification);
// 		repository.save(habitJournalUser);
// 		eventPublisher.publishEvent(new UserRegistrationEvent(habitJournalUser));
// 	}
// }