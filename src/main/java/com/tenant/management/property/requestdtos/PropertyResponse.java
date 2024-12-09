package com.tenant.management.property.requestdtos;
//Author : Kshitij Ghodekar
//Id : 24149802
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
