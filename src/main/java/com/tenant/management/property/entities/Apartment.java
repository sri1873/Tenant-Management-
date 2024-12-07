package com.tenant.management.property.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class Apartment extends Property {
    public Apartment(String address, double price, int bedrooms, int bathrooms, boolean available, UUID landlordId) {
        this.setType("Apartment");
        this.setAddress(address);
        this.setPrice(price);
        this.setBedrooms(bedrooms);
        this.setBathrooms(bathrooms);
        this.setAvailable(available);
        this.setLandlordId(landlordId);
    }
}
