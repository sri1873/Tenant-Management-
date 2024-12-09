package com.tenant.management.subscription.services;

import com.tenant.management.subscription.entities.*;
import com.tenant.management.user.entities.Tenant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class subscriptionService {
    private Map<String, SubscriptionPlan> plans = new HashMap<>();
    private Map<String, TenantSubscription> tenantSubscriptions = new HashMap<>();

    public subscriptionService() {
        initializeDefaultPlans();
    }

    private void initializeDefaultPlans() {
        plans.put("NORMAL", new SubscriptionPlan("Normal", 15.0));
        plans.put("PLUS", new SubscriptionPlan("Plus", 25.0));
        plans.put("PREMIUM", new SubscriptionPlan("Premium", 40.0));
    }

    public SubscriptionPlan getPlan(String planId) {
        return plans.get(planId);
    }

    public void subscribeTenant(Tenant tenant, String basePlanId, boolean prioritySupport, boolean additionalStorage) {
        SubscriptionPlan basePlan = getPlan(basePlanId);
        if (basePlan == null) {
            throw new IllegalArgumentException("Plan not found for ID: " + basePlanId);
        }

        SubscriptionPlan customizedPlan = basePlan;
        if (prioritySupport) {
            customizedPlan = new PrioritySupportDecorator(customizedPlan);
        }
        if (additionalStorage) {
            customizedPlan = new AdditionalStorageDecorator(customizedPlan);
        }

        TenantSubscription subscription = new TenantSubscription(tenant.getUserId().toString(), customizedPlan);
        tenantSubscriptions.put(tenant.getUserId().toString(), subscription);

        tenant.setActiveSubscription(true);
        tenant.setRentalPoints(tenant.getRentalPoints() + 10); // Example: Reward points for subscribing
        System.out.println("Tenant " + tenant.getName() + " subscribed to " + customizedPlan.getName() + " at $" + customizedPlan.getPrice());
    }

    public void cancelSubscription(Tenant tenant) {
        if (tenantSubscriptions.remove(tenant.getUserId().toString()) != null) {
            tenant.setActiveSubscription(false);
            System.out.println("Tenant " + tenant.getName() + "'s subscription has been canceled.");
        } else {
            System.out.println("No active subscription found for Tenant " + tenant.getName() + ".");
        }
    }

    public void updateSubscription(Tenant tenant, String newBasePlanId, boolean prioritySupport, boolean additionalStorage) {
        if (!tenantSubscriptions.containsKey(tenant.getUserId().toString())) {
            throw new IllegalArgumentException("No active subscription found for Tenant: " + tenant.getName());
        }
        SubscriptionPlan newBasePlan = getPlan(newBasePlanId);
        if (newBasePlan == null) {
            throw new IllegalArgumentException("Plan not found for ID: " + newBasePlanId);
        }
        SubscriptionPlan customizedPlan = newBasePlan;
        if (prioritySupport) {
            customizedPlan = new PrioritySupportDecorator(customizedPlan);
        }
        if (additionalStorage) {
            customizedPlan = new AdditionalStorageDecorator(customizedPlan);
        }
        TenantSubscription updatedSubscription = new TenantSubscription(tenant.getUserId().toString(), customizedPlan);
        tenantSubscriptions.put(tenant.getUserId().toString(), updatedSubscription);

        System.out.println("Tenant " + tenant.getName() + "'s subscription updated to " + customizedPlan.getName() + " at $" + customizedPlan.getPrice());
    }

    public TenantSubscription getSubscriptionById(String tenantId) {
        TenantSubscription subscription = tenantSubscriptions.get(tenantId);
        if (subscription == null) {
            throw new IllegalArgumentException("No subscription found for Tenant ID: " + tenantId);
        }
        return subscription;
    }
    public List<TenantSubscription> getAllSubscriptions() {
        return new ArrayList<>(tenantSubscriptions.values());
    }
}
