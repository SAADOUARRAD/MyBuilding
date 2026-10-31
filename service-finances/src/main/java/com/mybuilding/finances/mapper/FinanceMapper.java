package com.mybuilding.finances.mapper;

import com.mybuilding.finances.dto.ChargeRequestDTO;
import com.mybuilding.finances.dto.ChargeResponseDTO;
import com.mybuilding.finances.dto.PaymentRequestDTO;
import com.mybuilding.finances.dto.PaymentResponseDTO;
import com.mybuilding.finances.model.Charge;
import com.mybuilding.finances.model.Payment;
import com.mybuilding.finances.model.StatutCharge;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FinanceMapper {

    public Charge toEntity(ChargeRequestDTO dto) {
        Charge charge = new Charge();
        charge.setApartmentId(dto.getApartmentId());
        charge.setPeriode(dto.getPeriode());
        charge.setMontant(dto.getMontant());
        charge.setMontantPaye(BigDecimal.ZERO);
        charge.setStatut(StatutCharge.EMISE);
        return charge;
    }

    public ChargeResponseDTO toResponseDTO(Charge charge) {
        BigDecimal reste = charge.getMontant().subtract(charge.getMontantPaye());
        return new ChargeResponseDTO(
                charge.getId(),
                charge.getApartmentId(),
                charge.getPeriode(),
                charge.getMontant(),
                charge.getMontantPaye(),
                reste,
                charge.getStatut()
        );
    }

    public Payment toEntity(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setChargeId(dto.getChargeId());
        payment.setDate(dto.getDate());
        payment.setMontant(dto.getMontant());
        payment.setMoyen(dto.getMoyen());
        return payment;
    }

    public PaymentResponseDTO toResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getChargeId(),
                payment.getDate(),
                payment.getMontant(),
                payment.getMoyen()
        );
    }
}
