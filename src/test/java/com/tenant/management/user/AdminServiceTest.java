//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user;


import com.tenant.management.user.entities.Admin;
import com.tenant.management.user.entities.Issue;
import com.tenant.management.user.repositories.AdminRepository;
import com.tenant.management.user.repositories.IssueRepository;
import com.tenant.management.user.requestdtos.AddIssueDetails;
import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.user.services.AdminServices.AdminService;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    UUID adminId = UUID.randomUUID();
    UUID issueId = UUID.randomUUID();
    Admin admin = Admin.builder().adminId(adminId).build();
    Issue issue = Issue.builder().issueStatus(AppConstants.IssueStatus.OPEN).build();
    @Mock
    private AdminRepository adminTestRepository;
    @Mock
    private IssueRepository issueTestRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    void getAdminId_Success() {
        when(adminTestRepository.findByUuid(adminId)).thenReturn(Optional.of(admin));
        ApiResponse result = adminService.getAdminById(adminId);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(ApiResponse.class, result.getClass());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getAdminId_NotFound() {
        when(adminTestRepository.findByUuid(adminId)).thenReturn(Optional.empty());
        ApiResponse result = adminService.getAdminById(adminId);
        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertNull(result.getData());

    }

    @Test
    void createAdmin_Success() {
        AddUserDetails userDetails = AddUserDetails.builder().adminUsername("newAdmin")
                .adminPassword("newPassword").build();
        userDetails.setAdminUsername("newAdmin");
        userDetails.setAdminPassword("newPassword");
        ApiResponse result = adminService.createAdmin(userDetails);
        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.CREATED, result.getStatus());
        Assert.assertTrue(result.getSuccess());
    }

    @Test
    void getIssueId_Success() {
        when(issueTestRepository.findByUuid(issueId)).thenReturn(Optional.of(issue));
        ApiResponse result = adminService.getIssueById(issueId);
        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(issue, result.getData());
    }

    @Test
    void getIssueId_NotFound() {
        when(issueTestRepository.findByUuid(issueId)).thenReturn(Optional.empty());
        ApiResponse result = adminService.getIssueById(issueId);
        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertNull(result.getData());
    }

    @Test
    void raiseIssue_Success() {
        AddIssueDetails issueDetails = AddIssueDetails.builder().userType("Tenant")
                .issueStatus(String.valueOf(AppConstants.IssueStatus.OPEN))
                .issueDescription("issue description").adminId(adminId).build();

        when(adminTestRepository.findByUuid(adminId)).thenReturn(Optional.of(admin));
        ApiResponse result = adminService.raiseIssue(issueDetails);
        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertTrue(result.getSuccess());
    }


    @Test
    void updateIssueStatus_Success() {
        AddIssueDetails issueDetails = AddIssueDetails.builder().userType("Tenant")
                .issueStatus(String.valueOf(AppConstants.IssueStatus.CLOSED))
                .issueDescription("issue description").adminId(adminId).issueId(issueId).build();

        when(issueTestRepository.findByUuid(issueId)).thenReturn(Optional.of(issue));
        when(adminTestRepository.findByUuid(adminId)).thenReturn(Optional.of(admin));

        ApiResponse result = adminService.updateIssueStatus(issueId, issueDetails);

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertTrue(result.getSuccess());

    }
}
