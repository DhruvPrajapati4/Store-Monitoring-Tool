/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhruv.storeMonitoring.controller;

import com.dhruv.storeMonitoring.entities.Report;
import com.dhruv.storeMonitoring.entities.Report.ReportStatus;
import com.dhruv.storeMonitoring.entities.StoreReport;
import com.dhruv.storeMonitoring.service.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dhruv
 */
@RestController
public class ReportController {

    private final ReportService reportService;
    private final String homeEndpoint = "/store-monitoring";
    private final String triggerReportEndpoint = homeEndpoint + "/trigger_report";
    private final String getReportEndpoint = homeEndpoint + "/get_report/{reportId}";

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(homeEndpoint)
    public ResponseEntity<?> homePage() {
        return ResponseEntity.ok("Hey there! Welcome to Store monitoring tool. \nUse: " + triggerReportEndpoint + " to generate report. \nUse: " + getReportEndpoint + " to get the report");
    }

    @PostMapping(triggerReportEndpoint)
    public ResponseEntity<String> triggerReport() {
        List<StoreReport> reports = reportService.generateReport();
        // Store report data and return report ID
        String reportId = reportService.saveReportToDatabase(reports);
        return ResponseEntity.ok(reportId);
    }

    @GetMapping(getReportEndpoint)
    public ResponseEntity<?> getReport(@PathVariable String reportId) {
        Report report = reportService.getReportFromDatabase(reportId);

        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Such report with report ID= " + reportId + " is found! Try with valid reportId!");
        }
        
        String filePath = report.getCsvData();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv");

        // Create a FileSystemResource using the file path
        FileSystemResource resource = new FileSystemResource(filePath);

        if (report.getStatus().equals(ReportStatus.Completed)) {
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity.ok()
                    .body("Running");
        }
    }
}
