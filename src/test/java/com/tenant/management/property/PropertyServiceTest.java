package com.tenant.management.property;

import com.tenant.management.property.entities.Property;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.property.requestDtos.AddPropertyRequest;
import com.tenant.management.property.requestDtos.PropertyResponse;
import com.tenant.management.property.requestDtos.UpdatePropertyRequest;
import com.tenant.management.property.services.PropertyServiceImpl;
import com.tenant.management.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private UUID propertyId;
    private Property mockProperty;

    @BeforeEach
    void setUp() {
        propertyId = UUID.randomUUID();
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
    void addPropertyTest() {
        AddPropertyRequest addPropertyRequest = new AddPropertyRequest();
        addPropertyRequest.setAddress("123 Main St");
        addPropertyRequest.setPrice(2500.00);
        addPropertyRequest.setType("Apartment");
        addPropertyRequest.setBedrooms(2);
        addPropertyRequest.setBathrooms(1);
        addPropertyRequest.setAvailable(true);
        addPropertyRequest.setLandlordId(UUID.randomUUID());

        when(propertyRepository.save(any(Property.class))).thenReturn(mockProperty);

        ApiResponse result = propertyService.addProperty(addPropertyRequest);
        assertNotNull(result);
        assertEquals("Property added successfully", result.getMessage());
    }

    @Test
    void getPropertyByIdTest() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        PropertyResponse propertyResponse = propertyService.getPropertyById(propertyId);
        assertNotNull(propertyResponse);
        assertEquals(mockProperty.getAddress(), propertyResponse.getAddress());
        assertEquals(mockProperty.getPrice(), propertyResponse.getPrice());
    }

    @Test
    void updatePropertyTest() {
        UpdatePropertyRequest updateRequest = new UpdatePropertyRequest();
        updateRequest.setPrice(2600.00);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(mockProperty);
        ApiResponse result = propertyService.updateProperty(propertyId, updateRequest);
        assertNotNull(result);
        assertEquals("Property updated successfully", result.getMessage());
    }

    @Test
    void deletePropertyTest() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        ApiResponse result = propertyService.deleteProperty(propertyId);
        assertNotNull(result);
        assertEquals("Property deleted successfully", result.getMessage());
    }

    @Test
    void getPropertyById_NotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> propertyService.getPropertyById(propertyId));
    }
}
