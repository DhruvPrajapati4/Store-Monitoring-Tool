package com.dhruv.storeMonitoring.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 *
 * @author dhruv
 */

@Data
@Entity
public class StoreStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeId;
    private LocalDateTime timestampUtc;
    private String status;
    // Lombok generates getters, setters, toString, equals, hashCode
}





