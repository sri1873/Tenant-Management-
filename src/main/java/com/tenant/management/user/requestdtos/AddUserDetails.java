package com.tenant.management.user.requestDtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class AddUserDetails {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String occupation;

}
