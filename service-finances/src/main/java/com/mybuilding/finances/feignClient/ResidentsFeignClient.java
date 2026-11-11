package com.mybuilding.finances.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Client OpenFeign pour communiquer avec service-residents via Eureka.
 * Jalon V4 : Communication synchrone inter-services sans passer par la Gateway.
 */
@FeignClient(name = "service-residents")
public interface ResidentsFeignClient {

    @GetMapping("/api/apartments/{id}")
    ApartmentDTO getApartmentById(@PathVariable("id") Long id);

    @GetMapping("/api/residents/{id}")
    ResidentDTO getResidentById(@PathVariable("id") Long id);

    @PatchMapping("/api/residents/{id}/solde")
    void updateResidentSolde(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload);
}
