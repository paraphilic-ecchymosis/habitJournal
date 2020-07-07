package com.crookedcoder.habitjournal.userdetails;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.crookedcoder.habitjournal.service.JournalCommandService;

import lombok.RequiredArgsConstructor;

@Component("oauth2authSuccessHandler")
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private final JournalCommandService journalService;
	private final RedirectStrategy redirectStrategy;
	private final OAuth2AuthorizedClientService authorizedClientService;
	 
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if(!this.journalService.userHasAJournal(authentication.getName())) {
			this.journalService.createNewJournal(authentication.getName());
			if (authentication instanceof OAuth2AuthenticationToken) {
				OAuth2AuthenticationToken oToken = (OAuth2AuthenticationToken)authentication;
		}
		}
		this.redirectStrategy.sendRedirect(request, response, "/portfolio");
	}
}