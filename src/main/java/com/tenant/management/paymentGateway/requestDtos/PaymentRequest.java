package com.tenant.management.paymentGateway.requestDtos;
//Author : Kshitij Ghodekar
//Id : 24149802
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentRequest {
    private UUID UserId;
    private UUID propertyId;
    private Double amount;
    private String paymentMethod; // CARD, UPI
}
