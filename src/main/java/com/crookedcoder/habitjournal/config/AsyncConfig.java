package com.crookedcoder.habitjournal.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Async configuration using Java 21 virtual threads for lightweight concurrency
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Configure virtual thread executor for async operations
     * Virtual threads (Project Loom) provide lightweight concurrency without OS thread overhead
     */
    @Bean(name = "virtualThreadExecutor")
    public Executor virtualThreadExecutor() {
        // Use Java 21 virtual threads for async operations
        return java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * Traditional thread pool executor for comparison/fallback
     * Can be used for CPU-intensive tasks where virtual threads aren't ideal
     */
    @Bean(name = "threadPoolExecutor")
    public Executor threadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("habit-journal-");
        executor.initialize();
        return executor;
    }
}
