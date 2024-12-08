package com.tenant.management.rental.services;

import com.tenant.management.property.entities.Property;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.repositories.LeaseRepository;
import com.tenant.management.rental.requestdtos.SubmitApplicationRequest;
import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.repositories.LandlordRepository;
import com.tenant.management.user.repositories.TenantRepository;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Author : K S SRI KUMAR
//Id : 24177474
@Service
public class LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;
    @Autowired
    private LandlordRepository landlordRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private PropertyRepository propertyRepository;

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
        Optional<Landlord> optionalLandlord = landlordRepository.findByUuid(leaseApplication.getLandlordId());
        Optional<Tenant> optionalTenant = tenantRepository.findByUuid(leaseApplication.getTenantId());
        Property byId = propertyRepository.getById(leaseApplication.getPropertyId());
        if (byId.getId()==null){
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Property Not Found")
                    .success(Boolean.FALSE).build();
        }
        if (optionalLandlord.isEmpty() || optionalTenant.isEmpty()){
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("User Not Found")
                    .success(Boolean.FALSE).build();

        }

        leaseRepository.save(LeaseApplication.builder()
                .tenant(optionalTenant.get())
                .landlord(optionalLandlord.get())
                .property(byId)
                .status(AppConstants.ApplicationStatus.PENDING).build());
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Submitted Successfully")
                .success(Boolean.TRUE).build();

    }

    public ApiResponse updateLeaseApplicationStatus(LeaseApplication leaseApplication) {

        leaseRepository.save(leaseApplication);
        return ApiResponse.builder().status(HttpStatus.OK).message("Status Changed")
                .success(Boolean.TRUE).build();

    }
}
