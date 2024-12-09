package com.tenant.management.paymentGateway.services;
//Author : Kshitij Ghodekar
//Id : 24149802
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

    private final String paymentGatewayUrl = "https://api.paymentgateway.com"; // Will in future when integrate

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public PaymentResponse initiatePayment(PaymentRequest request) {
        Optional<Tenant> optionalTenant = tenantRepository.findByUuid(request.getTenantId());
        Property property = propertyRepository.getById(request.getPropertyId());

        // Create a new payment record
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setTenantId(optionalTenant.get());
        payment.setPropertyId(property);
        payment.setAmount(request.getAmount());
        payment.setStatus("PENDING");
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(request.getPaymentMethod());

        paymentRepository.save(payment);

        RestTemplate restTemplate = new RestTemplate();
        String transactionId = UUID.randomUUID().toString(); // Mock transaction ID
        String gatewayResponse = "Mock response from gateway";

        payment.setTransactionId(transactionId);
        payment.setGatewayResponse(gatewayResponse);
        payment.setStatus("SUCCESS");
        paymentRepository.save(payment);

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

    /**
     * Cancel a payment if it is still in "PENDING" status.
     */
    public PaymentResponse cancelPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        // Only cancel payments that are still "PENDING"
        if ("PENDING".equals(payment.getStatus())) {
            payment.setStatus("CANCELLED");
            paymentRepository.save(payment);

            PaymentResponse response = new PaymentResponse();
            response.setTransactionId(payment.getTransactionId());
            response.setStatus("CANCELLED");
            response.setAmount(payment.getAmount());
            response.setPaymentDate(payment.getPaymentDate());
            return response;
        }

        throw new IllegalStateException("Only PENDING payments can be cancelled");
    }

    /**
     * Mark a payment as successful.
     */
    public PaymentResponse markPaymentAsSuccess(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        // Only update status to SUCCESS if it's still PENDING
        if (!"CANCELLED".equals(payment.getStatus()) && !"SUCCESS".equals(payment.getStatus())) {
            payment.setStatus("SUCCESS");
            payment.setPaymentDate(new Date()); // Optional: Update the payment date to the current date
            paymentRepository.save(payment);

            PaymentResponse response = new PaymentResponse();
            response.setTransactionId(payment.getTransactionId());
            response.setStatus("SUCCESS");
            response.setAmount(payment.getAmount());
            response.setPaymentDate(payment.getPaymentDate());
            return response;
        }

        throw new IllegalStateException("Payment is already SUCCESS or CANCELLED");
    }
}
