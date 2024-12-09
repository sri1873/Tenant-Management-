package com.tenant.management.subscription.entities;

public class PrioritySupportDecorator extends SubscriptionPlanDecorator {

    public PrioritySupportDecorator(SubscriptionPlan wrappedPlan) {
        super(wrappedPlan);
    }

    @Override
    public String getName() {
        return wrappedPlan.getName() + " + Priority Support";
    }

    @Override
    public double getPrice() {
        return wrappedPlan.getPrice() + 10.0;
    }
}
