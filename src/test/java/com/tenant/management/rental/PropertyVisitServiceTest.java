package com.tenant.management.rental;

import com.tenant.management.rental.entities.PropertyVisit;
import com.tenant.management.rental.repositories.PropertyVisitRepository;
import com.tenant.management.rental.services.PropertyVisitService;
import com.tenant.management.utils.ApiResponse;
import com.tenant.management.utils.AppConstants;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertyVisitServiceTest {


    UUID id = UUID.randomUUID();
    PropertyVisit assetUser = PropertyVisit.builder().propertyVisitId(id).status(AppConstants.PropertyVisitStatus.APPROVED).build();
    @Mock
    private PropertyVisitRepository testRepository;
    @InjectMocks
    private PropertyVisitService testService;

    @Test
    void getPropertyVisitApplicationCase() {
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(assetUser));
        ApiResponse result = testService.getPropertyVisit(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getPropertyVisitNoApplicationCase() {
        when(testRepository.findByUuid(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getPropertyVisit(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void getPropertyVisitByLandlordIdApplicationCase() {
        when(testRepository.findAllByLandlordId(id)).thenReturn(Optional.of(List.of(assetUser)));
        ApiResponse result = testService.getPropertyVisitsByLandlord(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getPropertyVisitByLandlordIdNoApplicationCase() {
        when(testRepository.findAllByLandlordId(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getPropertyVisitsByLandlord(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void getPropertyVisitByTenantIdApplicationCase() {
        when(testRepository.findAllByTenantId(id)).thenReturn(Optional.of(List.of(assetUser)));
        ApiResponse result = testService.getPropertyVisitsByTenant(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getPropertyVisitByTenantIdNoApplicationCase() {
        when(testRepository.findAllByTenantId(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getPropertyVisitsByTenant(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void getPropertyVisitByPropertyIdApplicationCase() {
        when(testRepository.findAllByPropertyId(id)).thenReturn(Optional.of(List.of(assetUser)));
        ApiResponse result = testService.getPropertyVisitByProperty(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getPropertyVisitByPropertyIdNoApplicationCase() {
        when(testRepository.findAllByPropertyId(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getPropertyVisitByProperty(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void updatePropertyVisitStatusSuccessTest() {
        PropertyVisit propertyVisit = PropertyVisit.builder().propertyVisitId(UUID.randomUUID()).status(AppConstants.PropertyVisitStatus.APPROVED).visitDate(LocalDate.of(2025, 11, 23)).build();
        ApiResponse result = testService.updatePropertyVisitStatus(propertyVisit);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void updatePropertyVisitStatusFailureTest() {
        PropertyVisit propertyVisit = PropertyVisit.builder().propertyVisitId(UUID.randomUUID()).status(AppConstants.PropertyVisitStatus.APPROVED).visitDate(LocalDate.now()).build();
        ApiResponse result = testService.updatePropertyVisitStatus(propertyVisit);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.FORBIDDEN, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @TestConfiguration
    static class PropertyVisitServiceImplTestContextConfiguration {

        @Bean
        public PropertyVisitService PropertyVisitService() {
            return new PropertyVisitService();
        }
    }
}
