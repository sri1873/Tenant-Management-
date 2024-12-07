package com.tenant.management.user.controller;

import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.requestdtos.AddIssueDetails;
import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.user.services.AdminService;
import com.tenant.management.user.services.TenantService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TenantController {
    private final TenantService tenantService;
    private AdminService adminService;

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
    public ResponseEntity<ApiResponse> updateTenant(@RequestBody UUID userId, Tenant AddUserDetails) {
        ApiResponse updatedTenant = tenantService.updateTenant(userId, AddUserDetails);
        return new ResponseEntity<>(updatedTenant, HttpStatus.OK);
    }

    @DeleteMapping("/tenant/{id}")
    public ResponseEntity<ApiResponse> deleteTenant(@RequestBody UUID userId) {

        return new ResponseEntity<>(tenantService.deleteTenant(userId), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/tenant/issue")
    public ResponseEntity<ApiResponse> raiseIssue(@RequestBody AddIssueDetails addIssueDetails) {
        ApiResponse issueRaised = adminService.raiseIssue(addIssueDetails, admin);
        return new ResponseEntity<>(issueRaised, HttpStatus.CREATED);
    }
}
