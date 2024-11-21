//package com.tenant.management.property;
//
//import com.tenant.management.property.entities.Property;
//import com.tenant.management.property.repositories.PropertyRepository;
//import com.tenant.management.property.requestDtos.AddPropertyRequest;
//import com.tenant.management.property.requestDtos.PropertyResponse;
//import com.tenant.management.property.requestDtos.UpdatePropertyRequest;
//import com.tenant.management.property.services.PropertyServiceImpl;
//import com.tenant.management.utils.ApiResponse;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class PropertyServiceTest {
//
//    @Mock
//    private PropertyRepository propertyRepository;
//
//    @InjectMocks
//    private PropertyServiceImpl propertyService;
//
//    UUID propertyId = UUID.randomUUID();
//    Property mockProperty = Property.builder()
//            .id(propertyId)
//            .address("123 Main St")
//            .price(250000.00)
//            .type("apartment")
//            .bedrooms(2)
//            .bathrooms(1)
//            .available(true)
//            .landlordId(UUID.randomUUID())
//            .build();
//
//    @Test
//    void addPropertyTest() {
//        // Arrange
//        AddPropertyRequest addPropertyRequest = new AddPropertyRequest();
//        addPropertyRequest.setAddress("123 Main St");
//        addPropertyRequest.setPrice(250000.00);
//        addPropertyRequest.setType("apartment");
//        addPropertyRequest.setBedrooms(2);
//        addPropertyRequest.setBathrooms(1);
//        addPropertyRequest.setAvailable(true);
//        addPropertyRequest.setLandlordId(UUID.randomUUID());
//
//        when(propertyRepository.save(mockProperty)).thenReturn(mockProperty);
//
//        ApiResponse result = propertyService.addProperty(addPropertyRequest);
//
//        assertNotNull(result);
//        assertEquals(HttpStatus.OK, result.getStatus());
//        assertEquals("Property added successfully", result.getMessage());
//    }
//
//    @Test
//    void getPropertyByIdTest() {
//        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
//
//        PropertyResponse propertyResponse = propertyService.getPropertyById(propertyId);
//
//        assertNotNull(propertyResponse);
//        assertEquals(mockProperty.getAddress(), propertyResponse.getAddress());
//        assertEquals(mockProperty.getPrice(), propertyResponse.getPrice());
//    }
//
//    @Test
//    void updatePropertyTest() {
//        UpdatePropertyRequest updateRequest = new UpdatePropertyRequest();
//        updateRequest.setPrice(260000.00);
//        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
//        when(propertyRepository.save(mockProperty)).thenReturn(mockProperty);
//
//        ApiResponse result = propertyService.updateProperty(propertyId, updateRequest);
//
//        assertNotNull(result);
//        assertEquals(HttpStatus.OK, result.getStatus());
//        assertEquals("Property updated successfully", result.getMessage());
//    }
//
//    @Test
//    void deletePropertyTest() {
//        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(mockProperty));
//
//        ApiResponse result = propertyService.deleteProperty(propertyId);
//
//        assertNotNull(result);
//        assertEquals(HttpStatus.OK, result.getStatus());
//        assertEquals("Property deleted successfully", result.getMessage());
//    }
//}
