package com.tenant.management.property;

import com.tenant.management.property.entities.Property;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.property.requestdtos.AddPropertyRequest;
import com.tenant.management.property.requestdtos.PropertyResponse;
import com.tenant.management.property.requestdtos.UpdatePropertyRequest;
import com.tenant.management.property.services.PropertyServiceImpl;
import com.tenant.management.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    void addPropertyTest_InvalidPrice() {
        AddPropertyRequest invalidRequest = new AddPropertyRequest();
        invalidRequest.setPrice(-100.00); // Invalid price
        invalidRequest.setAddress("Valid Address"); // Valid address

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> propertyService.addProperty(invalidRequest));
        assertEquals("Price cannot be negative.", exception.getMessage());
    }

    @Test
    void addPropertyTest_InvalidAddress() {
        AddPropertyRequest invalidRequest = new AddPropertyRequest();
        invalidRequest.setPrice(100.00); // Valid price
        invalidRequest.setAddress(""); // Invalid address (empty)

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> propertyService.addProperty(invalidRequest));
        assertEquals("Address cannot be empty.", exception.getMessage());
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
        UUID uniquePropertyId = UUID.randomUUID();
        when(propertyRepository.findById(uniquePropertyId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> propertyService.getPropertyById(uniquePropertyId));
        assertEquals("Property not found with ID:" + uniquePropertyId, exception.getMessage());
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

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> propertyService.updateProperty(propertyId, updateRequest));
        assertEquals("Property not found with ID:" + propertyId, exception.getMessage());
    }

    @Test
    void deletePropertyTest() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        ApiResponse result = propertyService.deleteProperty(propertyId);
        verify(propertyRepository).delete(any(Property.class));
        assertNotNull(result);
        assertEquals("Property deleted successfully", result.getMessage());
    }

    @Test
    void deletePropertyTest_NotFound() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());
        ApiResponse result = propertyService.deleteProperty(propertyId);
        assertFalse(result.getSuccess());
        assertEquals("Property not found", result.getMessage());
    }

    @Test
    void deletePropertyTest_AlreadyDeleted() {
        mockProperty.setAvailable(false);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> propertyService.deleteProperty(propertyId));
        assertEquals("Cannot delete an already deleted property", exception.getMessage());
    }

    // New Test Cases
    @Test
    void searchPropertiesTest_NoMatch() {
        when(propertyRepository.searchProperties(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(List.of());  // No properties match the search criteria

        List<PropertyResponse> result = propertyService.searchProperties("Nonexistent Location", 1000.00, 2000.00, "Apartment", 2, 1, true);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result should be empty when no properties match the criteria.");
    }

    @Test
    void searchPropertiesTest_MultipleFilters() {
        when(propertyRepository.searchProperties("Main St", 1000.00, 3000.00, "Apartment", 2, 1, true))
                .thenReturn(List.of(mockProperty));  // Mocking the repository to return the mockProperty

        List<PropertyResponse> result = propertyService.searchProperties("Main St", 1000.00, 3000.00, "Apartment", 2, 1, true);

        assertNotNull(result);
        assertEquals(1, result.size(), "The search should return exactly one property.");
        assertEquals(mockProperty.getAddress(), result.get(0).getAddress(), "The property returned should match the mock.");
    }

    @Test
    void addPropertyTest_SaveFailure() {
        AddPropertyRequest addPropertyRequest = new AddPropertyRequest();
        addPropertyRequest.setAddress("123 Main St");
        addPropertyRequest.setPrice(2500.00);
        addPropertyRequest.setType("Apartment");
        addPropertyRequest.setBedrooms(2);
        addPropertyRequest.setBathrooms(1);
        addPropertyRequest.setAvailable(true);
        addPropertyRequest.setLandlordId(UUID.randomUUID());

        when(propertyRepository.save(any(Property.class)))
                .thenThrow(new RuntimeException("Database error"));  // Simulating a save failure

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> propertyService.addProperty(addPropertyRequest));

        assertEquals("Database error", exception.getMessage(), "The service should throw a runtime exception if saving fails.");
    }

    @Test
    void deletePropertyTest_DeleteFailure() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        doThrow(new RuntimeException("Database error")).when(propertyRepository).delete(any(Property.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> propertyService.deleteProperty(propertyId));

        assertEquals("Database error", exception.getMessage(), "The service should throw a runtime exception if deletion fails.");
    }

    @Test
    void updatePropertyTest_NullValues() {
        UpdatePropertyRequest updateRequest = new UpdatePropertyRequest();
        updateRequest.setPrice(null);  // Setting only price as null

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(mockProperty);

        ApiResponse result = propertyService.updateProperty(propertyId, updateRequest);

        ArgumentCaptor<Property> propertyCaptor = ArgumentCaptor.forClass(Property.class);
        verify(propertyRepository).save(propertyCaptor.capture());
        Property updatedProperty = propertyCaptor.getValue();

        assertNotNull(result);
        assertEquals("Property updated successfully", result.getMessage());
        assertEquals(mockProperty.getPrice(), updatedProperty.getPrice(), "The price should remain the same as it wasn't updated.");
    }

    @Test
    void addPropertyTest_InvalidLandlordId() {
        AddPropertyRequest invalidRequest = new AddPropertyRequest();
        invalidRequest.setPrice(1500.00);
        invalidRequest.setAddress("123 Main St");
        invalidRequest.setType("Apartment");
        invalidRequest.setBedrooms(2);
        invalidRequest.setBathrooms(1);
        invalidRequest.setAvailable(true);
        invalidRequest.setLandlordId(null);  // Invalid landlordId (null)

        // Now we expect an IllegalArgumentException to be thrown due to the null landlordId
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> propertyService.addProperty(invalidRequest));
        assertEquals("Landlord ID cannot be null or empty.", exception.getMessage(), "The service should throw an exception for invalid landlordId.");
    }


    @Test
    void deletePropertyTest_AlreadyDeletedProperty() {
        mockProperty.setAvailable(false);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> propertyService.deleteProperty(propertyId));

        assertEquals("Cannot delete an already deleted property", exception.getMessage(), "The service should throw an exception for already deleted properties.");
    }

    @Test
    void getPropertyByIdTest_AvailableProperty() {
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));

        PropertyResponse result = propertyService.getPropertyById(propertyId);

        assertNotNull(result);
        assertTrue(result.getAvailable(), "The property should be available.");
    }

    @Test
    void getPropertyByIdTest_UnavailableProperty() {
        mockProperty.setAvailable(false);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));

        PropertyResponse result = propertyService.getPropertyById(propertyId);

        assertNotNull(result);
        assertFalse(result.getAvailable(), "The property should be unavailable.");
    }
}
