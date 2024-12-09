package com.tenant.management.rental.requestdtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

//Author : K S SRI KUMAR
//Id : 24177474
@Data
@Builder
public class SubmitApplicationRequest {

    private UUID landlordId;
    private UUID propertyId;
    private UUID tenantId;

}
