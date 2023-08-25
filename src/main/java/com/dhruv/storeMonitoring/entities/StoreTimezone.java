/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhruv.storeMonitoring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author dhruv
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "store_timezone")
public class StoreTimezone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;
    
    @Column(name = "timezone")
    private String timezone;
    // Lombok generates getters, setters, toString, equals, hashCode
}
