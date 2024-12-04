package com.tenant.management.user.services;

import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.repositories.LandlordRepository;
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

    public void registerObserver(LandlordObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(LandlordObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Landlord landlord) {
        for (LandlordObserver observer : observers) {
            observer.onLandlordChange(landlord);
        }
    }

    //    LANDLORD APIS

    public ApiResponse getLandlordById(UUID userId){
        Optional<Landlord> byUuid = landlordRepository.findByUuid(userId);
        if (byUuid.isPresent()) {
            return ApiResponse.builder().data(byUuid.get()).status(HttpStatus.OK).message("")
                    .success(Boolean.TRUE).build();
        } else {
            return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Application Not Found")
                    .success(Boolean.FALSE).build();
        }
    }
    public ApiResponse createLandlord(Landlord landlord){
        landlordRepository.save(landlord);
        notifyObservers(landlord);
        return ApiResponse.builder().status(HttpStatus.CREATED).message("Landlord Created")
                .success(Boolean.TRUE).build();
    }

    public ApiResponse updateLandlord(UUID userId, Landlord AddUserDetails) {
        Optional<Landlord> byUuid = landlordRepository.findByUuid(userId);

        if (byUuid.isPresent()) {
            Landlord landlord=byUuid.get();
            landlord.setFirstName(AddUserDetails.getFirstName());
            landlord.setLastName(AddUserDetails.getLastName());
            landlord.setEmail(AddUserDetails.getEmail());
            landlord.setPassword(AddUserDetails.getPassword());
            landlord.setPhoneNumber(AddUserDetails.getPhoneNumber());
            landlord.setAddress(AddUserDetails.getAddress());
            landlord.setOccupation(AddUserDetails.getOccupation());
            landlordRepository.save(landlord);
            notifyObservers(landlord);
            return ApiResponse.builder().status(HttpStatus.OK).message("Landlord Details Updated")
                    .success(Boolean.TRUE).build();
        }
        return ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("Landlord Not Found")
                .success(Boolean.FALSE).build();
    }
    public void deleteLandlord(UUID userId) {
        landlordRepository.deleteById(userId);
        ApiResponse.builder().status(HttpStatus.OK).message("Landlord Deleted")
                .success(Boolean.TRUE).build();
    }
}
