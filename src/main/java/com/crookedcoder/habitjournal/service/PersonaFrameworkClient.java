package com.crookedcoder.habitjournal.service;

/**
 * Client for communicating with the Persona-Framework ecosystem.
 * Handles all AI-powered features through the Persona-Framework API.
 */
public interface PersonaFrameworkClient {

    /**
     * Send a prompt to the Persona-Framework and get AI-generated response.
     *
     * @param systemPrompt The system context/instructions
     * @param userPrompt The user's specific request
     * @return The AI-generated response text
     */
    String sendPrompt(String systemPrompt, String userPrompt);

    /**
     * Send a structured request to the Persona-Framework.
     *
     * @param request The structured request object
     * @return The AI-generated response
     */
    PersonaFrameworkResponse sendStructuredRequest(PersonaFrameworkRequest request);

    /**
     * Check if the Persona-Framework is available and configured.
     *
     * @return true if available, false otherwise
     */
    boolean isAvailable();

    /**
     * Request object for Persona-Framework API.
     */
    record PersonaFrameworkRequest(
        String persona,
        String systemContext,
        String userQuery,
        String responseFormat
    ) {}

    /**
     * Response object from Persona-Framework API.
     */
    record PersonaFrameworkResponse(
        String content,
        String persona,
        double confidence,
        String metadata
    ) {}
}
