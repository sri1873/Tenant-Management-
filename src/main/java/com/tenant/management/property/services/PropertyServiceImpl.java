package com.tenant.management.property.services;

import com.tenant.management.property.entities.Property;
import com.tenant.management.property.factories.PropertyFactory;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.property.repositories.PropertyService;
import com.tenant.management.property.requestDtos.AddPropertyRequest;
import com.tenant.management.property.requestDtos.UpdatePropertyRequest;
import com.tenant.management.property.requestDtos.PropertyResponse;
import com.tenant.management.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public ApiResponse addProperty(AddPropertyRequest addPropertyRequest) {
        // Used the factory method ("PropertyFactory")
        Property property = PropertyFactory.createProperty(
                addPropertyRequest.getType(),
                addPropertyRequest.getAddress(),
                addPropertyRequest.getPrice(),
                addPropertyRequest.getBedrooms(),
                addPropertyRequest.getBathrooms(),
                addPropertyRequest.getAvailable(),
                addPropertyRequest.getLandlordId()
        );
        propertyRepository.save(property);
        return ApiResponse.builder().success(true).message("Property added successfully").build();
    }

    @Override
    public ApiResponse updateProperty(UUID propertyId, UpdatePropertyRequest updateRequest) {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isEmpty()) {
            return ApiResponse.builder().success(false).message("Property not found").build();
        }
        Property property = optionalProperty.get();

        if (updateRequest.getAddress() != null) property.setAddress(updateRequest.getAddress());
        if (updateRequest.getPrice() != null) property.setPrice(updateRequest.getPrice());
        if (updateRequest.getType() != null) property.setType(updateRequest.getType());
        if (updateRequest.getBedrooms() != null) property.setBedrooms(updateRequest.getBedrooms());
        if (updateRequest.getBathrooms() != null) property.setBathrooms(updateRequest.getBathrooms());
        if (updateRequest.getAvailable() != null) property.setAvailable(updateRequest.getAvailable());

        propertyRepository.save(property);
        return ApiResponse.builder().success(true).message("Property updated successfully").build();
    }

    @Override
    public PropertyResponse getPropertyById(UUID propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        return mapToResponse(property);
    }

    @Override
    public ApiResponse deleteProperty(UUID propertyId) {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isEmpty()) {
            return ApiResponse.builder().success(false).message("Property not found").build();
        }
        propertyRepository.delete(optionalProperty.get());
        return ApiResponse.builder().success(true).message("Property deleted successfully").build();
    }

    @Override
    public List<PropertyResponse> searchProperties(String location, Double minPrice, Double maxPrice, String type, Integer bedrooms, Integer bathrooms, Boolean available) {
        List<Property> properties = propertyRepository.searchProperties(location, minPrice, maxPrice, type, bedrooms, bathrooms, available);
        return properties.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Helper method to map Property entity to PropertyResponse DTO
    private PropertyResponse mapToResponse(Property property) {
        PropertyResponse response = new PropertyResponse();
        response.setPropertyId(property.getId());
        response.setAddress(property.getAddress());
        response.setPrice(property.getPrice());
        response.setType(property.getType());
        response.setBedrooms(property.getBedrooms());
        response.setBathrooms(property.getBathrooms());
        response.setAvailable(property.getAvailable());
        response.setLandlordId(property.getLandlordId());
        return response;
    }

    @Override
    public List<PropertyResponse> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
