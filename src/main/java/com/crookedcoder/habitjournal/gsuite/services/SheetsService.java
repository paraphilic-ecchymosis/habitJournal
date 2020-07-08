package com.crookedcoder.habitjournal.gsuite.services;

import java.security.GeneralSecurityException;

import com.crookedcoder.habitjournal.gsuite.GSuiteAuthorizeServiceImpl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SheetsService {

    // @Autowired
    // private String applicationName;


    GSuiteAuthorizeServiceImpl gSuiteAuthorizeServiceImpl;

    // public Sheets getSheetsService() throws Exception, GeneralSecurityException {
    //     final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    //     Credential credential = gSuiteAuthorizeServiceImpl.authorizeUser(HTTP_TRANSPORT);
    //     return new Sheets.Builder(
    //       GoogleNetHttpTransport.newTrustedTransport(), 
    //       JacksonFactory.getDefaultInstance(), credential)
    //       .setApplicationName(applicationName)
    //       .build();
    // }
}