package com.tenant.management.subscription.entities;

// Enum for Subscription Plans
public enum SubscriptionPlan {
    NORMAL("Normal Plan"),
    PLUS("Plus Plan"),
    PREMIUM("Premium Plan");

    private final String description;

    // Constructor
    SubscriptionPlan(String description) {
        this.description = description;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }
}
