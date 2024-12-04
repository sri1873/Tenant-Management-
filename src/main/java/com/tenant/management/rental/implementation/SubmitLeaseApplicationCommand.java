package com.tenant.management.rental.implementation;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.rental.entities.LeaseApplication;

import com.tenant.management.rental.services.LeaseService;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;

public class SubmitLeaseApplicationCommand implements Command {

    private LeaseApplication leaseApplication;
    private AppConstants.ApplicationStatus prevStatus;
    private AppConstants.ApplicationStatus newStatus;
    private LeaseService leaseService;

    public SubmitLeaseApplicationCommand(LeaseApplication leaseApplication, AppConstants.ApplicationStatus newStatus, LeaseService leaseService) {
        this.leaseApplication = leaseApplication;
        this.leaseService = leaseService;
        this.newStatus = newStatus;
    }


    @Override
    public ApiResponse execute() {
        prevStatus = leaseApplication.getStatus();
        leaseApplication.setStatus(newStatus);
        return leaseService.updateLeaseApplicationStatus(leaseApplication);

    }

    @Override
    public ApiResponse undo() {
        leaseApplication.setStatus(prevStatus);
        return leaseService.updateLeaseApplicationStatus(leaseApplication);
    }
}
