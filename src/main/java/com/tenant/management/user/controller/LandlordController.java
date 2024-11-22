package com.tenant.management.user.controller;

import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.services.LandlordService;

import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LandlordController {
    private final LandlordService landlordService;

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
    public ResponseEntity<ApiResponse> createLandlord(@RequestBody Landlord landlord) {
        ApiResponse createdLandlord = landlordService.createLandlord(landlord);
        return new ResponseEntity<>(createdLandlord,HttpStatus.CREATED);
    }
    @PutMapping("/landlord/{userId}")
    public ResponseEntity<ApiResponse> updateLandlord(@RequestBody UUID userId, Landlord AddUserDetails) {
        ApiResponse updatedLandlord = landlordService.updateLandlord(userId, AddUserDetails);
        return new ResponseEntity<>(updatedLandlord, HttpStatus.OK);
    }

    @DeleteMapping("/landlord/{id}")
    public ResponseEntity<HttpStatus> deleteLandlord(@RequestBody UUID userId) {
        landlordService.deleteLandlord(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
