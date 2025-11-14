package com.crookedcoder.habitjournal.service.impl;

import com.crookedcoder.habitjournal.dto.AnalyticsResponse;
import com.crookedcoder.habitjournal.exception.ResourceNotFoundException;
import com.crookedcoder.habitjournal.model.Entry;
import com.crookedcoder.habitjournal.model.Habit;
import com.crookedcoder.habitjournal.model.Milestone;
import com.crookedcoder.habitjournal.repository.EntriesRepository;
import com.crookedcoder.habitjournal.repository.HabitRepository;
import com.crookedcoder.habitjournal.repository.MilestoneRepository;
import com.crookedcoder.habitjournal.service.AnalyticsService;
import com.crookedcoder.habitjournal.service.ExportService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of Export Service.
 */
@Service
public class ExportServiceImpl implements ExportService {

    private final HabitRepository habitRepository;
    private final EntriesRepository entriesRepository;
    private final MilestoneRepository milestoneRepository;
    private final AnalyticsService analyticsService;

    public ExportServiceImpl(
            HabitRepository habitRepository,
            EntriesRepository entriesRepository,
            MilestoneRepository milestoneRepository,
            AnalyticsService analyticsService) {
        this.habitRepository = habitRepository;
        this.entriesRepository = entriesRepository;
        this.milestoneRepository = milestoneRepository;
        this.analyticsService = analyticsService;
    }

