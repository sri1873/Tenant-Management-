package com.tenant.management.rental.services;

import com.tenant.management.rental.entities.PropertyVisit;
import com.tenant.management.rental.repositories.PropertyVisitRepository;
import com.tenant.management.rental.requestDtos.PropertyVisitActionRequest;
import com.tenant.management.rental.requestDtos.SubmitApplicationRequest;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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


//        propertyVisitRepository.save(leaseApplication);
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Submitted Successfully")
                .success(Boolean.TRUE).build();

    }

    public ApiResponse cancelPropertyVisits(UUID applicationId) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<PropertyVisit> byUuid = propertyVisitRepository.findByUuid(applicationId);
        if (byUuid.isPresent()) {
            PropertyVisit propertyVisit = byUuid.get();
            AppConstants.PropertyVisitStatus status = propertyVisit.getStatus();
            if (status.equals(AppConstants.PropertyVisitStatus.APPROVED) || status.equals(AppConstants.PropertyVisitStatus.PENDING)) {
                propertyVisit.setStatus(AppConstants.PropertyVisitStatus.CANCELLED);
                propertyVisitRepository.save(propertyVisit);
                apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Request Canceleld")
                        .success(Boolean.TRUE).build();
            } else {
                apiResponse = ApiResponse.builder().status(HttpStatus.FORBIDDEN).message("Request already Rejected")
                        .success(Boolean.FALSE).build();
            }
        } else {
            apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Request not found")
                    .success(Boolean.FALSE).build();
        }
        return apiResponse;
    }

    public ApiResponse approvePropertyVisits(PropertyVisitActionRequest requestDto) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<PropertyVisit> byUuid = propertyVisitRepository.findByUuid(requestDto.getPropertyVisitId());
        if (byUuid.isPresent()) {
            PropertyVisit propertyVisit = byUuid.get();
            if (propertyVisit.getLandlord().getUserId() == requestDto.getLandlordId()) {
                propertyVisit.setStatus(AppConstants.PropertyVisitStatus.APPROVED);
                propertyVisitRepository.save(propertyVisit);
                apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Request Approved").success(Boolean.TRUE).build();
            } else {
                apiResponse = ApiResponse.builder().status(HttpStatus.UNAUTHORIZED).message("Property does not belong to landlord").success(Boolean.FALSE).build();
            }
        } else {
            apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Request not found").success(Boolean.FALSE).build();
        }
        return apiResponse;
    }

    public ApiResponse rejectPropertyVisits(PropertyVisitActionRequest requestDto) {

        ApiResponse apiResponse = new ApiResponse();
        Optional<PropertyVisit> byUuid = propertyVisitRepository.findByUuid(requestDto.getPropertyVisitId());
        if (byUuid.isPresent()) {
            PropertyVisit propertyVisit = byUuid.get();
            if (propertyVisit.getLandlord().getUserId() == requestDto.getLandlordId()) {
                propertyVisit.setStatus(AppConstants.PropertyVisitStatus.REJECTED);
                propertyVisitRepository.save(propertyVisit);
                apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Request Rejected").success(Boolean.TRUE).build();
            } else {
                apiResponse = ApiResponse.builder().status(HttpStatus.UNAUTHORIZED).message("Property does not belong to landlord").success(Boolean.FALSE).build();
            }
        } else {
            apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Request not found").success(Boolean.FALSE).build();
        }
        return apiResponse;
    }
}
