package com.tenant.management.subscription.entities;

public abstract class SubscriptionPlanDecorator extends SubscriptionPlan {
    protected SubscriptionPlan wrappedPlan;

    public SubscriptionPlanDecorator(SubscriptionPlan wrappedPlan) {
        super(wrappedPlan.getName(), wrappedPlan.getPrice());
        this.wrappedPlan = wrappedPlan;
    }

    @Override
    public String getName() {
        return wrappedPlan.getName();
    }

    @Override
    public double getPrice() {
        return wrappedPlan.getPrice();
    }
}