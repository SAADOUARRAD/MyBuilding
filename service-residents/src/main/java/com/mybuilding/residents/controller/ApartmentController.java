package com.mybuilding.residents.controller;

import com.mybuilding.residents.dto.ApartmentRequestDTO;
import com.mybuilding.residents.dto.ApartmentResponseDTO;
import com.mybuilding.residents.service.ApartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public ResponseEntity<List<ApartmentResponseDTO>> getAll() {
        return ResponseEntity.ok(apartmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(apartmentService.getById(id));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Map<String, Boolean>> exists(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("exists", apartmentService.exists(id)));
    }

    @PostMapping
    public ResponseEntity<ApartmentResponseDTO> create(@Valid @RequestBody ApartmentRequestDTO dto) {
        ApartmentResponseDTO created = apartmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ApartmentRequestDTO dto) {
        return ResponseEntity.ok(apartmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        apartmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/resident")
    public ResponseEntity<ApartmentResponseDTO> assignResident(@PathVariable Long id, @RequestBody Map<String, Long> payload) {
        Long residentId = payload.get("residentId");
        return ResponseEntity.ok(apartmentService.assignResident(id, residentId));
    }
}
