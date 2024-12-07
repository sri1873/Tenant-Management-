package com.tenant.management.subscription.entities;

import com.tenant.management.user.entities.Tenant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Subscription {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "tenantId")
    private Tenant tenantId; // Link subscription to a tenant

    @Column(nullable = false)
    private String planType; // Normal, Plus, Premium

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Boolean isActive;
}
