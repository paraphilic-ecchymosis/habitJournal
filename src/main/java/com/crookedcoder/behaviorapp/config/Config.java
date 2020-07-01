package com.crookedcoder.behaviorapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
public @Data class Config {

    @Autowired
    @Value("${application.name}")
    private String applicationName;

}