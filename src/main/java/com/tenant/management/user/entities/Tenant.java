package com.tenant.management.user.entities;


import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Tenant extends User {
    private int rentalPoints;
    private boolean activeSubscription;


}
