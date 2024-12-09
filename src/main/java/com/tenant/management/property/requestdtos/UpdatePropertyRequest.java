package com.tenant.management.property.requestdtos;
//Author : Kshitij Ghodekar
//Id : 24149802
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
