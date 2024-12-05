package com.tenant.management.subscription.services;

import com.tenant.management.subscription.requestDtos.SubscriptionRequest;
import com.tenant.management.subscription.requestDtos.SubscriptionResponse;
import com.tenant.management.subscription.entities.Subscription;
import com.tenant.management.subscription.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse subscribe(SubscriptionRequest request) {
        // Define prices for subscription plans
        double price;
        switch (request.getPlanType().toUpperCase()) {
            case "PLUS":
                price = 15.0;
                break;
            case "PREMIUM":
                price = 25.0;
                break;
            default:
                price = 10.0; // Normal plan
        }

        // Create a new subscription
        Subscription subscription = new Subscription();
        subscription.setId(UUID.randomUUID());
        subscription.setTenantId(request.getTenantId());
        subscription.setPlanType(request.getPlanType());
        subscription.setPrice(price);
        subscription.setStartDate(new Date());

        // Set end date to 1 month from start date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(subscription.getStartDate());
        calendar.add(Calendar.MONTH, 1); // Add 1 month
        subscription.setEndDate(calendar.getTime());

        subscription.setIsActive(true);

        // Save subscription
        subscriptionRepository.save(subscription);

        // Create response
        SubscriptionResponse response = new SubscriptionResponse();
        response.setPlanType(subscription.getPlanType());
        response.setPrice(subscription.getPrice());
        response.setStartDate(subscription.getStartDate());
        response.setEndDate(subscription.getEndDate());
        response.setIsActive(subscription.getIsActive());

        return response;
    }

    public void cancelSubscription(UUID subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        subscription.setIsActive(false);
        subscriptionRepository.save(subscription);
    }
}
