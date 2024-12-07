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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        ArgumentCaptor<Property> propertyCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyRepository).save(propertyCaptor.capture());
        Property capturedProperty = propertyCaptor.getValue();

        assertNotNull(result);
        assertEquals("Property added successfully", result.getMessage());
        assertEquals(addPropertyRequest.getAddress(), capturedProperty.getAddress());
        assertEquals(addPropertyRequest.getPrice(), capturedProperty.getPrice());
    }

    @Test
    void addPropertyTest_InvalidRequest() {
        AddPropertyRequest invalidRequest = new AddPropertyRequest();
        invalidRequest.setPrice(-100.00); // Invalid price
        invalidRequest.setAddress(""); // Empty address

        assertThrows(IllegalArgumentException.class, () -> propertyService.addProperty(invalidRequest));
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
    void getPropertyById_NotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> propertyService.getPropertyById(propertyId));
        assertEquals("Property not found with ID: " + propertyId, exception.getMessage());
    }

    @Test
    void updatePropertyTest() {
        UpdatePropertyRequest updateRequest = new UpdatePropertyRequest();
        updateRequest.setPrice(2600.00);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(mockProperty);

        ApiResponse result = propertyService.updateProperty(propertyId, updateRequest);

        ArgumentCaptor<Property> propertyCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyRepository).save(propertyCaptor.capture());
        Property updatedProperty = propertyCaptor.getValue();

        assertNotNull(result);
        assertEquals("Property updated successfully", result.getMessage());
        assertEquals(updateRequest.getPrice(), updatedProperty.getPrice());
    }

    @Test
    void updatePropertyTest_NotFound() {
        UpdatePropertyRequest updateRequest = new UpdatePropertyRequest();
        updateRequest.setPrice(3000.00);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> propertyService.updateProperty(propertyId, updateRequest));
    }

    @Test
    void deletePropertyTest() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));

        ApiResponse result = propertyService.deleteProperty(propertyId);

        verify(propertyRepository).deleteById(propertyId);

        assertNotNull(result);
        assertEquals("Property deleted successfully", result.getMessage());
    }

    @Test
    void deletePropertyTest_NotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> propertyService.deleteProperty(propertyId));
    }

    @Test
    void deletePropertyTest_AlreadyDeleted() {
        mockProperty.setAvailable(false);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));

        Exception exception = assertThrows(IllegalStateException.class, () -> propertyService.deleteProperty(propertyId));
        assertEquals("Cannot delete an already deleted property", exception.getMessage());
    }

    @Test
    void addPropertyTest_LargeInput() {
        AddPropertyRequest addPropertyRequest = new AddPropertyRequest();
        addPropertyRequest.setAddress("Very long address ".repeat(100));
        addPropertyRequest.setPrice(999999.99);
        addPropertyRequest.setType("Luxury Apartment");
        addPropertyRequest.setBedrooms(10);
        addPropertyRequest.setBathrooms(5);
        addPropertyRequest.setAvailable(true);
        addPropertyRequest.setLandlordId(UUID.randomUUID());

        when(propertyRepository.save(any(Property.class))).thenReturn(mockProperty);

        ApiResponse result = propertyService.addProperty(addPropertyRequest);

        assertNotNull(result);
        assertEquals("Property added successfully", result.getMessage());
    }
}
