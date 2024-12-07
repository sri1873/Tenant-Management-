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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    private IssueRepository issueRepository;

    //    Observer Pattern implementation
    private final List<AdminObserver> observers;

    public AdminService() {
        this.observers = new ArrayList<>();
    }

    public void attach(AdminObserver observer) {
        observers.add(observer);
    }

    public void detach(AdminObserver observer) {
        observers.remove(observer);
    }

    private void notify(Admin admin) {
        for (AdminObserver observer : observers) {
            observer.onAdminChange(admin);
        }
    }
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
        notify(admin);
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Admin Created")
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

    public ApiResponse raiseIssue(AddIssueDetails issueDetails, Admin admin) {
        Issue issue = Issue.builder().issueId(UUID.randomUUID())
                .userType(issueDetails.getUserType())
                .issueStatus(issueDetails.getIssueStatus())
                .issueDescription(issueDetails.getIssueDescription()).build();
        issueRepository.save(issue);
        notify(admin);
        return ApiResponse.builder().status(HttpStatus.OK).message("Issue Raised")
                .success(Boolean.TRUE).build();
    }

    public ApiResponse updateIssueStatus(UUID issueId, Issue AddIssueDetails, Admin admin) {
        Optional<Issue> byUuid = issueRepository.findByUuid(issueId);
        if (byUuid.isPresent()) {
            Issue issue = byUuid.get();
            issue.setIssueStatus(AddIssueDetails.getIssueStatus());
            issueRepository.save(issue);
            notify(admin);
            return ApiResponse.builder().status(HttpStatus.OK).message("Issue Status Updated")
                    .success(Boolean.TRUE).build();
        }
        return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Issue Not Found")
                .success(Boolean.FALSE).build();
    }
}