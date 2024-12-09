//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user;

import com.tenant.management.user.entities.Landlord;
import com.tenant.management.user.repositories.LandlordRepository;
import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.user.services.LandlordServices.LandlordService;
import com.tenant.management.utils.ApiResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LandlordServiceTest {

    private final UUID landlordId = UUID.randomUUID();
    Landlord landlord = Landlord.builder().userId(landlordId).verified(Boolean.TRUE).build();

    @Mock
    private LandlordRepository testRepository;

    @InjectMocks
    private LandlordService testService;

    @Test
    void getLandlordById_Success() {
        when(testRepository.findByUuid(landlordId)).thenReturn(Optional.of(landlord));
        ApiResponse result = testService.getLandlordById(landlordId);
        Assert.assertEquals("", result.getMessage());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void getLandlordById_Failure() {
        when(testRepository.findByUuid(landlordId)).thenReturn(Optional.empty());
        ApiResponse result = testService.getLandlordById(landlordId);
        Assert.assertEquals("Landlord Not Found", result.getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void createLandlord_Success() {
        AddUserDetails requestDTO = AddUserDetails.builder()
                .email("test123@gmail.com").firstName("test").lastName("user").build();
        when(testRepository.save(any(Landlord.class))).thenReturn(landlord);
        ApiResponse result = testService.createLandlord(requestDTO);
        Assert.assertEquals("Landlord Created", result.getMessage());
        Assert.assertEquals(HttpStatus.CREATED, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void updateLandlord_Success() {
        when(testRepository.findByUuid(landlordId)).thenReturn(Optional.of(landlord));
        AddUserDetails requestDTO = AddUserDetails.builder()
                .email("test123@gmail.com").firstName("test").lastName("user").build();
        when(testRepository.save(any(Landlord.class))).thenReturn(landlord);

        ApiResponse result = testService.updateLandlord(landlordId, requestDTO);
        Assert.assertEquals(requestDTO.getFirstName(), landlord.getFirstName());
        Assert.assertEquals(requestDTO.getLastName(), landlord.getLastName());
        Assert.assertEquals(requestDTO.getEmail(), landlord.getEmail());
        Assert.assertEquals("Landlord Details Updated", result.getMessage());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void updateLandlord_Failure() {
        when(testRepository.findByUuid(landlordId)).thenReturn(Optional.empty());
        AddUserDetails requestDTO = AddUserDetails.builder()
                .email("test123@gmail.com").firstName("test").lastName("user").build();
        ApiResponse result = testService.updateLandlord(landlordId, requestDTO);
        Assert.assertEquals("Landlord Not Found", result.getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void deleteLandlord() {
        ApiResponse result = testService.deleteLandlord(landlordId);
        Assert.assertEquals("Landlord Deleted", result.getMessage());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }


}
