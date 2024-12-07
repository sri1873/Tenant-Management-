package com.tenant.management.user.requestdtos;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddIssueDetails {
    private String userType;
    private String issueStatus;
    private String issueDescription;
}
