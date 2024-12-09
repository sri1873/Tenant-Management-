//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user;

import com.tenant.management.property.entities.Property;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.user.entities.Admin;
import com.tenant.management.user.repositories.AdminRepository;
import com.tenant.management.user.repositories.RtbRepository;
import com.tenant.management.user.requestdtos.RtbRequestDto;
import com.tenant.management.user.services.AdminServices.RtbService;
import com.tenant.management.utils.ApiResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RtbServiceTest {
    UUID adminId = UUID.randomUUID();
    Admin appUser = Admin.builder().adminId(adminId).build();
    UUID propertyId = UUID.randomUUID();
    private Property mockProperty = new Property();
    private final RtbRequestDto requestDto = RtbRequestDto.builder().
            adminId(adminId).propertyId(propertyId).rtbVerified(Boolean.TRUE).build();
    @Mock
    private AdminRepository adminTestRepository;
    @Mock
    private PropertyRepository propertyTestRepository;
    @Mock
    private RtbRepository rtbTestRepository;
    @InjectMocks
    private RtbService rtbService;

    @BeforeEach
    void setUp() {
        mockProperty = new Property();
        mockProperty.setId(propertyId);
        mockProperty.setAddress("123 Main St");
        mockProperty.setPrice(2500.00);
        mockProperty.setType("Apartment");
        mockProperty.setBedrooms(2);
        mockProperty.setBathrooms(1);
        mockProperty.setAvailable(true);
        mockProperty.setLandlordId(UUID.randomUUID());
    }

    @Test
    void updateRtbStatus_Success() {
        when(adminTestRepository.findByUuid(adminId)).thenReturn(Optional.of(appUser));
        when(propertyTestRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        ApiResponse result = rtbService.updateRtbStatus(requestDto);
        Assert.assertEquals("RTB status updated successfully", result.getMessage());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void updateRtbStatus_NoAdmin() {
        when(adminTestRepository.findByUuid(adminId)).thenReturn(Optional.empty());
        ApiResponse result = rtbService.updateRtbStatus(requestDto);
        Assert.assertEquals("Admin not found", result.getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void updateRtbStatus_NoProperty() {
        when(adminTestRepository.findByUuid(adminId)).thenReturn(Optional.of(appUser));
        when(propertyTestRepository.findById(propertyId)).thenReturn(Optional.empty());
        ApiResponse result = rtbService.updateRtbStatus(requestDto);
        Assert.assertEquals("Property not found", result.getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

}
