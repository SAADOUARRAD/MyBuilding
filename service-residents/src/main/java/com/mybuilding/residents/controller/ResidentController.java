package com.mybuilding.residents.controller;

import com.mybuilding.residents.dto.ResidentRequestDTO;
import com.mybuilding.residents.dto.ResidentResponseDTO;
import com.mybuilding.residents.model.RoleResident;
import com.mybuilding.residents.repository.ResidentRepository;
import com.mybuilding.residents.service.ResidentService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;
    private final ResidentRepository residentRepository;

    public ResidentController(ResidentService residentService, ResidentRepository residentRepository) {
        this.residentService = residentService;
        this.residentRepository = residentRepository;
    }

    @PostConstruct
    public void initData() {
        if (residentRepository.count() == 0) {
            residentService.create(new ResidentRequestDTO("Ahmed Alami", "ahmed.alami@mybuilding.ma", "+212600112233", RoleResident.PROPRIETAIRE));
            residentService.create(new ResidentRequestDTO("Sara Bennani", "sara.bennani@mybuilding.ma", "+212600445566", RoleResident.LOCATAIRE));
        }
    }

    @GetMapping
    public ResponseEntity<List<ResidentResponseDTO>> getAll() {
        return ResponseEntity.ok(residentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(residentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResidentResponseDTO> create(@Valid @RequestBody ResidentRequestDTO dto) {
        ResidentResponseDTO created = residentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ResidentRequestDTO dto) {
        return ResponseEntity.ok(residentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        residentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/solde")
    public ResponseEntity<ResidentResponseDTO> updateSolde(@PathVariable Long id, @RequestBody Map<String, BigDecimal> payload) {
        BigDecimal delta = payload.getOrDefault("delta", BigDecimal.ZERO);
        return ResponseEntity.ok(residentService.updateSolde(id, delta));
    }
}
