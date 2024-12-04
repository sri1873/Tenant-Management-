package com.tenant.management.rental.implementation;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.rental.entities.PropertyVisit;
import com.tenant.management.rental.services.PropertyVisitService;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;

public class SchedulePropertyVisitCommand implements Command {

    private PropertyVisit propertyVisit;
    private AppConstants.PropertyVisitStatus prevStatus;
    private AppConstants.PropertyVisitStatus newStatus;
    private PropertyVisitService propertyVisitService;

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
