package com.tenant.management.rental.repositories;

import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.entities.PropertyVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeaseRepository extends JpaRepository<LeaseApplication, UUID> {

    @Query(value = "SELECT * FROM lease_applications la where la.landlord_id=?1", nativeQuery = true)
    Optional<List<LeaseApplication>> findAllByLandlordId(UUID lanlordId);

    @Query(value = "SELECT * FROM lease_applications la where la.tenant_id=?1", nativeQuery = true)
    Optional<List<LeaseApplication>> findAllByTenantId(UUID tenantId);

    @Query(value = "SELECT * FROM lease_applications la where la.property_id=?1", nativeQuery = true)
    Optional<List<LeaseApplication>> findAllByPropertyId(UUID propertyId);

    @Query(value = "SELECT * FROM lease_applications la where la.application_id =?1", nativeQuery = true)
    Optional<LeaseApplication> findByUuid(UUID applicationId);

}
