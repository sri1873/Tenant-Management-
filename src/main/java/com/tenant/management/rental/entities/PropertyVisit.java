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
import java.util.UUID;

//Author : K S SRI KUMAR
//Id : 24177474
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "property_visit_request")
public class PropertyVisit {

    @Id
    @GeneratedValue
    private UUID propertyVisitId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private LocalDate visitDate;

    private AppConstants.PropertyVisitStatus status;
}
