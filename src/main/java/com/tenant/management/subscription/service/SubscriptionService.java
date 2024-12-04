package com.tenant.management.subscription.service;

import com.tenant.management.subscription.entities.SubscriptionPlan;
import com.tenant.management.subscription.entities.Tenant;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionService {
    // Map to store plans (Plan ID -> SubscriptionPlan)
    private Map<String, SubscriptionPlan> plans = new HashMap<>();

    // Initialize default plans
    public SubscriptionService() {
        initializeDefaultPlans();
    }

    // Create a new plan (for demo purposes, enums already define plans)
    public void createPlan(String planId, SubscriptionPlan plan) {
        plans.put(planId, plan);
    }

    // Get plan details by plan ID
    public SubscriptionPlan getPlan(String planId) {
        return plans.get(planId);
    }

    // Subscribe a tenant to a specific plan
    public void subscribeTenant(Tenant tenant, String planId) {
        SubscriptionPlan plan = getPlan(planId);
        if (plan == null) {
            throw new IllegalArgumentException("Plan not found for ID: " + planId);
        }
        tenant.setPlan(plan);
        System.out.println("Tenant " + tenant.getName() + " subscribed to " + plan.name());
    }

    // Initialize default plans
    private void initializeDefaultPlans() {
        plans.put("NORMAL", SubscriptionPlan.NORMAL);
        plans.put("PLUS", SubscriptionPlan.PLUS);
        plans.put("PREMIUM", SubscriptionPlan.PREMIUM);
    }
}
