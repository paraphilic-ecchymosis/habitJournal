package com.crookedcoder.habitjournal.service;

import com.crookedcoder.habitjournal.model.User;

/**
 * Service for sending email notifications.
 */
public interface EmailService {

    /**
     * Send habit reminder email to user.
     */
    void sendHabitReminder(User user, String habitName);

    /**
     * Send weekly progress summary email.
     */
    void sendWeeklyProgressSummary(User user);

    /**
     * Send milestone achievement email.
     */
    void sendMilestoneAchievement(User user, String milestoneName);

    /**
     * Send welcome email to new user.
     */
    void sendWelcomeEmail(User user);
}
