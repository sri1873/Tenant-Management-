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
    private UUID applicationId = UUID.randomUUID();;

    private AppConstants.ApplicationStatus status;

    @ManyToOne
    private Landlord landlord;

    @ManyToOne
    private Tenant tenant;

    @OneToOne
    private Property property;

    private Date submittedDate;

    private double proposedRent;

    private double applicantScore;



}
