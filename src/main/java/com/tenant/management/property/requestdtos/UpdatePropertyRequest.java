package com.tenant.management.property.requestdtos;

import lombok.Data;

@Data
public class UpdatePropertyRequest {
    private String address;
    private Double price;
    private String type;
    private Integer bedrooms;
    private Integer bathrooms;
    private Boolean available;
}
