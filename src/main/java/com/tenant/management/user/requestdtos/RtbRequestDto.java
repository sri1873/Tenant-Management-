package com.tenant.management.user.requestdtos;

import com.tenant.management.property.entities.Property;
import com.tenant.management.user.entities.Admin;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
