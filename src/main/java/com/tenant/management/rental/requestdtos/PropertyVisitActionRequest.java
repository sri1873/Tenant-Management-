package com.tenant.management.rental.requestDtos;

import com.tenant.management.utils.AppConstants;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PropertyVisitActionRequest {

    private UUID propertyVisitId;
    private AppConstants.PropertyVisitStatus newStatus;

}
