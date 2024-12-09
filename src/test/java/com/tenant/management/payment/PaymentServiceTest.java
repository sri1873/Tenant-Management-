package com.tenant.management.payment;

import com.tenant.management.paymentGateway.entities.Payment;
import com.tenant.management.paymentGateway.repositories.PaymentRepository;
import com.tenant.management.paymentGateway.requestDtos.PaymentRequest;
import com.tenant.management.paymentGateway.requestDtos.PaymentResponse;
import com.tenant.management.paymentGateway.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private UUID paymentId;
    private Payment mockPayment;

    @BeforeEach
    void setUp() {
        paymentId = UUID.randomUUID();
        mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setAmount(1000.00);
        mockPayment.setStatus("PENDING");
        mockPayment.setPaymentDate(new Date());
        mockPayment.setPaymentMethod("Credit Card");
        mockPayment.setTransactionId(UUID.randomUUID().toString());
    }

    @Test
    void verifyPaymentTestForNotFound() {
        UUID uniquePaymentId = UUID.randomUUID();
        when(paymentRepository.findById(uniquePaymentId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> paymentService.verifyPayment(uniquePaymentId));
        assertEquals("Payment not found", exception.getMessage());
    }

    @Test
    void cancelPaymentTest() {
        mockPayment.setStatus("PENDING");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        PaymentResponse result = paymentService.cancelPayment(paymentId);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();

        assertNotNull(result);
        assertEquals("CANCELLED", capturedPayment.getStatus());
    }

    @Test
    void cancelPaymentTestForNotPending() {
        mockPayment.setStatus("SUCCESS");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> paymentService.cancelPayment(paymentId));
        assertEquals("Only PENDING payments can be cancelled", exception.getMessage());
    }

    @Test
    void markPaymentAsSuccessTest() {
        mockPayment.setStatus("PENDING");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(mockPayment);

        PaymentResponse result = paymentService.markPaymentAsSuccess(paymentId);

        ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
        verify(paymentRepository).save(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();

        assertNotNull(result);
        assertEquals("SUCCESS", capturedPayment.getStatus());
    }

    @Test
    void markPaymentAsSuccessTestForAlreadySuccess() {
        mockPayment.setStatus("SUCCESS");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> paymentService.markPaymentAsSuccess(paymentId));
        assertEquals("Payment is already SUCCESS or CANCELLED", exception.getMessage());
    }

    @Test
    void markPaymentAsSuccessTestForAlreadyCancelled() {
        mockPayment.setStatus("CANCELLED");
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> paymentService.markPaymentAsSuccess(paymentId));
        assertEquals("Payment is already SUCCESS or CANCELLED", exception.getMessage());
    }
}
