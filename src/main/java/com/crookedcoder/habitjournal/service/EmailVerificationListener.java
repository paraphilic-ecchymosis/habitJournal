package com.crookedcoder.habitjournal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.crookedcoder.habitjournal.event.UserRegistrationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationListener  implements ApplicationListener<UserRegistrationEvent> {
    
    private JavaMailSender mailSender;
	private VerificationService verificationService;
	private boolean disableEmailVerification = true;
	
	@Override
	public void onApplicationEvent(UserRegistrationEvent event) {
		if(disableEmailVerification) {
			return;
		}
		String username = event.getUser().getUsername();
		String verificationId = verificationService.createVerification(username);		
		String email = event.getUser().getEmail();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("HAbit Journal Account Verification");
		message.setText("Account activation link: https://localhost:8443/verify/email?id="+verificationId);
		message.setTo(email);
		mailSender.send(message);
	}
}