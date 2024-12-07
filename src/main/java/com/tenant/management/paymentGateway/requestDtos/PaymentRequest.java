package com.tenant.management.paymentGateway.requestDtos;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentRequest {
    private UUID tenantId;
    private UUID propertyId;
    private Double amount;
    private String paymentMethod; // CARD, UPI
}
