package com.tenant.management.rental.controllers;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.rental.entities.PropertyVisit;
import com.tenant.management.rental.implementation.command.CommandInvoker;
import com.tenant.management.rental.implementation.command.SchedulePropertyVisitCommand;
import com.tenant.management.rental.requestdtos.PropertyVisitActionRequest;
import com.tenant.management.rental.requestdtos.SubmitApplicationRequest;
import com.tenant.management.rental.services.PropertyVisitService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//Author : K S SRI KUMAR
//Id : 24177474
@RestController
public class PropertyVisitController {

    private final CommandInvoker commandInvoker = new CommandInvoker();
    @Autowired
    private PropertyVisitService propertyVisitService;

    @PostMapping("/submitPropertyVisit")
    public ResponseEntity<ApiResponse> submitPropertyVisit(@RequestBody SubmitApplicationRequest propertyVisit) {
        ApiResponse propertyVisits = propertyVisitService.submitPropertyVisits(propertyVisit);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }

    @GetMapping("/getPropertyVisit/{propertyVisitId}")
    public ResponseEntity<ApiResponse> getPropertyVisit(@PathVariable UUID propertyVisitId) {
        ApiResponse propertyVisits = propertyVisitService.getPropertyVisit(propertyVisitId);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }

    @GetMapping("/getPropertyVisitsByLandlord/{landlordId}")
    public ResponseEntity<ApiResponse> getLandlordPropertyVisits(@PathVariable UUID landlordId) {
        ApiResponse propertyVisits = propertyVisitService.getPropertyVisitsByLandlord(landlordId);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }

    @GetMapping("/getPropertyVisitsByTenant/{tenantId}")
    public ResponseEntity<ApiResponse> getTenantPropertyVisits(@PathVariable UUID tenantId) {
        ApiResponse propertyVisits = propertyVisitService.getPropertyVisitsByTenant(tenantId);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }

    @GetMapping("/getPropertyVisitsByProperty/{propertyId}")
    public ResponseEntity<ApiResponse> getPropertyPropertyVisits(@PathVariable UUID propertyId) {
        ApiResponse propertyVisits = propertyVisitService.getPropertyVisitByProperty(propertyId);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }

    @PutMapping("/updatePropertyVisitStatus")
    public ResponseEntity<ApiResponse> updatePropertyVisitStatus(@RequestBody PropertyVisitActionRequest actionRequest) {
        Object data = propertyVisitService.getPropertyVisit(actionRequest.getPropertyVisitId()).getData();

        Command command = new SchedulePropertyVisitCommand((PropertyVisit) data, actionRequest.getNewStatus(), propertyVisitService);
        return new ResponseEntity<>(commandInvoker.executeCommand(command), HttpStatus.OK);
    }

    @PutMapping("/undoPropertyVisitStatus")
    public ResponseEntity<ApiResponse> undoPropertyVisitStatus() {
        return new ResponseEntity<>(commandInvoker.undoLastCommand(), HttpStatus.OK);
    }
}
