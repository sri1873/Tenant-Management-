//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.entities;

import com.tenant.management.property.entities.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rtb")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RTB {
    @Id
    @Builder.Default
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
