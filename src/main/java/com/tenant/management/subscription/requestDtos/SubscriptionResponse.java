package com.tenant.management.subscription.requestDtos;

import lombok.Data;

import java.util.Date;

@Data
public class SubscriptionResponse {
    private String planType;
    private Double price;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;
}
