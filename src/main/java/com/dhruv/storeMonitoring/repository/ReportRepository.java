/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhruv.storeMonitoring.repository;
import com.dhruv.storeMonitoring.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dhruv
 */

public interface ReportRepository extends JpaRepository<Report, Long> {

    public Report findByReportId(String reportId);
    
}

