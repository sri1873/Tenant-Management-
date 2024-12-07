package com.tenant.management.user.requestdtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddUserDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String occupation;

}
