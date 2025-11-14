package com.crookedcoder.habitjournal.service.impl;

import com.crookedcoder.habitjournal.model.Habit;
import com.crookedcoder.habitjournal.model.User;
import com.crookedcoder.habitjournal.repository.HabitRepository;
import com.crookedcoder.habitjournal.repository.UserRepository;
import com.crookedcoder.habitjournal.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for scheduling automatic email notifications.
 */
@Service
public class NotificationSchedulerService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;

    public NotificationSchedulerService(
            EmailService emailService,
            UserRepository userRepository,
            HabitRepository habitRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
    }

    /**
     * Send daily habit reminders at 9 AM.
     * Runs daily at 9:00 AM server time.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyReminders() {
        List<User> users = userRepository.findAll();
        List<Habit> habits = habitRepository.findAll();

        for (User user : users) {
            // Find user's habits with DAILY frequency
            List<Habit> dailyHabits = habits.stream()
                    .filter(h -> "DAILY".equals(h.getFrequency()))
                    .toList();

            for (Habit habit : dailyHabits) {
                emailService.sendHabitReminder(user, habit.getName());
            }
        }
    }

    /**
     * Send weekly progress summaries every Sunday at 6 PM.
     * Runs every Sunday at 18:00 server time.
     */
    @Scheduled(cron = "0 0 18 * * SUN")
    public void sendWeeklySummaries() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            emailService.sendWeeklyProgressSummary(user);
        }
    }
}
