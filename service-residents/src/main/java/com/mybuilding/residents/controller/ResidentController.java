package com.mybuilding.residents.controller;

import com.mybuilding.residents.model.Resident;
import com.mybuilding.residents.model.RoleResident;
import com.mybuilding.residents.repository.ResidentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentRepository residentRepository;

    public ResidentController(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @PostConstruct
    public void initData() {
        if (residentRepository.count() == 0) {
            residentRepository.save(new Resident("Ahmed Alami", "ahmed.alami@mybuilding.ma", "+212600112233", RoleResident.PROPRIETAIRE));
            residentRepository.save(new Resident("Sara Bennani", "sara.bennani@mybuilding.ma", "+212600445566", RoleResident.LOCATAIRE));
        }
    }

    @GetMapping
    public List<Resident> getAll() {
        return residentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resident> getById(@PathVariable Long id) {
        return residentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Resident resident) {
        if (residentRepository.existsByEmail(resident.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà utilisé");
        }
        Resident saved = residentRepository.save(resident);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
