package com.tenant.management.user.requestdtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class AddIssueDetails {
    private UUID adminId;
    private UUID issueId;
    private String userType;
    private String issueStatus;
    private String issueDescription;
}
