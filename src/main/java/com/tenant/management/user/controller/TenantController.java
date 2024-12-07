package com.tenant.management.user.controller;


import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.user.services.TenantServices.TenantService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TenantController {
    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/getTenantById/{userId}")
    public ResponseEntity<ApiResponse> getTenantById(@RequestBody UUID userId) {
        ApiResponse tenant = tenantService.getTenantById(userId);
        return new ResponseEntity<>(tenant, HttpStatus.OK);
    }

    @PostMapping("/tenant")
    public ResponseEntity<ApiResponse> createTenant(@RequestBody AddUserDetails addUserDetails) {
        ApiResponse createdTenant = tenantService.createTenant(addUserDetails);
        return new ResponseEntity<>(createdTenant, HttpStatus.CREATED);
    }

    @PutMapping("/tenant/{userId}")
    public ResponseEntity<ApiResponse> updateTenant(@RequestBody UUID userId, AddUserDetails addUserDetails) {
        ApiResponse updatedTenant = tenantService.updateTenant(userId, addUserDetails);
        return new ResponseEntity<>(updatedTenant, HttpStatus.OK);
    }

    @DeleteMapping("/tenant/{id}")
    public ResponseEntity<ApiResponse> deleteTenant(@RequestBody UUID userId) {

        return new ResponseEntity<>(tenantService.deleteTenant(userId), HttpStatus.NO_CONTENT);
    }
}
