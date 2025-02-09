//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.entities;

import com.tenant.management.utils.AppConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private AppConstants.IssueStatus issueStatus;
    private String issueDescription;
}
