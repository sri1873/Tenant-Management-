//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user.services.LandlordServices;

import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.repositories.LandlordRepository;
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
public class LandlordService {

    private final LandlordRepository landlordRepository;
    private final List<LandlordObserver> observers;


    @Autowired
    public LandlordService(LandlordRepository landlordRepository) {
        this.landlordRepository = landlordRepository;
        this.observers = new ArrayList<>(); // Initialize the observers list
    }

    public void attach(LandlordObserver observer) {
        observers.add(observer);
    }

    public void detach(LandlordObserver observer) {
        observers.remove(observer);
    }

    private void notify(Landlord landlord) {
        for (LandlordObserver observer : observers) {
            observer.onLandlordChange(landlord);
        }
    }

    //    LANDLORD APIS

    public ApiResponse getLandlordById(UUID userId) {
        Optional<Landlord> byUuid = landlordRepository.findByUuid(userId);
        if (byUuid.isPresent()) {
            return ApiResponse.builder().data(byUuid.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Landlord Not Found")
                    .success(Boolean.FALSE).build();
        }
    }

    public ApiResponse createLandlord(AddUserDetails userDetails) {
        Landlord landlord = Landlord.builder().email(userDetails.getEmail()).userId(UUID.randomUUID())
                .address(userDetails.getAddress())
                .phoneNumber(userDetails.getPhoneNumber())
                .occupation(userDetails.getOccupation())
                .password(userDetails.getPassword())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName()).build();
        landlordRepository.save(landlord);
        notify(landlord); //to notify the creation of landlord
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Landlord Created")
                .success(Boolean.TRUE).build();
    }

    public ApiResponse updateLandlord(UUID userId, AddUserDetails addUserDetails) {
        Optional<Landlord> byUuid = landlordRepository.findByUuid(userId);

        if (byUuid.isPresent()) {
            Landlord landlord = byUuid.get();
            landlord.setFirstName(addUserDetails.getFirstName());
            landlord.setLastName(addUserDetails.getLastName());
            landlord.setEmail(addUserDetails.getEmail());
            landlord.setPassword(addUserDetails.getPassword());
            landlord.setPhoneNumber(addUserDetails.getPhoneNumber());
            landlord.setAddress(addUserDetails.getAddress());
            landlord.setOccupation(addUserDetails.getOccupation());
            landlordRepository.save(landlord);
            notify(landlord); //to notify the updates made to Landlord
            return ApiResponse.builder().status(HttpStatus.OK).message("Landlord Details Updated")
                    .success(Boolean.TRUE).build();
        }
        return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Landlord Not Found")
                .success(Boolean.FALSE).build();
    }

    public ApiResponse deleteLandlord(UUID userId) {
        landlordRepository.deleteById(userId);
        return ApiResponse.builder().status(HttpStatus.OK).message("Landlord Deleted")
                .success(Boolean.TRUE).build();
    }
}
