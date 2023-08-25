/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dhruv.storeMonitoring.repository;

import com.dhruv.storeMonitoring.entities.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author dhruv
 */

public interface StoreStatusRepository extends JpaRepository<StoreStatus, Long> {
    // Custom queries can be added if needed
}

