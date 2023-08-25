/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhruv.storeMonitoring.entities;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author dhruv
 */

@Data
@NoArgsConstructor
public class StoreReport { 
   
    private Long storeID;
    private int uptimeLastHour;
    private int uptimeLastDay;
    private int uptimeLastWeek;
    private int downtimeLastHour;
    private int downtimeLastDay;
    private int downtimeLastWeek;

    
    public StoreReport(Long storeId)
    {
        this.storeID=storeId;
        this.uptimeLastHour=0;
        this.uptimeLastDay=0;
        this.uptimeLastWeek=0;
        this.downtimeLastHour=0;
        this.downtimeLastDay=0;
        this.downtimeLastWeek=0;
    }

    // Constructors, getters, setters, and other methods
}


