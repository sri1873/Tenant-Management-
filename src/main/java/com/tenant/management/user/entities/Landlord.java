package com.tenant.management.user.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@RequiredArgsConstructor
public class Landlord extends User {
    public String property;
    private String advertisement;
}
