package com.tenant.management.rental.requestdtos;

import com.tenant.management.utils.AppConstants;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

//Author : K S SRI KUMAR
//Id : 24177474
@Builder
@Data
public class PropertyVisitActionRequest {

    private UUID propertyVisitId;
    private AppConstants.PropertyVisitStatus newStatus;

}
