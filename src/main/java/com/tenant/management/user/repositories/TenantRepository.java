//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.repositories;

import com.tenant.management.user.entities.Tenant;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    @Query(value = "SELECT * FROM tenant t JOIN app_user au ON t.user_id = au.user_id WHERE t.user_id = ?1", nativeQuery = true)
    Optional<Tenant> findByUuid(UUID userId);

    @Modifying
    @Query(value = "DELETE FROM tenant t  where t.user_id=?1", nativeQuery = true)
    void deleteById(@NotNull UUID userId);

}

