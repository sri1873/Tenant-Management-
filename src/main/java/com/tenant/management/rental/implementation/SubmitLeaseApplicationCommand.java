package com.tenant.management.rental.implementation;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.services.LeaseService;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;

public class SubmitLeaseApplicationCommand implements Command {

    private final LeaseApplication leaseApplication;
    private final AppConstants.ApplicationStatus newStatus;
    private final LeaseService leaseService;
    private AppConstants.ApplicationStatus prevStatus;

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
