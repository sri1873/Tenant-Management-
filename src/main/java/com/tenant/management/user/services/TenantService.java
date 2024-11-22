package com.tenant.management.user.services;

import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.repositories.TenantRepository;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

//    TENANT APIS
    public ApiResponse getTenantById(UUID userId){
        Optional<Tenant> byUuid = tenantRepository.findByUuid(userId);
        if (byUuid.isPresent()) {
            return ApiResponse.builder().data(byUuid.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Tenant Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse createTenant(Tenant tenant){
        tenantRepository.save(tenant);
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Tenant Created")
                .success(Boolean.TRUE).build();
    }

    public ApiResponse updateTenant(UUID userId, Tenant AddUserDetails) {
        Optional<Tenant> byUuid =  tenantRepository.findByUuid(userId);
        if (byUuid.isPresent()) {
            Tenant tenant=byUuid.get();
            tenant.setFirstName(AddUserDetails.getFirstName());
            tenant.setLastName(AddUserDetails.getLastName());
            tenant.setEmail(AddUserDetails.getEmail());
            tenant.setPassword(AddUserDetails.getPassword());
            tenant.setPhoneNumber(AddUserDetails.getPhoneNumber());
            tenant.setAddress(AddUserDetails.getAddress());
            tenant.setOccupation(AddUserDetails.getOccupation());
            tenantRepository.save(tenant);
            return ApiResponse.builder().status(HttpStatus.OK).message("Tenant Details Updated")
                    .success(Boolean.TRUE).build();
        }
        return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Tenant Not Found")
                .success(Boolean.FALSE).build();
    }

    public ApiResponse deleteTenant(UUID userId) {
        tenantRepository.deleteById(userId);
        return ApiResponse.builder().status(HttpStatus.OK).message("Tenant Deleted")
                .success(Boolean.TRUE).build();
    }

}


