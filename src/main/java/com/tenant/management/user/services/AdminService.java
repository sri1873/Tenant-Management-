package com.tenant.management.user.services;

import com.tenant.management.user.entities.Admin;
import com.tenant.management.user.entities.Issue;
import com.tenant.management.user.repositories.AdminRepository;
import com.tenant.management.user.repositories.IssueRepository;
import com.tenant.management.user.requestdtos.AddIssueDetails;
import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    private IssueRepository issueRepository;

    public ApiResponse getAdminById(UUID adminId) {
        Optional<Admin> byUuid = adminRepository.findByUuid(adminId);
        if (byUuid.isPresent()) {
            return ApiResponse.builder().data(byUuid.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Admin Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse createAdmin(AddUserDetails adminDetails) {
        Admin admin = Admin.builder().adminId(UUID.randomUUID())
                .adminUsername(adminDetails.getAdminUsername())
                .adminPassword(adminDetails.getAdminPassword()).build();
        adminRepository.save(admin);
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Landlord Created")
                .success(Boolean.TRUE).build();
    }

    public ApiResponse getIssueById(UUID issueId) {
        Optional<Issue> byUuid = issueRepository.findByUuid(issueId);
        if (byUuid.isPresent()) {
            return ApiResponse.builder().data(byUuid.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Issue Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse raiseIssue(AddIssueDetails issueDetails) {
        Issue issue = Issue.builder().issueId(UUID.randomUUID())
                .userType(issueDetails.getUserType())
                .issueStatus(issueDetails.getIssueStatus())
                .issueDescription(issueDetails.getIssueDescription()).build();
        issueRepository.save(issue);
        return ApiResponse.builder().status(HttpStatus.OK).message("Issue Raised")
                .success(Boolean.TRUE).build();
    }

    public ApiResponse updateIssueStatus(UUID issueId, Issue AddIssueDetails) {
        Optional<Issue> byUuid = issueRepository.findByUuid(issueId);
        if (byUuid.isPresent()) {
            Issue issue = byUuid.get();
            issue.setIssueStatus(AddIssueDetails.getIssueStatus());
            issueRepository.save(issue);
            return ApiResponse.builder().status(HttpStatus.OK).message("Issue Status Updated")
                    .success(Boolean.TRUE).build();
        }
        return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Issue Not Found")
                .success(Boolean.FALSE).build();
    }
}
