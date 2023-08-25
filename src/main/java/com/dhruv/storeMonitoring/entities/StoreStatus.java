package com.dhruv.storeMonitoring.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

/**
 *
 * @author dhruv
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "store_status") 
public class StoreStatus {

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "status")
    private String status;
    
    @Column(name = "timestamp_utc")
    private String timestampUtc;
    // Lombok generates getters, setters, toString, equals, hashCode
}





