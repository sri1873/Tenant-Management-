//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.services.AdminServices;

import com.tenant.management.user.entities.Admin;
import com.tenant.management.user.entities.Issue;
import com.tenant.management.user.repositories.AdminRepository;
import com.tenant.management.user.repositories.IssueRepository;
import com.tenant.management.user.requestdtos.AddIssueDetails;
import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    //    Observer Pattern implementation
    private final List<AdminObserver> observers;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private IssueRepository issueRepository;

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

    public ApiResponse raiseIssue(AddIssueDetails issueDetails) {
        Optional<Admin> optionalAdmin = adminRepository.findByUuid(issueDetails.getAdminId());
        Issue issue = Issue.builder().issueId(UUID.randomUUID())
                .userType(issueDetails.getUserType())
                .issueStatus(AppConstants.IssueStatus.OPEN)
                .issueDescription(issueDetails.getIssueDescription()).build();
        issueRepository.save(issue);
        if (optionalAdmin.isPresent()) {
            notify(optionalAdmin.get());
        }
        return ApiResponse.builder().status(HttpStatus.OK).message("Issue Raised")
                .success(Boolean.TRUE).build();
    }

    public ApiResponse updateIssueStatus(UUID issueId, AddIssueDetails issueDetails) {
        Optional<Issue> byUuid = issueRepository.findByUuid(issueId);
        Optional<Admin> optionalAdmin = adminRepository.findByUuid(issueDetails.getAdminId());
        if (byUuid.isPresent()) {
            Issue issue = byUuid.get();
            issue.setIssueStatus(issueDetails.getIssueStatus());
            issueRepository.save(issue);
            notify(optionalAdmin.get());
            return ApiResponse.builder().status(HttpStatus.OK).message("Issue Status Updated")
                    .success(Boolean.TRUE).build();
        }
        return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Issue Not Found")
                .success(Boolean.FALSE).build();
    }
}
