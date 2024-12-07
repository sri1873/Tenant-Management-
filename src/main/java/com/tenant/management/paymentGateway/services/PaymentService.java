package com.tenant.management.paymentGateway.services;

import com.tenant.management.paymentGateway.entities.Payment;
import com.tenant.management.paymentGateway.repositories.PaymentRepository;
import com.tenant.management.paymentGateway.requestDtos.PaymentRequest;
import com.tenant.management.paymentGateway.requestDtos.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class PaymentService {

    private final String paymentGatewayUrl = "https://api.paymentgateway.com";
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentResponse initiatePayment(PaymentRequest request) {
        // Create a new payment record
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setTenantId(request.getTenantId());
        payment.setPropertyId(request.getPropertyId());
        payment.setAmount(request.getAmount());
        payment.setStatus("PENDING");
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(request.getPaymentMethod());

        // Save payment record
        paymentRepository.save(payment);

        // Call payment gateway API
        RestTemplate restTemplate = new RestTemplate();
        String transactionId = UUID.randomUUID().toString(); // Mock transaction ID
        String gatewayResponse = "Mock response from gateway";

        // Update payment record
        payment.setTransactionId(transactionId);
        payment.setGatewayResponse(gatewayResponse);
        payment.setStatus("SUCCESS");
        paymentRepository.save(payment);

        // Create a response DTO
        PaymentResponse response = new PaymentResponse();
        response.setTransactionId(transactionId);
        response.setStatus("SUCCESS");
        response.setAmount(request.getAmount());
        response.setPaymentDate(payment.getPaymentDate());

        return response;
    }

    public PaymentResponse verifyPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        if ("SUCCESS".equals(payment.getStatus())) {
            PaymentResponse response = new PaymentResponse();
            response.setTransactionId(payment.getTransactionId());
            response.setStatus(payment.getStatus());
            response.setAmount(payment.getAmount());
            response.setPaymentDate(payment.getPaymentDate());
            return response;
        }

        throw new IllegalStateException("Payment verification failed");
    }
}
