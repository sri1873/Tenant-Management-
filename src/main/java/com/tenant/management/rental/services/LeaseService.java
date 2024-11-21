package com.tenant.management.rental.services;

import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.repositories.LeaseRepository;
import com.tenant.management.rental.requestDtos.LeaseAppActionRequest;
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
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    public ApiResponse getLeaseApplication(UUID applicationId) {
        Optional<LeaseApplication> byUuid = leaseRepository.findByUuid(applicationId);
        if (byUuid.isPresent()) {
            return ApiResponse.builder().data(byUuid.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Application Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse getLeaseApplicationsByLandlord(UUID landlordId) {

        Optional<List<LeaseApplication>> allByLandlordId = leaseRepository.findAllByLandlordId(landlordId);
        if (allByLandlordId.isPresent()) {
            return ApiResponse.builder().data(allByLandlordId.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Applications for landlord not found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse getLeaseApplicationsByTenant(UUID tenantId) {

        Optional<List<LeaseApplication>> allByTenantId = leaseRepository.findAllByTenantId(tenantId);
        if (allByTenantId.isPresent()) {
            return ApiResponse.builder().data(allByTenantId.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Tenant Application not found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse getLeaseApplicationByProperty(UUID propertyId) {

        Optional<List<LeaseApplication>> allByPropertyId = leaseRepository.findAllByPropertyId(propertyId);
        if (allByPropertyId.isPresent()) {
            return ApiResponse.builder().data(allByPropertyId.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Application for property not found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse submitLeaseApplications(SubmitApplicationRequest leaseApplication) {


//        leaseRepository.save(leaseApplication);
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Submitted Successfully")
                .success(Boolean.TRUE).build();

    }

    public ApiResponse withdrawLeaseApplications(UUID applicationId) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<LeaseApplication> byUuid = leaseRepository.findByUuid(applicationId);
        if (byUuid.isPresent()) {
            LeaseApplication leaseApplication = byUuid.get();
            AppConstants.ApplicationStatus status = leaseApplication.getStatus();
            if (status.equals(AppConstants.ApplicationStatus.APPROVED) || status.equals(AppConstants.ApplicationStatus.PENDING)) {
                leaseApplication.setStatus(AppConstants.ApplicationStatus.WITHDRAWN);
                leaseRepository.save(leaseApplication);
                apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Application Withdrawn")
                        .success(Boolean.TRUE).build();
            } else {
                apiResponse = ApiResponse.builder().status(HttpStatus.FORBIDDEN).message("Application already Rejected")
                        .success(Boolean.FALSE).build();
            }
        } else {
            apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Application not found")
                    .success(Boolean.FALSE).build();
        }
        return apiResponse;
    }

    public ApiResponse approveLeaseApplications(LeaseAppActionRequest requestDto) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<LeaseApplication> byUuid = leaseRepository.findByUuid(requestDto.getApplicationId());
        if (byUuid.isPresent()) {
            LeaseApplication leaseApplication = byUuid.get();
            if (leaseApplication.getLandlord().getUserId() == requestDto.getLandlordId()) {
                leaseApplication.setStatus(AppConstants.ApplicationStatus.APPROVED);
                leaseRepository.save(leaseApplication);
                apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Application Approved").success(Boolean.TRUE).build();
            } else {
                apiResponse = ApiResponse.builder().status(HttpStatus.UNAUTHORIZED).message("Property does not belong to landlord").success(Boolean.FALSE).build();
            }
        } else {
            apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Application not found").success(Boolean.FALSE).build();
        }

        return apiResponse;

    }

    public ApiResponse rejectLeaseApplications(LeaseAppActionRequest requestDto) {

        ApiResponse apiResponse = new ApiResponse();
        Optional<LeaseApplication> byUuid = leaseRepository.findByUuid(requestDto.getApplicationId());
        if (byUuid.isPresent()) {
            LeaseApplication leaseApplication = byUuid.get();
            if (leaseApplication.getLandlord().getUserId() == requestDto.getLandlordId()) {
                leaseApplication.setStatus(AppConstants.ApplicationStatus.REJECTED);
                leaseRepository.save(leaseApplication);
                apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Application Rejected").success(Boolean.TRUE).build();
            } else {
                apiResponse = ApiResponse.builder().status(HttpStatus.UNAUTHORIZED).message("Property does not belong to landlord").success(Boolean.FALSE).build();
            }
        } else {
            apiResponse = ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Application not found").success(Boolean.FALSE).build();
        }

        return apiResponse;

    }


}
