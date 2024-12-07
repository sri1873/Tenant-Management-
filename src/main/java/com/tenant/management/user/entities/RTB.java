package com.tenant.management.user.entities;

import com.tenant.management.property.entities.Property;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rtb")
@Data
@Builder
public class RTB {
    @Id
    private UUID rtbId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "adminId")
    private Admin admin;

    @OneToOne
    @JoinColumn(name = "propertyId")
    private Property property;

    private boolean rtbVerified;
    private LocalDate verifiedDate;
    private String remarks;


}
