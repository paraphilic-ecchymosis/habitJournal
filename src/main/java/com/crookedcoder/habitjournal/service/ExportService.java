package com.crookedcoder.habitjournal.service;

import java.io.ByteArrayOutputStream;

/**
 * Service for exporting habit data to various formats.
 */
public interface ExportService {

    /**
     * Export all habits to CSV format.
     */
    ByteArrayOutputStream exportHabitsToCSV();

    /**
     * Export all entries to CSV format.
     */
    ByteArrayOutputStream exportEntriesToCSV();

    /**
     * Export all milestones to CSV format.
     */
    ByteArrayOutputStream exportMilestonesToCSV();

    /**
     * Export comprehensive report to PDF format.
     */
    ByteArrayOutputStream exportComprehensiveReportToPDF();

    /**
     * Export habit progress report to PDF.
     */
    ByteArrayOutputStream exportHabitProgressToPDF(String habitId);
}
