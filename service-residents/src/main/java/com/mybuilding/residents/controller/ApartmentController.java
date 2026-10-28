package com.mybuilding.residents.controller;

import com.mybuilding.residents.model.Apartment;
import com.mybuilding.residents.repository.ApartmentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private final ApartmentRepository apartmentRepository;

    public ApartmentController(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @PostConstruct
    public void initData() {
        if (apartmentRepository.count() == 0) {
            apartmentRepository.save(new Apartment("A-101", 1, new BigDecimal("85.50"), 1L));
            apartmentRepository.save(new Apartment("A-102", 1, new BigDecimal("92.00"), 2L));
            apartmentRepository.save(new Apartment("B-201", 2, new BigDecimal("110.00"), null));
        }
    }

    @GetMapping
    public List<Apartment> getAll() {
        return apartmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getById(@PathVariable Long id) {
        return apartmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Map<String, Boolean>> exists(@PathVariable Long id) {
        boolean exists = apartmentRepository.existsById(id);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Apartment apartment) {
        if (apartmentRepository.existsByNumero(apartment.getNumero())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Numéro d'appartement déjà existant");
        }
        Apartment saved = apartmentRepository.save(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
