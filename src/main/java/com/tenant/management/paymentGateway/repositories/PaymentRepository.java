package com.tenant.management.paymentGateway.repositories;
//Author : Kshitij Ghodekar
//Id : 24149802
import com.tenant.management.paymentGateway.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
