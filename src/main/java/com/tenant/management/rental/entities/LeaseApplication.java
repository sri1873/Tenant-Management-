package com.tenant.management.rental.entities;

import com.tenant.management.property.entities.Property;
import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.entities.Tenant;
import com.tenant.management.utils.AppConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lease_applications")
public class LeaseApplication {

    @Id
    @Builder.Default
    private UUID applicationId = UUID.randomUUID();

    private AppConstants.ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private LocalDate submittedDate;

    private double proposedRent;

    private double applicantScore;
}
