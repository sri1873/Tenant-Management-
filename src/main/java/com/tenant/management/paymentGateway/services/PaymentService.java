package com.tenant.management.paymentGateway.services;

import com.tenant.management.paymentGateway.entities.Payment;
import com.tenant.management.paymentGateway.repositories.PaymentRepository;
import com.tenant.management.paymentGateway.requestDtos.PaymentRequest;
import com.tenant.management.paymentGateway.requestDtos.PaymentResponse;
import com.tenant.management.property.entities.Property;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final String paymentGatewayUrl = "https://api.paymentgateway.com";
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    public PaymentResponse initiatePayment(PaymentRequest request) {
        Optional<Tenant> optionalTenant = tenantRepository.findByUuid(request.getTenantId());
        Property byId = propertyRepository.getById(request.getPropertyId());
        // Create a new payment record
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setTenantId(optionalTenant.get());
        payment.setPropertyId(byId);
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
