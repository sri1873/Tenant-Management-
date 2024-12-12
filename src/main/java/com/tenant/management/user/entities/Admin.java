//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "admin")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @Builder.Default
    private UUID adminId = UUID.randomUUID();

    private String adminUsername;
    private String adminPassword;


}
