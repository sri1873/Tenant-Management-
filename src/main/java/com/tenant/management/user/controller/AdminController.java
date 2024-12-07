package com.tenant.management.user.controller;

import com.tenant.management.user.entities.Issue;
import com.tenant.management.user.requestdtos.AddIssueDetails;
import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.user.services.AdminService;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getAdminById/{adminId}")
    public ResponseEntity<ApiResponse> getAdminById(@RequestBody UUID adminId) {
        ApiResponse admin = adminService.getAdminById(adminId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody AddUserDetails addUserDetails) {
        ApiResponse createdAdmin = adminService.createAdmin(addUserDetails);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/getIssueById/{issueId}")
    public ResponseEntity<ApiResponse> getIssueById(@RequestBody UUID issueId) {
        ApiResponse issue = adminService.getIssueById(issueId);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @PutMapping("/issue/{issueId}")
    public ResponseEntity<ApiResponse> updateIssueStatus(@RequestBody AddIssueDetails issueDetails) {
        ApiResponse updatedStatus = adminService.updateIssueStatus(issueDetails);
        return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
    }
    @PostMapping("/issue/raiseIssue")
    public ResponseEntity<ApiResponse> raiseIssue(@RequestBody AddIssueDetails addIssueDetails) {
        ApiResponse issueRaised = adminService.raiseIssue(addIssueDetails);
        return new ResponseEntity<>(issueRaised, HttpStatus.CREATED);
    }
}
