package com.mybuilding.finances.controller;

import com.mybuilding.finances.model.Charge;
import com.mybuilding.finances.repository.ChargeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/charges")
public class ChargeController {

    private final ChargeRepository chargeRepository;

    public ChargeController(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    @PostConstruct
    public void initData() {
        if (chargeRepository.count() == 0) {
            chargeRepository.save(new Charge(1L, "2026-11", new BigDecimal("1500.00")));
            chargeRepository.save(new Charge(2L, "2026-11", new BigDecimal("1200.00")));
        }
    }

    @GetMapping
    public List<Charge> getAll() {
        return chargeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Charge> getById(@PathVariable Long id) {
        return chargeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Charge charge) {
        if (chargeRepository.existsByApartmentIdAndPeriode(charge.getApartmentId(), charge.getPeriode())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Une charge existe déjà pour cet appartement et cette période");
        }
        Charge saved = chargeRepository.save(charge);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
