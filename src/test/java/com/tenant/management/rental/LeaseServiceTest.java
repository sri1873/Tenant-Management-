package com.tenant.management.rental;

import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.repositories.LeaseRepository;
import com.tenant.management.rental.requestDtos.LeaseAppActionRequest;
import com.tenant.management.rental.services.LeaseService;
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
class LeaseServiceTest {


    UUID id = UUID.randomUUID();
    LeaseApplication assetUser = LeaseApplication.builder().applicationId(id).status(AppConstants.ApplicationStatus.APPROVED).build();
    @Mock
    private LeaseRepository testRepository;
    @InjectMocks
    private LeaseService testService;

    @Test
    void getLeaseApplicationApplicationCase() {
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(assetUser));
        ApiResponse result = testService.getLeaseApplication(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getLeaseApplicationNoApplicationCase() {
        when(testRepository.findByUuid(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getLeaseApplication(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void getLeaseApplicationByLandlordIdApplicationCase() {
        when(testRepository.findAllByLandlordId(id)).thenReturn(Optional.of(List.of(assetUser)));
        ApiResponse result = testService.getLeaseApplicationsByLandlord(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getLeaseApplicationByLandlordIdNoApplicationCase() {
        when(testRepository.findAllByLandlordId(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getLeaseApplicationsByLandlord(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void getLeaseApplicationByTenantIdApplicationCase() {
        when(testRepository.findAllByTenantId(id)).thenReturn(Optional.of(List.of(assetUser)));
        ApiResponse result = testService.getLeaseApplicationsByTenant(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getLeaseApplicationByTenantIdNoApplicationCase() {
        when(testRepository.findAllByTenantId(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getLeaseApplicationsByTenant(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void getLeaseApplicationByPropertyIdApplicationCase() {
        when(testRepository.findAllByPropertyId(id)).thenReturn(Optional.of(List.of(assetUser)));
        ApiResponse result = testService.getLeaseApplicationByProperty(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void getLeaseApplicationByPropertyIdNoApplicationCase() {
        when(testRepository.findAllByPropertyId(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.getLeaseApplicationByProperty(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNull(result.getData());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void withdrawApplicationPendingCase() {
        LeaseApplication testRes = assetUser;
        testRes.setStatus(AppConstants.ApplicationStatus.PENDING);
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(testRes));
        ApiResponse result = testService.withdrawLeaseApplications(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.ApplicationStatus.WITHDRAWN, testRes.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void withdrawApplicationApprovedCase() {
        LeaseApplication testRes = assetUser;
        testRes.setStatus(AppConstants.ApplicationStatus.APPROVED);
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(testRes));
        ApiResponse result = testService.withdrawLeaseApplications(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.ApplicationStatus.WITHDRAWN, testRes.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void withdrawApplicationRejectedCase() {
        LeaseApplication testRes = assetUser;
        testRes.setStatus(AppConstants.ApplicationStatus.REJECTED);
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(testRes));
        ApiResponse result = testService.withdrawLeaseApplications(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());

    }

    @Test
    void withdrawApplicationNoApplicationCase() {
        when(testRepository.findByUuid(id)).thenReturn(Optional.empty());
        ApiResponse result = testService.withdrawLeaseApplications(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void approveApplicationPendingCase() {
        LeaseApplication sampleReq = assetUser;
        sampleReq.setStatus(AppConstants.ApplicationStatus.PENDING);
        Landlord testlandlord = new Landlord();
        assetUser.setLandlord(testlandlord);
        LeaseAppActionRequest testReq = LeaseAppActionRequest.builder().applicationId(any()).landlordId(testlandlord.getUserId()).build();
        when(testRepository.findByUuid(testReq.getApplicationId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.approveLeaseApplications(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.ApplicationStatus.APPROVED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void approveApplicationFailureCase() {
        LeaseApplication sampleReq = assetUser;
        sampleReq.setLandlord(new Landlord());
        sampleReq.setStatus(AppConstants.ApplicationStatus.PENDING);
        LeaseAppActionRequest testReq = LeaseAppActionRequest.builder().applicationId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getApplicationId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.approveLeaseApplications(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotEquals(AppConstants.ApplicationStatus.APPROVED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void approveApplicationNotFoundCase() {
        LeaseAppActionRequest testReq = LeaseAppActionRequest.builder().applicationId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getApplicationId())).thenReturn(Optional.empty());
        ApiResponse result = testService.approveLeaseApplications(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void rejectApplicationPendingCase() {
        LeaseApplication sampleReq = assetUser;
        sampleReq.setStatus(AppConstants.ApplicationStatus.PENDING);
        Landlord testlandlord = new Landlord();
        assetUser.setLandlord(testlandlord);
        LeaseAppActionRequest testReq = LeaseAppActionRequest.builder().applicationId(any()).landlordId(testlandlord.getUserId()).build();
        when(testRepository.findByUuid(testReq.getApplicationId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.rejectLeaseApplications(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(AppConstants.ApplicationStatus.REJECTED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void rejectApplicationFailureCase() {
        LeaseApplication sampleReq = assetUser;
        sampleReq.setLandlord(new Landlord());
        sampleReq.setStatus(AppConstants.ApplicationStatus.PENDING);
        LeaseAppActionRequest testReq = LeaseAppActionRequest.builder().applicationId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getApplicationId())).thenReturn(Optional.of(sampleReq));
        ApiResponse result = testService.rejectLeaseApplications(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotEquals(AppConstants.ApplicationStatus.REJECTED, assetUser.getStatus());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void rejectApplicationNotFoundCase() {
        LeaseAppActionRequest testReq = LeaseAppActionRequest.builder().applicationId(UUID.randomUUID()).landlordId(UUID.randomUUID()).build();
        when(testRepository.findByUuid(testReq.getApplicationId())).thenReturn(Optional.empty());
        ApiResponse result = testService.rejectLeaseApplications(testReq);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @TestConfiguration
    static class LeaseServiceImplTestContextConfiguration {

        @Bean
        public LeaseService LeaseService() {
            return new LeaseService();
        }
    }
}
