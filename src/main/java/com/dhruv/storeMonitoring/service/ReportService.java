/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhruv.storeMonitoring.service;

import com.dhruv.storeMonitoring.entities.BusinessHours;
import com.dhruv.storeMonitoring.entities.Report;
import com.dhruv.storeMonitoring.entities.Report.ReportStatus;
import com.dhruv.storeMonitoring.entities.StoreReport;
import com.dhruv.storeMonitoring.entities.StoreStatus;
import com.dhruv.storeMonitoring.entities.StoreTimezone;
import com.dhruv.storeMonitoring.repository.BusinessHoursRepository;
import com.dhruv.storeMonitoring.repository.ReportRepository;
import com.dhruv.storeMonitoring.repository.StoreStatusRepository;
import com.dhruv.storeMonitoring.repository.StoreTimezoneRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dhruv
 */
@Service
public class ReportService {

    private final StoreStatusRepository storeStatusRepository;
    private final BusinessHoursRepository businessHoursRepository;
    private final StoreTimezoneRepository storeTimezoneRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(
            StoreStatusRepository storeStatusRepository,
            BusinessHoursRepository businessHoursRepository,
            StoreTimezoneRepository storeTimezoneRepository,
            ReportRepository reportRepository) {
        this.storeStatusRepository = storeStatusRepository;
        this.businessHoursRepository = businessHoursRepository;
        this.storeTimezoneRepository = storeTimezoneRepository;
        this.reportRepository = reportRepository;
    }

    private List<StoreReport> reportList = new ArrayList<>();

    public List<StoreReport> generateReport() {
        List<StoreStatus> storeStatuses = storeStatusRepository.findAll();
        List<BusinessHours> businessHoursList = businessHoursRepository.findAll();
        List<StoreTimezone> timezones = storeTimezoneRepository.findAll();
        Map<Long, String> timezoneMap = new HashMap<>();
        timezoneMap = createTimezoneMap(timezones);

        // Calculate and set the uptime and downtime attributes
        for (StoreStatus status : storeStatuses) {
            try {
                String timestampText = status.getTimestampUtc();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS 'UTC'");
                LocalDateTime timestampUtc = LocalDateTime.parse(timestampText, formatter);
                Long storeId = status.getStoreId();
                StoreReport report = new StoreReport(storeId);

                BusinessHours businessHours = findBusinessHoursByStoreAndDay(businessHoursList, storeId);
                if (businessHours == null) {
                    // If business hours not found, assume 24x7
                    businessHours = new BusinessHours(storeId, -1,
                            LocalTime.MIN, LocalTime.MAX);
                }

                String timezone = timezoneMap.getOrDefault(storeId, "America/Chicago");
                if (timezone == null) {
                    // If timezone not found, use America/Chicago
                    timezone = "America/Chicago";
                }

                LocalDateTime localTime = convertToStoreLocalTime(timestampUtc, timezone);

                if (isWithinBusinessHours(localTime, businessHours)) {
                    // Calculate time interval
                    LocalDateTime businessStartTime = localTime.toLocalDate().atTime(businessHours.getStartTimeLocal());
                    LocalDateTime businessEndTime = localTime.toLocalDate().atTime(businessHours.getEndTimeLocal());

                    Duration interval = Duration.between(businessStartTime, businessEndTime);

                    // Check status and update uptime/downtime counters accordingly
                    if ("active".equalsIgnoreCase(status.getStatus())) {
                        int currentUptimeLastHour = report.getUptimeLastHour();
                        int currentUptimeLastDay = report.getUptimeLastDay();
                        int currentUptimeLastWeek = report.getUptimeLastWeek();
                        report.setUptimeLastHour(currentUptimeLastHour + (int) interval.toMinutes());
                        report.setUptimeLastDay(currentUptimeLastDay + (int) interval.toHours());
                        report.setUptimeLastWeek(currentUptimeLastWeek + (int) interval.toHours());
                    } else if ("inactive".equalsIgnoreCase(status.getStatus())) {
                        int currentDowntimeLastHour = report.getDowntimeLastHour();
                        int currentDowntimeLastDay = report.getDowntimeLastDay();
                        int currentDowntimeLastWeek = report.getDowntimeLastWeek();
                        report.setDowntimeLastHour(currentDowntimeLastHour + (int) interval.toMinutes());
                        report.setDowntimeLastDay(currentDowntimeLastDay + (int) interval.toHours());
                        report.setDowntimeLastWeek(currentDowntimeLastWeek + (int) interval.toHours());
                    }
                }
                reportList.add(report);
            } catch (Exception e) {
                System.out.println("Error in Exception thrown in generating report: " + e);
            }
        }
        return reportList;
    }

