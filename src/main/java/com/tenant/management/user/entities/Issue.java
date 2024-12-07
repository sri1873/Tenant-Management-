package com.tenant.management.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "issue")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Issue {
    @Id
    private UUID issueId = UUID.randomUUID();
    private String userType;
    private String issueStatus;
    private String issueDescription;


}
