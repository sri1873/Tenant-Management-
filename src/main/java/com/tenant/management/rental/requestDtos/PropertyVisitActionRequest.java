package com.tenant.management.rental.requestDtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class PropertyVisitActionRequest {

    private UUID propertyVisitId;
    private UUID landlordId;

}
