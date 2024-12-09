package com.tenant.management.subscription.repositories;

import com.tenant.management.subscription.entities.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<SubscriptionPlan, UUID> {

    // Find all subscription plans for a specific tenant
    @Query(value = "SELECT * FROM subscription sub WHERE sub.tenant_id = ?1", nativeQuery = true)
    List<SubscriptionPlan> findByUserId(UUID tenantId);

    @Override
    List<SubscriptionPlan> findAll();
}
