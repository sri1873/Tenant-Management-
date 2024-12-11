package com.tenant.management.subscription.controller;

import com.tenant.management.subscription.entities.TenantSubscription;
import com.tenant.management.subscription.services.subscriptionService;
import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.repositories.TenantRepository;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

    @Autowired
    private subscriptionService subscriptionService;

    @Autowired
    private TenantRepository tenantRepository;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeTenant(@RequestParam UUID tenantId,
                                                  @RequestParam String basePlanId,
                                                  @RequestParam boolean prioritySupport,
                                                  @RequestParam boolean additionalStorage) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) {
            return ResponseEntity.badRequest().body("Tenant not found with ID: " + tenantId);
        }
        Tenant tenant = optionalTenant.get();
        subscriptionService.subscribeTenant(tenant, basePlanId, prioritySupport, additionalStorage);
        return ResponseEntity.ok("Subscription successful.");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelTenantSubscription(@RequestParam UUID tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) {
            return ResponseEntity.badRequest().body("Tenant not found with ID: " + tenantId);
        }
        Tenant tenant = optionalTenant.get();
        subscriptionService.cancelSubscription(tenant);
        return ResponseEntity.ok("Subscription canceled successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTenantSubscription(@RequestParam UUID tenantId,
                                                           @RequestParam String newBasePlanId,
                                                           @RequestParam boolean prioritySupport,
                                                           @RequestParam boolean additionalStorage) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) {
            return ResponseEntity.badRequest().body("Tenant not found with ID: " + tenantId);
        }
        Tenant tenant = optionalTenant.get();
        subscriptionService.updateSubscription(tenant, newBasePlanId, prioritySupport, additionalStorage);
        return ResponseEntity.ok("Subscription updated successfully.");
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantSubscription> getTenantSubscription(@PathVariable UUID tenantId) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
        if (optionalTenant.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Tenant tenant = optionalTenant.get();
        TenantSubscription subscription = subscriptionService.getSubscriptionById(tenant.getUserId().toString());
        return ResponseEntity.ok(subscription);
    }
}
