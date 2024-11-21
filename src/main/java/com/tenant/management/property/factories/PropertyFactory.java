package com.tenant.management.property.factories;

import com.tenant.management.property.entities.Apartment;
import com.tenant.management.property.entities.House;
import com.tenant.management.property.entities.Condo;
import com.tenant.management.property.entities.Property;

import java.util.UUID;

public class PropertyFactory {
    public static Property createProperty(String type, String address, double price,
                                          int bedrooms, int bathrooms, boolean available, UUID landlordId) {
        switch (type.toLowerCase()) {
            case "apartment":
                return new Apartment(address, price, bedrooms, bathrooms, available, landlordId);
            case "house":
                return new House(address, price, bedrooms, bathrooms, available, landlordId);
            case "condo":
                return new Condo(address, price, bedrooms, bathrooms, available, landlordId);
            default:
                throw new IllegalArgumentException("Unknown property type: " + type);
        }
    }
}
