package com.tenant.management.property.requestDtos;

import lombok.Data;

import java.util.UUID;

@Data
public class PropertyResponse {
    private UUID propertyId;
    private String address;
    private Double price;
    private String type;
    private Integer bedrooms;
    private Integer bathrooms;
    private Boolean available;
    private UUID landlordId;
}
