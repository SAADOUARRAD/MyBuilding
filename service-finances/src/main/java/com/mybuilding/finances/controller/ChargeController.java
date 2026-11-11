package com.mybuilding.finances.controller;

import com.mybuilding.finances.dto.ChargeRequestDTO;
import com.mybuilding.finances.dto.ChargeResponseDTO;
import com.mybuilding.finances.dto.ChargeResponseFullDTO;
import com.mybuilding.finances.model.Charge;
import com.mybuilding.finances.model.StatutCharge;
import com.mybuilding.finances.repository.ChargeRepository;
import com.mybuilding.finances.service.ChargeService;
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

    private final ChargeService chargeService;
    private final ChargeRepository chargeRepository;

    public ChargeController(ChargeService chargeService, ChargeRepository chargeRepository) {
        this.chargeService = chargeService;
        this.chargeRepository = chargeRepository;
    }

    @PostConstruct
    public void initData() {
        // Initialisation directe via le repository pour éviter les appels Feign au démarrage
        // (service-residents peut ne pas encore être UP lors du démarrage)
        if (chargeRepository.count() == 0) {
            Charge c1 = new Charge();
            c1.setApartmentId(1L);
            c1.setPeriode("2026-11");
            c1.setMontant(new BigDecimal("1500.00"));
            c1.setMontantPaye(BigDecimal.ZERO);
            c1.setStatut(StatutCharge.EMISE);
            chargeRepository.save(c1);

            Charge c2 = new Charge();
            c2.setApartmentId(2L);
            c2.setPeriode("2026-11");
            c2.setMontant(new BigDecimal("1200.00"));
            c2.setMontantPaye(BigDecimal.ZERO);
            c2.setStatut(StatutCharge.EMISE);
            chargeRepository.save(c2);
        }
    }

    @GetMapping
    public ResponseEntity<List<ChargeResponseDTO>> getAll() {
        return ResponseEntity.ok(chargeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(chargeService.getById(id));
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ChargeResponseFullDTO> getDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(chargeService.getChargeFullById(id));
    }

    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<ChargeResponseDTO>> getByApartment(@PathVariable Long apartmentId) {
        return ResponseEntity.ok(chargeService.getByApartmentId(apartmentId));
    }

    @GetMapping("/unpaid")
    public ResponseEntity<List<ChargeResponseDTO>> getUnpaid() {
        return ResponseEntity.ok(chargeService.getUnpaid());
    }

    @PostMapping
    public ResponseEntity<ChargeResponseDTO> create(@Valid @RequestBody ChargeRequestDTO dto) {
        ChargeResponseDTO created = chargeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
