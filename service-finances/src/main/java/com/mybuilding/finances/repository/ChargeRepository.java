package com.mybuilding.finances.repository;

import com.mybuilding.finances.model.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {
    Optional<Charge> findByApartmentIdAndPeriode(Long apartmentId, String periode);
    boolean existsByApartmentIdAndPeriode(Long apartmentId, String periode);
    List<Charge> findByApartmentId(Long apartmentId);
}
