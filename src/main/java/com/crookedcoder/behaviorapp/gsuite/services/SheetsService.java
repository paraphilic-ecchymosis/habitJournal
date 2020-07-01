package com.crookedcoder.behaviorapp.gsuite.services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.crookedcoder.behaviorapp.gsuite.GSuiteAuthorizeUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

import org.springframework.stereotype.Service;

@Service
public class SheetsService {

    private static String APPLICATION_NAME = "Behavior App";

    public Sheets getSheetsService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = GSuiteAuthorizeUtil.authorizeUser(HTTP_TRANSPORT);
        return new Sheets.Builder(
          GoogleNetHttpTransport.newTrustedTransport(), 
          JacksonFactory.getDefaultInstance(), credential)
          .setApplicationName(APPLICATION_NAME)
          .build();
    }
}