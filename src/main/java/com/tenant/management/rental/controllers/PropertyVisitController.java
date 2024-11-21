package com.tenant.management.rental.controllers;

import com.tenant.management.rental.requestDtos.PropertyVisitActionRequest;
import com.tenant.management.rental.requestDtos.SubmitApplicationRequest;
import com.tenant.management.rental.services.PropertyVisitService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PropertyVisitController {

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

    @PostMapping("/cancelPropertyVisit/{propertyVisitId}")
    public ResponseEntity<ApiResponse> cancelPropertyVisit(@PathVariable UUID propertyVisitId) {
        ApiResponse propertyVisits = propertyVisitService.cancelPropertyVisits(propertyVisitId);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }

    @PostMapping("/approvePropertyVisit")
    public ResponseEntity<ApiResponse> approvePropertyVisit(@RequestBody PropertyVisitActionRequest requestDto) {
        ApiResponse propertyVisits = propertyVisitService.approvePropertyVisits(requestDto);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }

    @PostMapping("/rejectPropertyVisit")
    public ResponseEntity<ApiResponse> rejectPropertyVisit(@PathVariable PropertyVisitActionRequest requestDto) {
        ApiResponse propertyVisits = propertyVisitService.rejectPropertyVisits(requestDto);
        return new ResponseEntity<>(propertyVisits, HttpStatus.OK);
    }


}
