package com.tenant.management.user.requestdtos;

import com.tenant.management.utils.AppConstants;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddUserDetails {
    //User Details
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private AppConstants.OccupationCategories occupation;

    //    Admin Details
    private String adminUsername;
    private String adminPassword;


}
