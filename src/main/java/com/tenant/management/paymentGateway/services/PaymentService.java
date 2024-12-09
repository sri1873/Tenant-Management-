package com.tenant.management.paymentGateway.services;

import com.tenant.management.paymentGateway.entities.Payment;
import com.tenant.management.paymentGateway.repositories.PaymentRepository;
import com.tenant.management.paymentGateway.requestDtos.PaymentRequest;
import com.tenant.management.paymentGateway.requestDtos.PaymentResponse;
import com.tenant.management.property.entities.Property;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.subscription.entities.SubscriptionPlan;
import com.tenant.management.subscription.repositories.SubscriptionRepository;
import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private SubscriptionRepository subscriptionRepository;  // Added SubscriptionRepository

    public PaymentResponse initiatePayment(PaymentRequest request) {
        Optional<Tenant> optionalTenant = tenantRepository.findByUuid(request.getUserId());
        Property property = propertyRepository.getById(request.getPropertyId());

        // Ensure the tenant exists
        Tenant tenant = optionalTenant.orElseThrow(() -> new IllegalArgumentException("Tenant not found"));
        // Get the tenant's subscription plan
        SubscriptionPlan subscriptionPlan = getTenantSubscription(tenant.getUserId());
        // Calculate the final amount with discount based on subscription
        double finalAmount = calculateFinalAmount(property, subscriptionPlan);

        // Create a new payment record
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setTenantId(tenant);
        payment.setPropertyId(property);
        payment.setAmount(finalAmount);  // Use the final amount after discount and taxes
        payment.setStatus("PENDING");
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(request.getPaymentMethod());

        // Simulate payment gateway response to be added later on
        String transactionId = UUID.randomUUID().toString(); // Mock transaction ID
        String gatewayResponse = "Mock response from gateway";

        payment.setTransactionId(transactionId);
        payment.setGatewayResponse(gatewayResponse);
        payment.setStatus("SUCCESS");
        paymentRepository.save(payment);

        // Prepare the response
        PaymentResponse response = new PaymentResponse();
        response.setTransactionId(transactionId);
        response.setStatus("SUCCESS");
        response.setAmount(finalAmount);  // Set the final amount in the response
        response.setPaymentDate(payment.getPaymentDate());

        return response;
    }

    // Method to fetch the tenant's subscription plan
    private SubscriptionPlan getTenantSubscription(UUID tenantId) {
        List<SubscriptionPlan> subscriptionPlans = subscriptionRepository.findByUserId(tenantId);
        if (subscriptionPlans.isEmpty()) {
            throw new IllegalArgumentException("No subscription found for tenant ID: " + tenantId);
        }
        // Assuming the first subscription is the active one
        return subscriptionPlans.get(0);
    }

    // Calculate the final amount considering the subscription discount and VAT
    private double calculateFinalAmount(Property property, SubscriptionPlan subscriptionPlan) {
        double basePrice = property.getPrice();
        double discount = getDiscountForSubscription(subscriptionPlan);
        double discountedPrice = basePrice - (basePrice * discount);
        if (property.getType().equalsIgnoreCase("commercial")) {
            double vatRate = 0.23; // 23% VAT for commercial properties in ireland
            discountedPrice = discountedPrice + (discountedPrice * vatRate);
        }
        return discountedPrice;
    }

    // Get discount based on subscription plan
    private double getDiscountForSubscription(SubscriptionPlan subscriptionPlan) {
        if (subscriptionPlan == null) {
            return 0;  // No discount if subscription plan is not there
        }

        String planName = subscriptionPlan.getName().toUpperCase();
        switch (planName) {
            case "NORMAL":
                return 0.15; // 15% discount for Normal plan
            case "PLUS":
                return 0.20; // 20% discount for Plus plan
            case "PREMIUM":
                return 0.25; // 25% discount for Premium plan
            default:
                return 0; // No discount for unrecognized plans
        }
    }

    public PaymentResponse verifyPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        // Handle SUCCESS status
        if ("SUCCESS".equals(payment.getStatus())) {
            PaymentResponse response = new PaymentResponse();
            response.setTransactionId(payment.getTransactionId());
            response.setStatus(payment.getStatus());
            response.setAmount(payment.getAmount());
            response.setPaymentDate(payment.getPaymentDate());
            return response;
        }

        // Handle PENDING status (you could retry after a delay or provide feedback)
        if ("PENDING".equals(payment.getStatus())) {
            // You can retry the check after a delay or give some other feedback
            throw new IllegalStateException("Payment is still pending. Please try again later.");
        }

        // Handle FAILED status
        if ("FAILED".equals(payment.getStatus())) {
            throw new IllegalStateException("Payment failed. Please check your payment details.");
        }

        // Handle any other unexpected statuses
        throw new IllegalStateException("Payment verification failed: Payment status is not SUCCESS. Current status: " + payment.getStatus());
    }

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

        throw new IllegalArgumentException("Only PENDING payments can be cancelled");
    }

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
