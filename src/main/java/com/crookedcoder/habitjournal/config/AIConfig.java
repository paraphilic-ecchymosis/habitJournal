package com.crookedcoder.habitjournal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for AI integration through Persona-Framework.
 * All AI capabilities are provided by the Persona-Framework ecosystem.
 */
@Configuration
public class AIConfig {

    /**
     * RestTemplate bean for HTTP communication with Persona-Framework.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
