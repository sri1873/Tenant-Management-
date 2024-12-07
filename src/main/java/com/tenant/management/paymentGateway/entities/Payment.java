package com.tenant.management.paymentGateway.entities;

import com.tenant.management.property.entities.Property;
import com.tenant.management.user.entities.Tenant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tenantId")
    private Tenant tenantId; // Tenant while making payment

    @ManyToOne
    @JoinColumn(name = "propertyId")
    private Property propertyId; // Property payment

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