    public Report getReportFromDatabase(String reportId) {
        try {
            Report report = reportRepository.findByReportId(reportId);
            return report;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String saveReportToDatabase(List<StoreReport> reportList) {
        String reportId = generateReportId();
        StringBuilder reportCSV = new StringBuilder();

        String headerRow = "Store ID,Uptime Last Hour,Uptime Last Day,Update Last Week,Downtime Last Hour,Downtime Last Day,Downtime Last Week\n";
        reportCSV.append(headerRow);

        for (StoreReport storeReport : reportList) {
            String storeReportCSV = convertReportToCSV(storeReport);
            reportCSV.append(storeReportCSV);
        }

        Report report = new Report();
        report.setReportId(reportId);

        String directoryPath = "/home/dhruv/Desktop/Store_Monitoring_Project/Reports/";
        try {
            String fileName = "store_report_" + System.currentTimeMillis() + ".csv";
            Path filePath = Paths.get(directoryPath, fileName);
            Files.write(filePath, reportCSV.toString().getBytes());

            report.setCsvData(filePath.toString());
            report.setStatus(ReportStatus.Completed);
            reportRepository.save(report);
            return report.getReportId();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertReportToCSV(StoreReport storeReport) {
        StringBuilder csv = new StringBuilder();

        csv.append(storeReport.getStoreID()).append(",")
                .append(storeReport.getUptimeLastHour()).append(",")
                .append(storeReport.getUptimeLastDay()).append(",")
                .append(storeReport.getUptimeLastWeek()).append(",")
                .append(storeReport.getDowntimeLastHour()).append(",")
                .append(storeReport.getDowntimeLastDay()).append(",")
                .append(storeReport.getDowntimeLastWeek()).append("\n");

        return csv.toString();
    }

    private BusinessHours findBusinessHoursByStoreAndDay(List<BusinessHours> businessHoursList, Long storeId) {
        for (BusinessHours businessHours : businessHoursList) {
            if (storeId.equals(businessHours.getStoreId())) {
                return businessHours;
            }
        }
        return null; // Business hours not found for the store and day
    }

    private boolean isWithinBusinessHours(LocalDateTime localTime, BusinessHours businessHours) {
        LocalTime startTime = businessHours.getStartTimeLocal();
        LocalTime endTime = businessHours.getEndTimeLocal();

        LocalTime timeToCheck = localTime.toLocalTime();

        return !timeToCheck.isBefore(startTime) && !timeToCheck.isAfter(endTime);

    }

    private Map<Long, String> createTimezoneMap(List<StoreTimezone> timezones) {
        Map<Long, String> timezoneMap = new HashMap<>();
        for (StoreTimezone timezone : timezones) {
            timezoneMap.put(timezone.getStoreId(), timezone.getTimezone());
        }
        return timezoneMap;
    }

    private LocalDateTime convertToStoreLocalTime(LocalDateTime timestampUtc, String timezone) {
        ZoneId storeZone = ZoneId.of(timezone);
        Instant instant = timestampUtc.atZone(ZoneId.of("UTC")).toInstant();
        return instant.atZone(storeZone).toLocalDateTime();
    }

    private String generateReportId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "REPORT_" + now.format(formatter);
    }

}