    @Override
    public ByteArrayOutputStream exportHabitsToCSV() {
        List<Habit> habits = habitRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Habits");

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Description", "Frequency", "Start Date", "Goal Units"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }

            // Data rows
            int rowNum = 1;
            for (Habit habit : habits) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(habit.getId());
                row.createCell(1).setCellValue(habit.getName());
                row.createCell(2).setCellValue(habit.getDescription());
                row.createCell(3).setCellValue(habit.getFrequency());
                row.createCell(4).setCellValue(habit.getStartDate() != null ? habit.getStartDate().toString() : "");
                row.createCell(5).setCellValue(habit.getGoalUnits() != null ? habit.getGoalUnits() : 0);
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out;
        } catch (IOException e) {
            throw new RuntimeException("Failed to export habits to CSV", e);
        }
    }

    @Override
    public ByteArrayOutputStream exportEntriesToCSV() {
        List<Entry> entries = entriesRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Entries");

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Habit ID", "Entry", "Entry Date", "Completed", "Notes"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }

            // Data rows
            int rowNum = 1;
            for (Entry entry : entries) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getId());
                row.createCell(1).setCellValue(entry.getHabitId() != null ? entry.getHabitId() : "");
                row.createCell(2).setCellValue(entry.getEntry());
                row.createCell(3).setCellValue(entry.getEntryDate() != null ? entry.getEntryDate().toString() : "");
                row.createCell(4).setCellValue(entry.isCompleted() ? "Yes" : "No");
                row.createCell(5).setCellValue(entry.getNotes() != null ? entry.getNotes() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out;
        } catch (IOException e) {
            throw new RuntimeException("Failed to export entries to CSV", e);
        }
    }

    @Override
    public ByteArrayOutputStream exportMilestonesToCSV() {
        List<Milestone> milestones = milestoneRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Milestones");

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Habit ID", "Name", "Description", "Status", "Goal Units", "Completed Units", "Due Date"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }

            // Data rows
            int rowNum = 1;
            for (Milestone milestone : milestones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(milestone.getId());
                row.createCell(1).setCellValue(milestone.getHabitId() != null ? milestone.getHabitId() : "");
                row.createCell(2).setCellValue(milestone.getName());
                row.createCell(3).setCellValue(milestone.getDescription());
                row.createCell(4).setCellValue(milestone.getStatus().toString());
                row.createCell(5).setCellValue(milestone.getGoalUnits() != null ? milestone.getGoalUnits() : 0);
                row.createCell(6).setCellValue(milestone.getCompletedUnits() != null ? milestone.getCompletedUnits() : 0);
                row.createCell(7).setCellValue(milestone.getDueDate() != null ? milestone.getDueDate().toString() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out;
        } catch (IOException e) {
            throw new RuntimeException("Failed to export milestones to CSV", e);
        }
    }

    @Override
    public ByteArrayOutputStream exportComprehensiveReportToPDF() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph title = new Paragraph("Habit Journal - Comprehensive Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Date
            Paragraph date = new Paragraph("Generated: " + LocalDate.now().toString());
            date.setAlignment(Element.ALIGN_CENTER);
            date.setSpacingAfter(30);
            document.add(date);

            // Get analytics
            AnalyticsResponse analytics = analyticsService.getAnalytics();

            // Overall Statistics
            addSectionTitle(document, "Overall Statistics");
            PdfPTable statsTable = new PdfPTable(2);
            statsTable.setWidthPercentage(100);
            addTableRow(statsTable, "Total Habits", String.valueOf(analytics.habitStats().totalHabits()));
            addTableRow(statsTable, "Active Habits", String.valueOf(analytics.habitStats().activeHabits()));
            addTableRow(statsTable, "Total Entries", String.valueOf(analytics.habitStats().totalEntries()));
            addTableRow(statsTable, "Completed Milestones", String.valueOf(analytics.habitStats().completedMilestones()));
            addTableRow(statsTable, "Overall Completion Rate", String.format("%.2f%%", analytics.progressSummary().overallCompletionRate()));
            addTableRow(statsTable, "Total Days Tracked", String.valueOf(analytics.progressSummary().totalDaysTracked()));
            document.add(statsTable);
            document.add(new Paragraph("\n"));

            // Streaks
            addSectionTitle(document, "Habit Streaks");
            PdfPTable streakTable = new PdfPTable(3);
            streakTable.setWidthPercentage(100);
            addTableHeader(streakTable, "Habit Name", "Current Streak", "Longest Streak");
            for (AnalyticsResponse.StreakInfo streak : analytics.streaks()) {
                addTableRow(streakTable,
                    streak.habitName(),
                    String.valueOf(streak.currentStreak()) + " days",
                    String.valueOf(streak.longestStreak()) + " days"
                );
            }
            document.add(streakTable);
            document.add(new Paragraph("\n"));

            // Completion Rates
            addSectionTitle(document, "Completion Rates");
            PdfPTable rateTable = new PdfPTable(3);
            rateTable.setWidthPercentage(100);
            addTableHeader(rateTable, "Habit Name", "Completion Rate", "Entries");
            for (AnalyticsResponse.CompletionRate rate : analytics.completionRates()) {
                addTableRow(rateTable,
                    rate.habitName(),
                    String.format("%.2f%%", rate.rate()),
                    String.format("%d / %d", rate.completedEntries(), rate.totalEntries())
                );
            }
            document.add(rateTable);

            document.close();
            return out;
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF report", e);
        }
    }

    @Override
    public ByteArrayOutputStream exportHabitProgressToPDF(String habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with id: " + habitId));

        List<Entry> entries = entriesRepository.findByHabitId(habitId);
        AnalyticsResponse.StreakInfo streak = analyticsService.getHabitStreak(habitId);
        AnalyticsResponse.CompletionRate completionRate = analyticsService.getHabitCompletionRate(habitId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Paragraph title = new Paragraph("Habit Progress Report: " + habit.getName(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Habit Details
            addSectionTitle(document, "Habit Details");
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);
            addTableRow(detailsTable, "Name", habit.getName());
            addTableRow(detailsTable, "Description", habit.getDescription());
            addTableRow(detailsTable, "Frequency", habit.getFrequency());
            addTableRow(detailsTable, "Start Date", habit.getStartDate() != null ? habit.getStartDate().toString() : "N/A");
            document.add(detailsTable);
            document.add(new Paragraph("\n"));

            // Progress
            addSectionTitle(document, "Progress");
            PdfPTable progressTable = new PdfPTable(2);
            progressTable.setWidthPercentage(100);
            addTableRow(progressTable, "Completion Rate", String.format("%.2f%%", completionRate.rate()));
            addTableRow(progressTable, "Total Entries", String.valueOf(completionRate.totalEntries()));
            addTableRow(progressTable, "Completed Entries", String.valueOf(completionRate.completedEntries()));
            addTableRow(progressTable, "Current Streak", streak.currentStreak() + " days");
            addTableRow(progressTable, "Longest Streak", streak.longestStreak() + " days");
            addTableRow(progressTable, "Last Entry", streak.lastEntryDate());
            document.add(progressTable);

            document.close();
            return out;
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate habit progress PDF", e);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private void addSectionTitle(Document document, String title) throws DocumentException {
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph section = new Paragraph(title, sectionFont);
        section.setSpacingBefore(10);
        section.setSpacingAfter(10);
        document.add(section);
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    private void addTableRow(PdfPTable table, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value));
            cell.setPadding(5);
            table.addCell(cell);
        }
    }
}
