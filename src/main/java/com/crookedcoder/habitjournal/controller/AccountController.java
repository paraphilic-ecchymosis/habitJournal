package com.crookedcoder.habitjournal.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.crookedcoder.habitjournal.annotations.HabitJournalPrincipal;
import com.crookedcoder.habitjournal.exceptions.InvalidTOTPVerificationCode;
import com.crookedcoder.habitjournal.model.TotpCode;
import com.crookedcoder.habitjournal.service.TOTPService;
import com.crookedcoder.habitjournal.userdetails.MFAUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {

	private final TOTPService totpService;
	
	@GetMapping("/account")
	public String getAccount(Model model,@AuthenticationPrincipal MFAUser user ) {
		if (user != null) { 
			model.addAttribute("totpEnabled",user.isTotpEnabled());
		} else {
			model.addAttribute("totpEnabled",true);
		}
		return "account";
	}
	
	@GetMapping("/setup-totp")
	public String getGoogleAuthenticatorQRUrl(Model model, @HabitJournalPrincipal MFAUser authPrincipal) {
		String username = authPrincipal.getUsername();
		boolean userHasTotpEnabled = authPrincipal.isTotpEnabled();
		if(!userHasTotpEnabled) {
			model.addAttribute("qrUrl",totpService.generateNewGoogleAuthQrUrl(username));
			model.addAttribute("codeDto", new TotpCode());
		}
		model.addAttribute("totpEnabled",userHasTotpEnabled);
		return "account";
	}	

	@PostMapping("/confirm-totp")
	public String confirmGoogleAuthenticatorSetup(Model model, @AuthenticationPrincipal MFAUser user, @ModelAttribute("codeDto") TotpCode codeDto) {
		boolean userHasTotpEnabled = user.isTotpEnabled();
		if(!userHasTotpEnabled) {
			totpService.enableTOTPForUser(user.getUsername(), Integer.valueOf(codeDto.getCode()));
			model.addAttribute("totpEnabled",true);
		}
		return "account";
	}
	
	@ExceptionHandler(InvalidTOTPVerificationCode.class)
	public String handleInvalidTOTPVerificationCode(Model model, @AuthenticationPrincipal MFAUser user) {
		boolean userHasTotpEnabled = user.isTotpEnabled();
		model.addAttribute("totpEnabled",userHasTotpEnabled);
		model.addAttribute("confirmError",true);
		return "account";
	}
	
}