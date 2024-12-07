package com.tenant.management.rental.controllers;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.implementation.CommandInvoker;
import com.tenant.management.rental.implementation.SubmitLeaseApplicationCommand;
import com.tenant.management.rental.requestdtos.LeaseAppActionRequest;
import com.tenant.management.rental.requestdtos.SubmitApplicationRequest;
import com.tenant.management.rental.services.LeaseService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LeaseController {

    private final CommandInvoker commandInvoker = new CommandInvoker();
    @Autowired
    private LeaseService leaseService;

    @PostMapping("/submitLeaseApplication")
    public ResponseEntity<ApiResponse> submitLeaseAppication(@RequestBody SubmitApplicationRequest leaseApplication) {
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

    @GetMapping("/getLeaseApplicationsByTenant/{tenantId}")
    public ResponseEntity<ApiResponse> getTenantLeaseApplications(@PathVariable UUID tenantId) {
        ApiResponse applications = leaseService.getLeaseApplicationsByTenant(tenantId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/getLeaseApplicationsByProperty/{propertyId}")
    public ResponseEntity<ApiResponse> getPropertyLeaseApplications(@PathVariable UUID propertyId) {
        ApiResponse applications = leaseService.getLeaseApplicationByProperty(propertyId);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @PutMapping("/updateLeaseApplicationStatus")
    public ResponseEntity<ApiResponse> updateLeaseApplicationStatus(@RequestBody LeaseAppActionRequest actionRequest) {
        Object data = leaseService.getLeaseApplication(actionRequest.getApplicationId()).getData();

        Command command = new SubmitLeaseApplicationCommand((LeaseApplication) data, actionRequest.getNewStatus(), leaseService);
        return new ResponseEntity<>(commandInvoker.executeCommand(command), HttpStatus.OK);
    }

    @PutMapping("/undoLeaseApplicationStatus")
    public ResponseEntity<ApiResponse> undoLeaseApplicationStatus() {
        return new ResponseEntity<>(commandInvoker.undoLastCommand(), HttpStatus.OK);
    }


}
