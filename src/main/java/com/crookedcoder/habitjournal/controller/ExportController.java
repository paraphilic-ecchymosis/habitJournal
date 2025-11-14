package com.crookedcoder.habitjournal.controller;

import com.crookedcoder.habitjournal.service.ExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

/**
 * REST controller for exporting data to various formats.
 */
@RestController
@RequestMapping("/api/export")
@PreAuthorize("isAuthenticated()")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    /**
     * GET /api/export/habits/csv
     * Export all habits to CSV (Excel) format.
     */
    @GetMapping("/habits/csv")
    public ResponseEntity<byte[]> exportHabitsToCSV() {
        ByteArrayOutputStream out = exportService.exportHabitsToCSV();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "habits_" + LocalDate.now() + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(out.toByteArray());
    }

    /**
     * GET /api/export/entries/csv
     * Export all entries to CSV (Excel) format.
     */
    @GetMapping("/entries/csv")
    public ResponseEntity<byte[]> exportEntriesToCSV() {
        ByteArrayOutputStream out = exportService.exportEntriesToCSV();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "entries_" + LocalDate.now() + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(out.toByteArray());
    }

    /**
     * GET /api/export/milestones/csv
     * Export all milestones to CSV (Excel) format.
     */
    @GetMapping("/milestones/csv")
    public ResponseEntity<byte[]> exportMilestonesToCSV() {
        ByteArrayOutputStream out = exportService.exportMilestonesToCSV();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "milestones_" + LocalDate.now() + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(out.toByteArray());
    }

    /**
     * GET /api/export/report/pdf
     * Export comprehensive report to PDF format.
     */
    @GetMapping("/report/pdf")
    public ResponseEntity<byte[]> exportComprehensiveReportToPDF() {
        ByteArrayOutputStream out = exportService.exportComprehensiveReportToPDF();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "habit_report_" + LocalDate.now() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(out.toByteArray());
    }

    /**
     * GET /api/export/habit/{habitId}/pdf
     * Export specific habit progress report to PDF.
     */
    @GetMapping("/habit/{habitId}/pdf")
    public ResponseEntity<byte[]> exportHabitProgressToPDF(@PathVariable String habitId) {
        ByteArrayOutputStream out = exportService.exportHabitProgressToPDF(habitId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "habit_progress_" + habitId + "_" + LocalDate.now() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(out.toByteArray());
    }
}
