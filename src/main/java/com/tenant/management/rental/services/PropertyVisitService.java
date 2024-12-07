package com.tenant.management.rental.services;

import com.tenant.management.rental.entities.PropertyVisit;
import com.tenant.management.rental.repositories.PropertyVisitRepository;
import com.tenant.management.rental.requestdtos.SubmitApplicationRequest;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyVisitService {

    @Autowired
    private PropertyVisitRepository propertyVisitRepository;

    public ApiResponse getPropertyVisit(UUID propertyVisitId) {
        Optional<PropertyVisit> byUuid = propertyVisitRepository.findByUuid(propertyVisitId);
        if (byUuid.isPresent()) {
            return ApiResponse.builder().data(byUuid.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Request Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse getPropertyVisitsByLandlord(UUID landlordId) {

        Optional<List<PropertyVisit>> allByLandlordId = propertyVisitRepository.findAllByLandlordId(landlordId);
        if (allByLandlordId.isPresent()) {
            return ApiResponse.builder().data(allByLandlordId.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Request Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse getPropertyVisitsByTenant(UUID tenantId) {

        Optional<List<PropertyVisit>> allByTenantId = propertyVisitRepository.findAllByTenantId(tenantId);
        if (allByTenantId.isPresent()) {
            return ApiResponse.builder().data(allByTenantId.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Request Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse getPropertyVisitByProperty(UUID propertyId) {

        Optional<List<PropertyVisit>> allByPropertyId = propertyVisitRepository.findAllByPropertyId(propertyId);
        if (allByPropertyId.isPresent()) {
            return ApiResponse.builder().data(allByPropertyId.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Request Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse submitPropertyVisits(SubmitApplicationRequest leaseApplication) {


        propertyVisitRepository.save(PropertyVisit.builder().visitDate(LocalDate.of(2025, 11, 23)).status(AppConstants.PropertyVisitStatus.PENDING).build());
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Submitted Successfully")
                .success(Boolean.TRUE).build();

    }

    public ApiResponse updatePropertyVisitStatus(PropertyVisit propertyVisit) {
        ApiResponse apiResponse = new ApiResponse();
        LocalDate visitDate = propertyVisit.getVisitDate();
        LocalDate today = LocalDate.now();

        if (today.isBefore(visitDate)) {
            propertyVisitRepository.save(propertyVisit);
            apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Status Changed")
                    .success(Boolean.TRUE).build();
        } else {
            apiResponse = ApiResponse.builder().status(HttpStatus.FORBIDDEN).message("Cannot change status")
                    .success(Boolean.FALSE).build();
        }
        return apiResponse;
    }

}
