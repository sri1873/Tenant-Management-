package com.tenant.management.subscription;

import com.tenant.management.subscription.entities.SubscriptionPlan;
import com.tenant.management.subscription.entities.TenantSubscription;
import com.tenant.management.subscription.services.subscriptionService;
import com.tenant.management.user.entities.Tenant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionServiceTest {

    private subscriptionService subscriptionService;
    private Tenant mockTenant;
    private Tenant anotherTenant;

    @BeforeEach
    void setUp() {
        subscriptionService = new subscriptionService();

        // First tenant
        mockTenant = new Tenant();
        mockTenant.setName("John Doe");
        mockTenant.setRentalPoints(0);
        mockTenant.setActiveSubscription(false);
        mockTenant.setUserId(java.util.UUID.randomUUID());

        // Second tenant for "all subscriptions" test
        anotherTenant = new Tenant();
        anotherTenant.setName("Jane Smith");
        anotherTenant.setRentalPoints(0);
        anotherTenant.setActiveSubscription(false);
        anotherTenant.setUserId(java.util.UUID.randomUUID());
    }

    @Test
    void testGetSubscriptionById() {
        subscriptionService.subscribeTenant(mockTenant, "NORMAL", true, true);

        TenantSubscription subscription = subscriptionService.getSubscriptionById(mockTenant.getUserId().toString());
        assertNotNull(subscription, "Subscription should not be null.");
        assertEquals(mockTenant.getUserId().toString(), subscription.getTenantId(), "Tenant ID should match.");
        assertEquals("Normal + Priority Support + Extra Storage", subscription.getPlan().getName(),
                "Plan name should match the selected features.");
    }

    @Test
    void testUpdateAndGetSubscription() {
        // Initial subscription
        subscriptionService.subscribeTenant(mockTenant, "NORMAL", false, false);

        // Update subscription
        subscriptionService.updateSubscription(mockTenant, "PREMIUM", true, true);

        // Retrieve updated subscription
        TenantSubscription updatedSubscription = subscriptionService.getSubscriptionById(mockTenant.getUserId().toString());
        assertNotNull(updatedSubscription, "Updated subscription should exist.");
        assertEquals("Premium + Priority Support + Extra Storage", updatedSubscription.getPlan().getName(),
                "Plan name should match the updated plan with features.");
        assertTrue(mockTenant.isActiveSubscription(), "Tenant should still have an active subscription.");
    }

    @Test
    void testGetAllSubscriptions() {
        // Subscribe multiple tenants
        subscriptionService.subscribeTenant(mockTenant, "NORMAL", true, false);
        subscriptionService.subscribeTenant(anotherTenant, "PLUS", false, true);

        // Retrieve all subscriptions
        List<TenantSubscription> allSubscriptions = subscriptionService.getAllSubscriptions();

        // Verify subscriptions
        assertNotNull(allSubscriptions, "All subscriptions list should not be null.");
        assertEquals(2, allSubscriptions.size(), "There should be 2 active subscriptions.");

        List<String> subscriptionNames = allSubscriptions.stream()
                .map(TenantSubscription::getPlan)
                .map(SubscriptionPlan::getName)
                .collect(Collectors.toList());

        assertTrue(subscriptionNames.contains("Normal + Priority Support"), "Should include 'Normal + Priority Support' plan.");
        assertTrue(subscriptionNames.contains("Plus + Extra Storage"), "Should include 'Plus + Extra Storage' plan.");
    }

    @Test
    void testCancelSubscription() {
        subscriptionService.subscribeTenant(mockTenant, "NORMAL", false, false);
        subscriptionService.cancelSubscription(mockTenant);

        assertFalse(mockTenant.isActiveSubscription(), "Tenant's subscription should be canceled.");
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.getSubscriptionById(mockTenant.getUserId().toString()),
                "Subscription should no longer exist.");
    }
    @Test
    void testSubscribeWithInvalidPlanId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.subscribeTenant(mockTenant, "INVALID_PLAN", false, false);
        });
        assertEquals("Plan not found for ID: INVALID_PLAN", exception.getMessage());
    }
    @Test
    void testMultipleSubscriptionsBySameTenant() {
        subscriptionService.subscribeTenant(mockTenant, "NORMAL", false, false);
        subscriptionService.subscribeTenant(mockTenant, "PREMIUM", true, false);

        TenantSubscription subscription = subscriptionService.getSubscriptionById(mockTenant.getUserId().toString());
        assertEquals("Premium + Priority Support", subscription.getPlan().getName(),
                "New subscription should replace the old one.");
    }
    @Test
    void testGetAllSubscriptionsAfterCancellation() {
        subscriptionService.subscribeTenant(mockTenant, "NORMAL", true, true);
        subscriptionService.subscribeTenant(anotherTenant, "PLUS", false, true);

        subscriptionService.cancelSubscription(mockTenant);

        List<TenantSubscription> activeSubscriptions = subscriptionService.getAllSubscriptions();
        assertEquals(1, activeSubscriptions.size(), "Only one active subscription should remain.");
        assertEquals(anotherTenant.getUserId().toString(), activeSubscriptions.get(0).getTenantId(),
                "The remaining subscription should belong to anotherTenant.");
    }


}
