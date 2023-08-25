package com.dhruv.storeMonitoring.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * @author dhruv
 */

@Data
@Entity
@Table(name = "storeStatus") 
public class StoreStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "status")
    private String status;
    
    @Column(name = "timestamp_utc")
    private String timestampUtc;
    // Lombok generates getters, setters, toString, equals, hashCode
}





