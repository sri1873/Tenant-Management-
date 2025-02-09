package com.tenant.management.rental.implementation.command;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.rental.entities.PropertyVisit;
import com.tenant.management.rental.services.PropertyVisitService;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;

//Author : K S SRI KUMAR
//Id : 24177474
public class SchedulePropertyVisitCommand implements Command {

    private final PropertyVisit propertyVisit;
    private final AppConstants.PropertyVisitStatus newStatus;
    private final PropertyVisitService propertyVisitService;
    private AppConstants.PropertyVisitStatus prevStatus;

    public SchedulePropertyVisitCommand(PropertyVisit propertyVisit, AppConstants.PropertyVisitStatus newStatus, PropertyVisitService propertyVisitService) {
        this.propertyVisit = propertyVisit;
        this.propertyVisitService = propertyVisitService;
        this.newStatus = newStatus;
    }


    @Override
    public ApiResponse execute() {
        prevStatus = propertyVisit.getStatus();
        propertyVisit.setStatus(newStatus);
        return propertyVisitService.updatePropertyVisitStatus(propertyVisit);

    }

    @Override
    public ApiResponse undo() {
        propertyVisit.setStatus(prevStatus);
        return propertyVisitService.updatePropertyVisitStatus(propertyVisit);
    }
}
