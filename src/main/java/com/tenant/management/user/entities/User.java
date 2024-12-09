//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.entities;

import com.tenant.management.utils.AppConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    private UUID userId = UUID.randomUUID();

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private AppConstants.OccupationCategories occupation;
    private boolean verified;
    public String getName() {
        return firstName + " " + lastName;
    }
    public void setName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        String[] nameParts = fullName.split(" ", 2);
        this.firstName = nameParts[0];
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
    }
}

