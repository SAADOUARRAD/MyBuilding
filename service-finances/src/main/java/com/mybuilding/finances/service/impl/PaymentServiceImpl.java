package com.mybuilding.finances.service.impl;

import com.mybuilding.finances.dto.PaymentRequestDTO;
import com.mybuilding.finances.dto.PaymentResponseDTO;
import com.mybuilding.finances.mapper.FinanceMapper;
import com.mybuilding.finances.model.Charge;
import com.mybuilding.finances.model.Payment;
import com.mybuilding.finances.model.StatutCharge;
import com.mybuilding.finances.repository.ChargeRepository;
import com.mybuilding.finances.repository.PaymentRepository;
import com.mybuilding.finances.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ChargeRepository chargeRepository;
    private final FinanceMapper financeMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, ChargeRepository chargeRepository, FinanceMapper financeMapper) {
        this.paymentRepository = paymentRepository;
        this.chargeRepository = chargeRepository;
        this.financeMapper = financeMapper;
    }

    @Override
    public PaymentResponseDTO recordPayment(PaymentRequestDTO dto) {
        Charge charge = chargeRepository.findById(dto.getChargeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Charge introuvable : id=" + dto.getChargeId() + " (RM-PAY-01)"));

        if (charge.getStatut() == StatutCharge.PAYEE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une charge déjà PAYEE ne peut plus recevoir de paiement (RM-PAY-06)");
        }

        BigDecimal resteAPayer = charge.getMontant().subtract(charge.getMontantPaye());

        if (dto.getMontant().compareTo(resteAPayer) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le montant du paiement (" + dto.getMontant() + ") dépasse le restant dû (" + resteAPayer + ") (RM-PAY-03)");
        }

        BigDecimal nouveauTotalPaye = charge.getMontantPaye().add(dto.getMontant());
        charge.setMontantPaye(nouveauTotalPaye);

        if (nouveauTotalPaye.compareTo(charge.getMontant()) == 0) {
            charge.setStatut(StatutCharge.PAYEE);
        } else {
            charge.setStatut(StatutCharge.PARTIELLE);
        }
        chargeRepository.save(charge);

        Payment payment = financeMapper.toEntity(dto);
        Payment saved = paymentRepository.save(payment);

        return financeMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getAll() {
        return paymentRepository.findAll().stream()
                .map(financeMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paiement introuvable avec l'id : " + id));
        return financeMapper.toResponseDTO(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getByChargeId(Long chargeId) {
        return paymentRepository.findByChargeId(chargeId).stream()
                .map(financeMapper::toResponseDTO)
                .toList();
    }
}
