package com.tenant.management.rental.requestDtos;

import com.tenant.management.utils.AppConstants;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class LeaseAppActionRequest {

    private UUID applicationId;
    private AppConstants.ApplicationStatus newStatus;

}
