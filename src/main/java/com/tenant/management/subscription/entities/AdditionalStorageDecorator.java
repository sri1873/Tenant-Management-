package com.tenant.management.subscription.entities;

public class AdditionalStorageDecorator extends SubscriptionPlanDecorator {

    public AdditionalStorageDecorator(SubscriptionPlan wrappedPlan) {
        super(wrappedPlan);
    }

    @Override
    public String getName() {
        return wrappedPlan.getName() + " + Extra Storage";
    }

    @Override
    public double getPrice() {
        return wrappedPlan.getPrice() + 15.0;
    }
}
