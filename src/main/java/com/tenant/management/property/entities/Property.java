package com.tenant.management.property.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Property {
    @Id
    private UUID propertyId = UUID.randomUUID();
}
