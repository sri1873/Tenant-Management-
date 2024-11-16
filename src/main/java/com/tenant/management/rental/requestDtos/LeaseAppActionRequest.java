package com.tenant.management.rental.requestDtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class LeaseAppActionRequest {

    private UUID applicationId;
    private UUID landlordId;

}
