package com.tenant.management.subscription.controller;

import com.tenant.management.subscription.entities.TenantSubscription;
import com.tenant.management.subscription.services.SubscriptionService;
import com.tenant.management.user.entities.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // Simulated tenant database (replace with actual database in real application)
    private final Map<UUID, Tenant> tenantDatabase = new ConcurrentHashMap<>();

    @PostMapping("/addTenant")
    public ResponseEntity<String> addTenant(@RequestParam UUID tenantId, @RequestParam String tenantName) {
        Tenant tenant = new Tenant();
        tenant.setUserId(tenantId);
        tenant.setName(tenantName);
        tenant.setRentalPoints(0);
        tenant.setActiveSubscription(false);
        tenantDatabase.put(tenantId, tenant);
        return ResponseEntity.ok("Tenant added successfully.");
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeTenant(@RequestParam UUID tenantId, @RequestParam String basePlanId,
                                                  @RequestParam boolean prioritySupport, @RequestParam boolean additionalStorage) {
        Tenant tenant = tenantDatabase.get(tenantId);
        if (tenant == null) {
            return ResponseEntity.badRequest().body("Tenant not found with ID: " + tenantId);
        }

        subscriptionService.subscribeTenant(tenant, basePlanId, prioritySupport, additionalStorage);
        return ResponseEntity.ok("Subscription successful.");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelTenantSubscription(@RequestParam UUID tenantId) {
        Tenant tenant = tenantDatabase.get(tenantId);
        if (tenant == null) {
            return ResponseEntity.badRequest().body("Tenant not found with ID: " + tenantId);
        }

        subscriptionService.cancelSubscription(tenant);
        return ResponseEntity.ok("Subscription canceled successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTenantSubscription(@RequestParam UUID tenantId, @RequestParam String newBasePlanId,
                                                           @RequestParam boolean prioritySupport, @RequestParam boolean additionalStorage) {
        Tenant tenant = tenantDatabase.get(tenantId);
        if (tenant == null) {
            return ResponseEntity.badRequest().body("Tenant not found with ID: " + tenantId);
        }

        subscriptionService.updateSubscription(tenant, newBasePlanId, prioritySupport, additionalStorage);
        return ResponseEntity.ok("Subscription updated successfully.");
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantSubscription> getTenantSubscription(@PathVariable UUID tenantId) {
        Tenant tenant = tenantDatabase.get(tenantId);
        if (tenant == null) {
            return ResponseEntity.badRequest().body(null);
        }

        TenantSubscription subscription = subscriptionService.getSubscriptionById(tenant.getUserId().toString());
        return ResponseEntity.ok(subscription);
    }
}
