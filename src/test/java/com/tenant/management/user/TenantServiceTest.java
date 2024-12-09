//Author: Mamtha Patalay
//ID:24182559

package com.tenant.management.user;

import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.repositories.TenantRepository;
import com.tenant.management.user.requestdtos.AddUserDetails;
import com.tenant.management.user.services.TenantServices.TenantService;
import com.tenant.management.utils.ApiResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {

    private final UUID tenantId = UUID.randomUUID();
    Tenant tenant = Tenant.builder().userId(tenantId).verified(Boolean.TRUE).build();

    @Mock
    private TenantRepository testRepository;

    @InjectMocks
    private TenantService testService;

    @Test
    void getTenantById_Success() {
        when(testRepository.findByUuid(tenantId)).thenReturn(Optional.of(tenant));
        ApiResponse result = testService.getTenantById(tenantId);
        Assert.assertEquals("", result.getMessage());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void getTenantById_Failure() {
        when(testRepository.findByUuid(tenantId)).thenReturn(Optional.empty());
        ApiResponse result = testService.getTenantById(tenantId);
        Assert.assertEquals("Tenant Not Found", result.getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void createTenant_Success() {
        AddUserDetails requestDTO = AddUserDetails.builder()
                .email("test123@gmail.com").firstName("test").lastName("user").build();
        when(testRepository.save(any(Tenant.class))).thenReturn(tenant);
        ApiResponse result = testService.createTenant(requestDTO);
        Assert.assertEquals("Tenant Created", result.getMessage());
        Assert.assertEquals(HttpStatus.CREATED, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void updateTenant_Success() {
        when(testRepository.findByUuid(tenantId)).thenReturn(Optional.of(tenant));
        AddUserDetails requestDTO = AddUserDetails.builder()
                .email("test123@gmail.com").firstName("test").lastName("user").build();
        when(testRepository.save(any(Tenant.class))).thenReturn(tenant);

        ApiResponse result = testService.updateTenant(tenantId, requestDTO);
        Assert.assertEquals(requestDTO.getFirstName(), tenant.getFirstName());
        Assert.assertEquals(requestDTO.getLastName(), tenant.getLastName());
        Assert.assertEquals(requestDTO.getEmail(), tenant.getEmail());
        Assert.assertEquals("Tenant Details Updated", result.getMessage());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }

    @Test
    void updateTenant_Failure() {
        when(testRepository.findByUuid(tenantId)).thenReturn(Optional.empty());
        AddUserDetails requestDTO = AddUserDetails.builder()
                .email("test123@gmail.com").firstName("test").lastName("user").build();
        ApiResponse result = testService.updateTenant(tenantId, requestDTO);
        Assert.assertEquals("Tenant Not Found", result.getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
        Assert.assertEquals(Boolean.FALSE, result.getSuccess());
    }

    @Test
    void deleteTenant() {
        ApiResponse result = testService.deleteTenant(tenantId);
        Assert.assertEquals("Tenant Deleted", result.getMessage());
        Assert.assertEquals(HttpStatus.OK, result.getStatus());
        Assert.assertEquals(Boolean.TRUE, result.getSuccess());
    }


}
