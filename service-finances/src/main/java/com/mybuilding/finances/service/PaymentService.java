package com.mybuilding.finances.service;

import com.mybuilding.finances.dto.PaymentRequestDTO;
import com.mybuilding.finances.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO recordPayment(PaymentRequestDTO dto);
    List<PaymentResponseDTO> getAll();
    PaymentResponseDTO getById(Long id);
    List<PaymentResponseDTO> getByChargeId(Long chargeId);
}
