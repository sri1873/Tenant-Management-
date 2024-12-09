package com.tenant.management.rental.repositories;

import com.tenant.management.rental.entities.PropertyVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Author : K S SRI KUMAR
//Id : 24177474
@Repository
public interface PropertyVisitRepository extends JpaRepository<PropertyVisit, UUID> {

    @Query(value = "SELECT * FROM property_visit_request pvr where pvr.landlord_id=?1", nativeQuery = true)
    Optional<List<PropertyVisit>> findAllByLandlordId(UUID pvrnlordId);

    @Query(value = "SELECT * FROM property_visit_request pvr where pvr.tenant_id=?1", nativeQuery = true)
    Optional<List<PropertyVisit>> findAllByTenantId(UUID tenantId);

    @Query(value = "SELECT * FROM property_visit_request pvr where pvr.property_id=?1", nativeQuery = true)
    Optional<List<PropertyVisit>> findAllByPropertyId(UUID propertyId);


    @Query(value = "SELECT * FROM property_visit_request pvr where pvr.property_visit_id =?1", nativeQuery = true)
    Optional<PropertyVisit> findByUuid(UUID applicationId);

}
