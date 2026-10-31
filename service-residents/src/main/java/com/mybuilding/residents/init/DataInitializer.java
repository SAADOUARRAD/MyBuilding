package com.mybuilding.residents.init;

import com.mybuilding.residents.dto.ApartmentRequestDTO;
import com.mybuilding.residents.dto.ResidentRequestDTO;
import com.mybuilding.residents.model.RoleResident;
import com.mybuilding.residents.repository.ApartmentRepository;
import com.mybuilding.residents.repository.ResidentRepository;
import com.mybuilding.residents.service.ApartmentService;
import com.mybuilding.residents.service.ResidentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ResidentRepository residentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ResidentService residentService;
    private final ApartmentService apartmentService;

    public DataInitializer(ResidentRepository residentRepository,
                           ApartmentRepository apartmentRepository,
                           ResidentService residentService,
                           ApartmentService apartmentService) {
        this.residentRepository = residentRepository;
        this.apartmentRepository = apartmentRepository;
        this.residentService = residentService;
        this.apartmentService = apartmentService;
    }

    @Override
    public void run(String... args) {
        if (residentRepository.count() == 0) {
            residentService.create(new ResidentRequestDTO("Ahmed Alami", "ahmed.alami@mybuilding.ma", "+212600112233", RoleResident.PROPRIETAIRE));
            residentService.create(new ResidentRequestDTO("Sara Bennani", "sara.bennani@mybuilding.ma", "+212600445566", RoleResident.LOCATAIRE));
        }

        if (apartmentRepository.count() == 0) {
            apartmentService.create(new ApartmentRequestDTO("A-101", 1, new BigDecimal("85.50"), 1L));
            apartmentService.create(new ApartmentRequestDTO("A-102", 1, new BigDecimal("92.00"), 2L));
            apartmentService.create(new ApartmentRequestDTO("B-201", 2, new BigDecimal("110.00"), null));
        }
    }
}
