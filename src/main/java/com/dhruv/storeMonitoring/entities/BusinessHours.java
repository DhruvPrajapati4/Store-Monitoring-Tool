/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhruv.storeMonitoring.entities;

import java.time.LocalTime;
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
@Table(name = "business_hours")
public class BusinessHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="store_id")
    private Long storeId;
    
    @Column(name="day_of_week")
    private int dayOfWeek;
    
    @Column(name="start_time_local")
    private LocalTime startTimeLocal;
    
    @Column(name="end_time_local")
    private LocalTime endTimeLocal;
    // Lombok generates getters, setters, toString, equals, hashCode

    public BusinessHours(Long storeId, int dayOfWeek, LocalTime startTimeLocal, LocalTime endTimeLocal) {
        this.storeId=storeId;
        this.dayOfWeek=dayOfWeek;
        this.startTimeLocal=startTimeLocal;
        this.endTimeLocal=endTimeLocal;
    }
}
