package com.crookedcoder.habitjournal.service.impl;

import com.crookedcoder.habitjournal.service.PersonaFrameworkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of PersonaFrameworkClient using REST API calls.
 * Connects to the Persona-Framework ecosystem for AI-powered features.
 */
@Service
public class PersonaFrameworkClientImpl implements PersonaFrameworkClient {

    private final RestTemplate restTemplate;

    @Value("${persona.framework.api.url:http://localhost:5000/api}")
    private String apiUrl;

    @Value("${persona.framework.api.key:}")
    private String apiKey;

    @Value("${persona.framework.enabled:false}")
    private boolean enabled;

    public PersonaFrameworkClientImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String sendPrompt(String systemPrompt, String userPrompt) {
        if (!enabled || !isAvailable()) {
            return getFallbackResponse();
        }

        try {
            // Construct request to Persona-Framework
            // TODO: Update this based on actual Persona-Framework API specification
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("system_prompt", systemPrompt);
            requestBody.put("user_prompt", userPrompt);
            requestBody.put("persona", "habit_coach"); // Default persona

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (apiKey != null && !apiKey.isEmpty()) {
                headers.setBearerAuth(apiKey);
            }

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl + "/generate",
                request,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // TODO: Update this based on actual Persona-Framework response structure
                return (String) response.getBody().getOrDefault("content", getFallbackResponse());
            }

            return getFallbackResponse();

        } catch (RestClientException e) {
            System.err.println("Error communicating with Persona-Framework: " + e.getMessage());
            return getFallbackResponse();
        }
    }

    @Override
    public PersonaFrameworkResponse sendStructuredRequest(PersonaFrameworkRequest request) {
        if (!enabled || !isAvailable()) {
            return new PersonaFrameworkResponse(
                getFallbackResponse(),
                "fallback",
                0.0,
                "Persona-Framework not available"
            );
        }

        try {
            // Construct structured request
            // TODO: Update this based on actual Persona-Framework API specification
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("persona", request.persona());
            requestBody.put("system_context", request.systemContext());
            requestBody.put("user_query", request.userQuery());
            requestBody.put("response_format", request.responseFormat());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (apiKey != null && !apiKey.isEmpty()) {
                headers.setBearerAuth(apiKey);
            }

            HttpEntity<Map<String, Object>> httpRequest = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl + "/structured",
                httpRequest,
                Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                return new PersonaFrameworkResponse(
                    (String) body.getOrDefault("content", ""),
                    (String) body.getOrDefault("persona", request.persona()),
                    ((Number) body.getOrDefault("confidence", 0.0)).doubleValue(),
                    (String) body.getOrDefault("metadata", "")
                );
            }

            return new PersonaFrameworkResponse(getFallbackResponse(), "fallback", 0.0, "Error");

        } catch (RestClientException e) {
            System.err.println("Error communicating with Persona-Framework: " + e.getMessage());
            return new PersonaFrameworkResponse(
                getFallbackResponse(),
                "fallback",
                0.0,
                "Exception: " + e.getMessage()
            );
        }
    }

    @Override
    public boolean isAvailable() {
        if (!enabled) {
            return false;
        }

        try {
            // Check if Persona-Framework is reachable
            // TODO: Update this based on actual Persona-Framework health endpoint
            ResponseEntity<String> response = restTemplate.getForEntity(
                apiUrl + "/health",
                String.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    private String getFallbackResponse() {
        return "AI insights are currently unavailable. Please ensure the Persona-Framework is configured and running.";
    }
}
