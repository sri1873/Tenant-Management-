package com.tenant.management.paymentGateway.strategy;

import com.tenant.management.property.entities.Property;
import com.tenant.management.subscription.entities.SubscriptionPlan;

public interface PaymentCalculationStrategy {
    double calculateFinalAmount(Property property, SubscriptionPlan subscriptionPlan);
}
