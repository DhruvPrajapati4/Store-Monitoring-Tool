/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhruv.storeMonitoring.entities;

import lombok.Data;
import javax.persistence.*;
import lombok.NoArgsConstructor;

/**
 *
 * @author dhruv
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "report")
public class Report{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private String reportId;

    @Column(name = "csv_data")
    private String csvData;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus status;

    // Constructors, getters, setters, etc.
    
    // Enums
    public enum ReportStatus {
        Running, Completed
    }
}

