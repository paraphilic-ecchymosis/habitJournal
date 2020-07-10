// package com.crookedcoder.habitjournal.service;

// import com.crookedcoder.habitjournal.entity.HjUser;
// import com.crookedcoder.habitjournal.entity.TOTPDetails;
// import com.crookedcoder.habitjournal.exceptions.InvalidTOTPVerificationCode;
// import com.crookedcoder.habitjournal.repository.TOTPDetailsRepository;
// import com.crookedcoder.habitjournal.repository.HjUserRepository;
// import com.warrenstrange.googleauth.GoogleAuthenticator;
// import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
// import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

// import org.springframework.stereotype.Service;


// @Service
// public class TotpQueryServiceNoSql implements TotpQueryService {
    
// 	private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();
// 	private TOTPDetailsRepository totpDetailsRepository;
// 	private HjUserRepository userRepository;
// 	private static final String ISSUER = "Habitjournal.dev";
	
// 	public String generateNewGoogleAuthQrUrl(String username) {	
// 		GoogleAuthenticatorKey authKey = googleAuth.createCredentials();
// 		String secret = authKey.getKey();
// 		totpDetailsRepository.deleteByUsername(username);
// 		totpDetailsRepository.save(new TOTPDetails(username, secret));
// 		return GoogleAuthenticatorQRGenerator.getOtpAuthURL(ISSUER, username, authKey);
// 	}
	
// 	public boolean isTotpEnabled(String username) {
// 		return userRepository.findByUsername(username).isTotpEnabled();
// 	}
	
// 	public void enableTOTPForUser(String username, int code) {
// 		if(!verifyCode(username, code)) {
// 			throw new InvalidTOTPVerificationCode("Verification code is Invalid");
// 		}
// 		HjUser user = userRepository.findByUsername(username);
// 		user.setTotpEnabled(true);
// 		userRepository.save(user);
// 	}
	
// 	public boolean verifyCode(String username, int code) {
// 		TOTPDetails totpDetails = totpDetailsRepository.findByUsername(username);
// 		return googleAuth.authorize(totpDetails.getSecret(), code);
// 	}
	

// }