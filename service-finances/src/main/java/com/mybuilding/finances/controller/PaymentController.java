package com.mybuilding.finances.controller;

import com.mybuilding.finances.model.Charge;
import com.mybuilding.finances.model.MoyenPaiement;
import com.mybuilding.finances.model.Payment;
import com.mybuilding.finances.model.StatutCharge;
import com.mybuilding.finances.repository.ChargeRepository;
import com.mybuilding.finances.repository.PaymentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final ChargeRepository chargeRepository;

    public PaymentController(PaymentRepository paymentRepository, ChargeRepository chargeRepository) {
        this.paymentRepository = paymentRepository;
        this.chargeRepository = chargeRepository;
    }

    @PostConstruct
    public void initData() {
        if (paymentRepository.count() == 0) {
            paymentRepository.save(new Payment(1L, LocalDate.now(), new BigDecimal("500.00"), MoyenPaiement.VIREMENT));
        }
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Payment payment) {
        Charge charge = chargeRepository.findById(payment.getChargeId()).orElse(null);
        if (charge == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Charge introuvable");
        }
        if (charge.getStatut() == StatutCharge.PAYEE) {
            return ResponseEntity.badRequest().body("La charge est déjà entièrement payée");
        }
        BigDecimal reste = charge.getMontant().subtract(charge.getMontantPaye());
        if (payment.getMontant().compareTo(reste) > 0) {
            return ResponseEntity.badRequest().body("Le paiement dépasse le montant restant dû: " + reste);
        }

        BigDecimal nouveauMontantPaye = charge.getMontantPaye().add(payment.getMontant());
        charge.setMontantPaye(nouveauMontantPaye);
        if (nouveauMontantPaye.compareTo(charge.getMontant()) == 0) {
            charge.setStatut(StatutCharge.PAYEE);
        } else {
            charge.setStatut(StatutCharge.PARTIELLE);
        }
        chargeRepository.save(charge);

        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
