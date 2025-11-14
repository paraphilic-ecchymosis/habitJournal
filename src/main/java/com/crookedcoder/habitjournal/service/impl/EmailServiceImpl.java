package com.crookedcoder.habitjournal.service.impl;

import com.crookedcoder.habitjournal.dto.AnalyticsResponse;
import com.crookedcoder.habitjournal.model.User;
import com.crookedcoder.habitjournal.service.AnalyticsService;
import com.crookedcoder.habitjournal.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Implementation of Email Service using Spring Mail.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final AnalyticsService analyticsService;

    @Value("${spring.mail.username:noreply@habitjournal.com}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender, AnalyticsService analyticsService) {
        this.mailSender = mailSender;
        this.analyticsService = analyticsService;
    }

    @Override
    @Async("virtualThreadExecutor")
    public void sendHabitReminder(User user, String habitName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Reminder: Time to complete " + habitName);
        message.setText(String.format("""
            Hi %s,

            This is a friendly reminder to complete your habit: %s

            Keep up the great work! Consistency is the key to success.

            Best regards,
            Habit Journal Team
            """, user.getFirstname(), habitName));

        mailSender.send(message);
    }

    @Override
    @Async("virtualThreadExecutor")
    public void sendWeeklyProgressSummary(User user) {
        AnalyticsResponse analytics = analyticsService.getAnalytics();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Your Weekly Habit Progress Summary");
        message.setText(String.format("""
            Hi %s,

            Here's your weekly habit progress summary:

            📊 Statistics:
            - Total Habits: %d
            - Active Habits: %d
            - Total Entries: %d
            - Completed Milestones: %d

            📈 Progress:
            - Overall Completion Rate: %.2f%%
            - Total Days Tracked: %d
            - Most Consistent Habit: %s

            Keep pushing forward! Every small step counts toward your goals.

            Best regards,
            Habit Journal Team
            """,
            user.getFirstname(),
            analytics.habitStats().totalHabits(),
            analytics.habitStats().activeHabits(),
            analytics.habitStats().totalEntries(),
            analytics.habitStats().completedMilestones(),
            analytics.progressSummary().overallCompletionRate(),
            analytics.progressSummary().totalDaysTracked(),
            analytics.progressSummary().mostConsistentHabit()
        ));

        mailSender.send(message);
    }

    @Override
    @Async("virtualThreadExecutor")
    public void sendMilestoneAchievement(User user, String milestoneName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("🎉 Congratulations! Milestone Achieved!");
        message.setText(String.format("""
            Hi %s,

            🎉 Congratulations! You've achieved a milestone: %s

            This is a significant accomplishment. Take a moment to celebrate your progress!

            Your dedication and consistency are paying off. Keep up the excellent work!

            Best regards,
            Habit Journal Team
            """, user.getFirstname(), milestoneName));

        mailSender.send(message);
    }

    @Override
    @Async("virtualThreadExecutor")
    public void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Welcome to Habit Journal!");
        message.setText(String.format("""
            Hi %s,

            Welcome to Habit Journal! 🎉

            We're excited to have you on board. Habit Journal is your personal companion for building
            better habits and achieving your goals.

            Getting Started:
            1. Create your first habit
            2. Log daily entries to track your progress
            3. Set milestones to mark important achievements
            4. Use AI-powered insights to optimize your habit formation

            Here's to building a better you, one habit at a time!

            Best regards,
            Habit Journal Team
            """, user.getFirstname()));

        mailSender.send(message);
    }
}
