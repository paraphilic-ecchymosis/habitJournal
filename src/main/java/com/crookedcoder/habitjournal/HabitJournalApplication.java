package com.crookedcoder.habitjournal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HabitJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitJournalApplication.class, args);

	}
}
