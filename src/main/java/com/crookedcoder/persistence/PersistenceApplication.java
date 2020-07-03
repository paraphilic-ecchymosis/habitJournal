package com.crookedcoder.persistence;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PersistenceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(PersistenceApplication.class, args);

	}

}
