package com.tenant.management.subscription.entities;

public class TenantSubscription {
    private String tenantId;
    private SubscriptionPlan plan;

    public TenantSubscription(String tenantId, SubscriptionPlan plan) {
        this.tenantId = tenantId;
        this.plan = plan;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "TenantSubscription{" +
                "tenantId='" + tenantId + '\'' +
                ", plan=" + plan +
                '}';
    }
}
