//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class Tenant extends User {
    private int creditScore;
    private int rentalPoints;
    private boolean activeSubscription;

}

