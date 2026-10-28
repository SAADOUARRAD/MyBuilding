package com.mybuilding.residents.repository;

import com.mybuilding.residents.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    Optional<Apartment> findByNumero(String numero);
    boolean existsByNumero(String numero);
}
