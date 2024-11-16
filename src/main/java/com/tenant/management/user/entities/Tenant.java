package com.tenant.management.user.entities;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Tenant extends User {
    private int rentalPoints;
    private boolean activeSubscription;
}
