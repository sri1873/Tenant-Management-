package com.tenant.management.rental;

import com.tenant.management.rental.entities.LeaseApplication;
import com.tenant.management.rental.repositories.LeaseRepository;
import com.tenant.management.rental.services.LeaseService;
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


import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaseServiceTest {


    @TestConfiguration
    static class LeaseServiceImplTestContextConfiguration {

        @Bean
        public LeaseService LeaseService() {
            return new LeaseService();
        }
    }
    @Mock
    private LeaseRepository testRepository;

    @InjectMocks
    private LeaseService testService;

    UUID id = UUID.randomUUID();
    LeaseApplication assetUser = LeaseApplication.builder().applicationId(id).status(AppConstants.ApplicationStatus.APPROVED).build();
    @Test
    void getLeaseApplication() {
        when(testRepository.findByUuid(id)).thenReturn(Optional.of(assetUser));
        ApiResponse result = testService.getLeaseApplication(id);
        Assert.assertEquals(result.getClass(), ApiResponse.class);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
    }
}
