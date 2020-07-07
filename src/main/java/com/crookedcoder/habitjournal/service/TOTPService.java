package com.crookedcoder.habitjournal.service;

import com.crookedcoder.habitjournal.entity.HabitJournalUser;
import com.crookedcoder.habitjournal.entity.TOTPDetails;
import com.crookedcoder.habitjournal.exceptions.InvalidTOTPVerificationCode;
import com.crookedcoder.habitjournal.repository.TOTPRepository;
import com.crookedcoder.habitjournal.repository.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import org.springframework.stereotype.Service;


@Service
public class TOTPService {
    
    private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();
	private final TOTPRepository totpRepository;
	private final UserRepository userRepository;
	private static final String ISSUER = "Habitjournal.dev";
	
	
	public TOTPService(TOTPRepository totpRepository, UserRepository userRepository) {
		this.totpRepository=totpRepository;
		this.userRepository=userRepository;
	}
	
	public String generateNewGoogleAuthQrUrl(String username) {	
		GoogleAuthenticatorKey authKey = googleAuth.createCredentials();
		String secret = authKey.getKey();
		totpRepository.deleteByUsername(username);
		totpRepository.save(new TOTPDetails(username, secret));
		return GoogleAuthenticatorQRGenerator.getOtpAuthURL(ISSUER, username, authKey);
	}
	
	public boolean isTotpEnabled(String username) {
		return userRepository.findByUsername(username).isTotpEnabled();
	}
	
	public void enableTOTPForUser(String username, int code) {
		if(!verifyCode(username, code)) {
			throw new InvalidTOTPVerificationCode("Verification code is Invalid");
		}
		HabitJournalUser user = userRepository.findByUsername(username);
		user.setTotpEnabled(true);
		userRepository.save(user);
	}
	
	public boolean verifyCode(String username, int code) {
		TOTPDetails totpDetails = totpRepository.findByUsername(username);
		return googleAuth.authorize(totpDetails.getSecret(), code);
	}
	

}