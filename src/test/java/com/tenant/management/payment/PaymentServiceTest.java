package com.tenant.management.payment;

import com.tenant.management.paymentGateway.entities.Payment;
import com.tenant.management.paymentGateway.repositories.PaymentRepository;
import com.tenant.management.property.repositories.PropertyRepository;
import com.tenant.management.paymentGateway.requestDtos.PaymentRequest;
import com.tenant.management.paymentGateway.requestDtos.PaymentResponse;
import com.tenant.management.paymentGateway.services.PaymentService;
import com.tenant.management.property.entities.Property;
import com.tenant.management.subscription.entities.SubscriptionPlan;
import com.tenant.management.subscription.repositories.SubscriptionRepository;
import com.tenant.management.user.entities.Tenant;
import com.tenant.management.user.repositories.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private PaymentService paymentService;

    private UUID userId;
    private UUID propertyId;
    private Payment mockPayment;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        propertyId = UUID.randomUUID();
        mockPayment = new Payment();
        mockPayment.setId(UUID.randomUUID());
        mockPayment.setAmount(1000.00);
        mockPayment.setStatus("PENDING");
        mockPayment.setPaymentDate(new Date());
        mockPayment.setPaymentMethod("Credit Card");
        mockPayment.setTransactionId(UUID.randomUUID().toString());
    }

    @Test
    void initiatePaymentTestForSuccess() {
        PaymentRequest request = new PaymentRequest();
        request.setUserId(userId);
        request.setPropertyId(propertyId);

        Tenant tenant = new Tenant();
        tenant.setUserId(userId);

        Property property = new Property();
        property.setPrice(1000.00);
        property.setType("commercial");

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan("PREMIUM", 0.25);
        subscriptionPlan.setName("PLUS");

        when(tenantRepository.findByUuid(userId)).thenReturn(Optional.of(tenant));
        when(propertyRepository.getById(propertyId)).thenReturn(property);
        when(subscriptionRepository.findByUserId(userId)).thenReturn(List.of(subscriptionPlan));

        PaymentResponse result = paymentService.initiatePayment(request);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertNotNull(result.getTransactionId());
        assertEquals(800.0, result.getAmount(), 0.01); // Price after subscription discount (20%) and VAT (23%)
    }

    @Test
    void initiatePaymentTestForTenantNotFound() {
        PaymentRequest request = new PaymentRequest();
        request.setUserId(userId);
        request.setPropertyId(propertyId);

        when(tenantRepository.findByUuid(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentService.initiatePayment(request));
        assertEquals("Tenant not found", exception.getMessage());
    }

    @Test
    void initiatePaymentTestForNoSubscriptionFound() {
        PaymentRequest request = new PaymentRequest();
        request.setUserId(userId);
        request.setPropertyId(propertyId);

        Tenant tenant = new Tenant();
        tenant.setUserId(userId);

        Property property = new Property();
        property.setPrice(1000.00);

        when(tenantRepository.findByUuid(userId)).thenReturn(Optional.of(tenant));
        when(propertyRepository.getById(propertyId)).thenReturn(property);
        when(subscriptionRepository.findByUserId(userId)).thenReturn(List.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentService.initiatePayment(request));
        assertEquals("No subscription found for tenant ID: " + userId, exception.getMessage());
    }

    @Test
    void initiatePaymentTestForSubscriptionDiscountApplied() {
        PaymentRequest request = new PaymentRequest();
        request.setUserId(userId);
        request.setPropertyId(propertyId);

        Tenant tenant = new Tenant();
        tenant.setUserId(userId);

        Property property = new Property();
        property.setPrice(1000.00);
        property.setType("commercial");

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan("PREMIUM", 0.25);
        subscriptionPlan.setName("PREMIUM");

        when(tenantRepository.findByUuid(userId)).thenReturn(Optional.of(tenant));
        when(propertyRepository.getById(propertyId)).thenReturn(property);
        when(subscriptionRepository.findByUserId(userId)).thenReturn(List.of(subscriptionPlan));

        PaymentResponse result = paymentService.initiatePayment(request);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();

        assertNotNull(result);
        assertEquals(750.0, result.getAmount(), 0.01); // Price after subscription discount (25%) and VAT (23%)
    }

    @Test
    void initiatePaymentTestForPropertyVATApplied() {
        PaymentRequest request = new PaymentRequest();
        request.setUserId(userId);
        request.setPropertyId(propertyId);

        Tenant tenant = new Tenant();
        tenant.setUserId(userId);

        Property property = new Property();
        property.setPrice(1000.00);
        property.setType("commercial");

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan("PREMIUM", 0.25);
        subscriptionPlan.setName("NORMAL");

        when(tenantRepository.findByUuid(userId)).thenReturn(Optional.of(tenant));
        when(propertyRepository.getById(propertyId)).thenReturn(property);
        when(subscriptionRepository.findByUserId(userId)).thenReturn(List.of(subscriptionPlan));

        PaymentResponse result = paymentService.initiatePayment(request);

        assertEquals(850.0, result.getAmount(), 0.01); // Price after subscription discount (15%) and VAT (23%)
    }
    @Test
    void cancelPaymentTestForSuccess() {
        UUID paymentId = UUID.randomUUID();

        Payment mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setStatus("PENDING");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        PaymentResponse result = paymentService.cancelPayment(paymentId);

        assertNotNull(result);
        assertEquals("CANCELLED", result.getStatus()); // Assuming canceled status is "CANCELLED"
        verify(paymentRepository).save(mockPayment);
    }

    @Test
    void cancelPaymentTestForPaymentAlreadyProcessed() {
        UUID paymentId = UUID.randomUUID();

        Payment mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setStatus("COMPLETED");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentService.cancelPayment(paymentId));
        assertEquals("Only PENDING payments can be cancelled", exception.getMessage());
    }

    @Test
    void cancelPaymentTestForPaymentNotFound() {
        UUID paymentId = UUID.randomUUID();

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentService.cancelPayment(paymentId));
        assertEquals("Payment not found", exception.getMessage());
    }

    @Test
    void cancelPaymentTestForInvalidStatus() {
        UUID paymentId = UUID.randomUUID();

        Payment mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setStatus("FAILED");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentService.cancelPayment(paymentId));
        assertEquals("Only PENDING payments can be cancelled", exception.getMessage());
    }

}
