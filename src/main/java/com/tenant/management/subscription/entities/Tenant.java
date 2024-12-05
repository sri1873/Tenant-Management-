package com.tenant.management.subscription.entities;

public class Tenant {
    private String tenantId;
    private String name;
    private String email;
    private SubscriptionPlan plan; // Use enum for subscription plan

    public Tenant(String tenantId, String name, String email) {
        this.tenantId = tenantId;
        this.name = name;
        this.email = email;
        this.plan = SubscriptionPlan.NORMAL; // Default to NORMAL
    }

    // Getters and Setters
    public String getTenantId() {
        return tenantId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "tenantId='" + tenantId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", plan=" + plan +
                '}';
    }
}
