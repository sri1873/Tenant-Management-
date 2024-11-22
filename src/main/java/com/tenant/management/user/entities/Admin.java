package com.tenant.management.user.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table
@Builder
public class Admin {
    @Id
    @Builder.Default
    private UUID adminId = UUID.randomUUID();

    private String adminUsername;
    private String adminPassword;


}
