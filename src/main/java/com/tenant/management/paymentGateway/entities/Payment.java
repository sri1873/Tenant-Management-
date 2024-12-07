package com.tenant.management.paymentGateway.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID tenantId; // Tenant while making payment

    @Column(nullable = false)
    private UUID propertyId; // Property payment

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status; // "PENDING", "SUCCESS", "FAILED"

    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private String paymentMethod; // "CARD", "UPI" , "Apple Pay"

    private String transactionId; // Transaction Id

    private String gatewayResponse; // Payment Response
}
