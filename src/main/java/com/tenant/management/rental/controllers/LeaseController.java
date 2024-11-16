package com.tenant.management.rental.controllers;

import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.requestDtos.LeaseAppActionRequest;
import com.tenant.management.rental.requestDtos.SubmitApplicationRequest;
import com.tenant.management.rental.services.LeaseService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @PostMapping("/submitLeaseApplication")
    public ResponseEntity<ApiResponse> submitLeaseAppication(@RequestBody SubmitApplicationRequest leaseApplication){
        ApiResponse applications = leaseService.submitLeaseApplications(leaseApplication);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    @GetMapping("/getLeaseApplication/{applicationId}")
    public ResponseEntity<ApiResponse> getLeaseApplication(@PathVariable UUID applicationId) {
        ApiResponse applications = leaseService.getLeaseApplication(applicationId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/getLeaseApplicationsByLandlord/{landlordId}")
    public ResponseEntity<ApiResponse> getLandlordLeaseApplications(@PathVariable UUID landlordId) {
        ApiResponse applications = leaseService.getLeaseApplicationsByLandlord(landlordId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    @GetMapping("/getLeaseApplicationsByProperty/{propertyId}")
    public ResponseEntity<ApiResponse> getPropertyLeaseApplications(@PathVariable UUID propertyId) {
        ApiResponse applications = leaseService.getLeaseApplicationByProperty(propertyId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @PostMapping("/withdrawLeaseApplication/{applicationId}")
    public ResponseEntity<ApiResponse> withdrawLeaseAppication(@PathVariable UUID applicationId){
        ApiResponse applications = leaseService.withdrawLeaseApplications(applicationId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    @PostMapping("/approveLeaseApplication")
    public ResponseEntity<ApiResponse> approveLeaseAppication(@RequestBody LeaseAppActionRequest requestDto){
        ApiResponse applications = leaseService.approveLeaseApplications(requestDto);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
    @PostMapping("/rejectLeaseApplication")
    public ResponseEntity<ApiResponse> rejectLeaseAppication(@PathVariable LeaseAppActionRequest requestDto){
        ApiResponse applications = leaseService.rejectLeaseApplications(requestDto);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }



}
