package com.tenant.management.subscription.requestDtos;

import lombok.Data;

import java.util.UUID;

@Data
public class SubscriptionRequest {
    private UUID tenantId;
    private String planType; // Normal, Plus, Premium
}
