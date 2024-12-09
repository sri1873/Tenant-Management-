//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.controller;

import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.user.services.AdminServices.AdminService;
import com.tenant.management.user.services.LandlordServices.LandlordService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LandlordController {
    private final LandlordService landlordService;
    private AdminService adminService;

    @Autowired
    public LandlordController(LandlordService landlordService) {
        this.landlordService = landlordService;
    }

    @GetMapping("/getLandlordById/{userId}")
    public ResponseEntity<ApiResponse> getLandlordById(@RequestBody UUID userId) {
        ApiResponse landlord = landlordService.getLandlordById(userId);
        return new ResponseEntity<>(landlord, HttpStatus.OK);
    }

    @PostMapping("/landlord")
    public ResponseEntity<ApiResponse> createLandlord(@RequestBody AddUserDetails userDetails) {
        ApiResponse createdLandlord = landlordService.createLandlord(userDetails);
        return new ResponseEntity<>(createdLandlord, HttpStatus.CREATED);
    }

    @PutMapping("/landlord/{userId}")
    public ResponseEntity<ApiResponse> updateLandlord(@RequestBody UUID userId, AddUserDetails addUserDetails) {
        ApiResponse updatedLandlord = landlordService.updateLandlord(userId, addUserDetails);
        return new ResponseEntity<>(updatedLandlord, HttpStatus.OK);
    }

    @DeleteMapping("/landlord/{id}")
    public ResponseEntity<ApiResponse> deleteLandlord(@RequestBody UUID userId) {
        ApiResponse response = landlordService.deleteLandlord(userId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
