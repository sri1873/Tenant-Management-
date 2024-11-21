package com.tenant.management.rental;

import com.tenant.management.rental.entities.PropertyVisit;
import com.tenant.management.rental.repositories.PropertyVisitRepository;
import com.tenant.management.rental.requestDtos.PropertyVisitActionRequest;
import com.tenant.management.rental.services.PropertyVisitService;
import com.tenant.management.user.entities.Landlord;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
    void cancelVisitPendingCase() {
        PropertyVisit testRes = assetUser;
        testRes.setStatus(AppConstants.PropertyVisitStatus.PENDING);
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(testRes));
        ApiResponse result = testService.cancelPropertyVisits(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.PropertyVisitStatus.CANCELLED, testRes.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void cancelVisitApprovedCase() {
        PropertyVisit testRes = assetUser;
        testRes.setStatus(AppConstants.PropertyVisitStatus.APPROVED);
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(testRes));
        ApiResponse result = testService.cancelPropertyVisits(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.PropertyVisitStatus.CANCELLED, testRes.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void cancelVisitRejectedCase() {
        PropertyVisit testRes = assetUser;
        testRes.setStatus(AppConstants.PropertyVisitStatus.REJECTED);
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(testRes));
        ApiResponse result = testService.cancelPropertyVisits(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());

    }

    @Test
    void cancelVisitNoApplicationCase() {
        when(testRepository.findByUuid(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.cancelPropertyVisits(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void approveVisitPendingCase() {
        PropertyVisit sampleReq = assetUser;
        sampleReq.setStatus(AppConstants.PropertyVisitStatus.PENDING);
        Landlord testlandlord = new Landlord();
        assetUser.setLandlord(testlandlord);
        PropertyVisitActionRequest testReq = PropertyVisitActionRequest.builder().propertyVisitId(any()).landlordId(testlandlord.getUserId()).build();
        when(testRepository.findByUuid(testReq.getPropertyVisitId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.approvePropertyVisits(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.PropertyVisitStatus.APPROVED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void approveVisitFailureCase() {
        PropertyVisit sampleReq = assetUser;
        sampleReq.setLandlord(new Landlord());
        sampleReq.setStatus(AppConstants.PropertyVisitStatus.PENDING);
        PropertyVisitActionRequest testReq = PropertyVisitActionRequest.builder().propertyVisitId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getPropertyVisitId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.approvePropertyVisits(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotEquals(AppConstants.PropertyVisitStatus.APPROVED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void approveVisitNotFoundCase() {
        PropertyVisitActionRequest testReq = PropertyVisitActionRequest.builder().propertyVisitId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getPropertyVisitId())).thenReturn(Optional.empty());
        ApiResponse result = testService.approvePropertyVisits(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void rejectVisitPendingCase() {
        PropertyVisit sampleReq = assetUser;
        sampleReq.setStatus(AppConstants.PropertyVisitStatus.PENDING);
        Landlord testlandlord = new Landlord();
        assetUser.setLandlord(testlandlord);
        PropertyVisitActionRequest testReq = PropertyVisitActionRequest.builder().propertyVisitId(any()).landlordId(testlandlord.getUserId()).build();
        when(testRepository.findByUuid(testReq.getPropertyVisitId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.rejectPropertyVisits(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.PropertyVisitStatus.REJECTED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void rejectVisitFailureCase() {
        PropertyVisit sampleReq = assetUser;
        sampleReq.setLandlord(new Landlord());
        sampleReq.setStatus(AppConstants.PropertyVisitStatus.PENDING);
        PropertyVisitActionRequest testReq = PropertyVisitActionRequest.builder().propertyVisitId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getPropertyVisitId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.rejectPropertyVisits(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotEquals(AppConstants.PropertyVisitStatus.REJECTED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void rejectVisitNotFoundCase() {
        PropertyVisitActionRequest testReq = PropertyVisitActionRequest.builder().propertyVisitId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getPropertyVisitId())).thenReturn(Optional.empty());
        ApiResponse result = testService.rejectPropertyVisits(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
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
