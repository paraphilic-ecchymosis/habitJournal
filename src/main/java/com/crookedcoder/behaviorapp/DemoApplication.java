package com.crookedcoder.behaviorapp;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.crookedcoder.behaviorapp.gsuite.Config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(DemoApplication.class, args);
		// Not prompting for new credentials?
		Config.getJournal();
	}

}
