package com.tenant.management.subscription.controllers;

import com.tenant.management.subscription.entities.Tenant;
import com.tenant.management.subscription.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final SubscriptionService subscriptionService;

    public TenantController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;

    }

    // Example method to find tenant (mock logic, replace with DB call)
    private Tenant findTenantById(String tenantId) {
        // Replace with actual logic to fetch tenant from DB
        return new Tenant(tenantId, "Sample Tenant", "tenant@example.com");
    }
}
