package com.crookedcoder.persistence.config;

import java.io.IOException;
import java.io.InputStreamReader;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfig {

    @Value("${spring.application.name}")
    public String applicationName;

        /* GSuite Config */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Bean
    GoogleClientSecrets clientSecrets() throws IOException {
        Resource credentials = new ClassPathResource("credentials.json");
        return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(credentials.getInputStream()));
    }
    
    // TODO: Need message properties
    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
}