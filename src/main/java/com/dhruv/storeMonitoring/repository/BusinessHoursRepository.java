/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhruv.storeMonitoring.repository;

import com.dhruv.storeMonitoring.entities.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author dhruv
 */
public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
    // Custom queries can be added if needed
}