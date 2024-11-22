package com.tenant.management.user.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Landlord extends User {
    public String property;
    private String advertisement;
}
