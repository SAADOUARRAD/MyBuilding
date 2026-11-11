package com.mybuilding.incidents.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client OpenFeign fortement typé pour interagir avec service-residents.
 * Utilise la résolution de nom Eureka sans passer par l'API Gateway.
 */
@FeignClient(name = "service-residents")
public interface ResidentsFeignClient {

    @GetMapping("/api/apartments/{id}")
    ApartmentDTO getApartmentById(@PathVariable("id") Long id);

    @GetMapping("/api/residents/{id}")
    ResidentDTO getResidentById(@PathVariable("id") Long id);
}
