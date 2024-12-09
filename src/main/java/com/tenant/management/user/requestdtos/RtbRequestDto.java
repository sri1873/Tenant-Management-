//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.requestdtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class RtbRequestDto {
    private UUID adminId;
    private UUID propertyId;
    private boolean rtbVerified;
    private LocalDate verifiedDate;
    private String remarks;
}
