package com.tenant.management.subscription.repositories;

import com.tenant.management.subscription.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query(value = "SELECT * FROM subscription sub where sub.tenant_id=?1", nativeQuery = true)
    List<Subscription> findByTenantId(UUID tenantId);
}
