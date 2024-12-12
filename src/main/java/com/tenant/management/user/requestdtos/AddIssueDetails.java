//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.requestdtos;

import com.tenant.management.utils.AppConstants;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class AddIssueDetails {
    private UUID adminId;
    private String userType;
    private AppConstants.IssueStatus issueStatus;
    private String issueDescription;
}
