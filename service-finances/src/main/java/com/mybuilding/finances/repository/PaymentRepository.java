package com.mybuilding.finances.repository;

import com.mybuilding.finances.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByChargeId(Long chargeId);
}
