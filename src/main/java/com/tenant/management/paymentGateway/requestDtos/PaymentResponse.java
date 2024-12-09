package com.tenant.management.paymentGateway.requestDtos;
//Author : Kshitij Ghodekar
//Id : 24149802
import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponse {
    private String transactionId;
    private String status;
    private Double amount;
    private Date paymentDate;
}
