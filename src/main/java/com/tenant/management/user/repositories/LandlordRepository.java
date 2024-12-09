//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.repositories;

import com.tenant.management.user.entities.Landlord;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, UUID> {

    @Query(value = "select * from landlord l join app_user au on l.user_id = au.user_id where l.user_id =?1", nativeQuery = true)
    Optional<Landlord> findByUuid(UUID userId);

    @Query(value = "DELETE * FROM app_user au where au.userId=?1", nativeQuery = true)
    void deleteById(@NotNull UUID userId);
}

