package com.crookedcoder.habitjournal.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Spring AI integration.
 * Provides AI-powered insights and coaching features.
 */
@Configuration
public class AIConfig {

    /**
     * Creates a ChatClient bean for AI interactions.
     * Uses OpenAI's GPT models for generating insights and coaching.
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                    You are an expert habit coach and behavioral psychologist.
                    Your role is to analyze user habits and provide:
                    - Insightful analysis of patterns and trends
                    - Personalized, actionable coaching advice
                    - Motivational support and encouragement
                    - Evidence-based strategies for habit formation

                    Always be supportive, constructive, and specific in your recommendations.
                    Keep responses concise but meaningful (2-4 sentences).
                    """)
                .build();
    }
}
