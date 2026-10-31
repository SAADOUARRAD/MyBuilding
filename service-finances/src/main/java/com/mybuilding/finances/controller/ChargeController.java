package com.mybuilding.finances.controller;

import com.mybuilding.finances.dto.ChargeRequestDTO;
import com.mybuilding.finances.dto.ChargeResponseDTO;
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
        if (chargeRepository.count() == 0) {
            chargeService.create(new ChargeRequestDTO(1L, "2026-11", new BigDecimal("1500.00")));
            chargeService.create(new ChargeRequestDTO(2L, "2026-11", new BigDecimal("1200.00")));
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
