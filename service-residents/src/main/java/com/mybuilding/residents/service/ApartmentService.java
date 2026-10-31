package com.mybuilding.residents.service;

import com.mybuilding.residents.dto.ApartmentRequestDTO;
import com.mybuilding.residents.dto.ApartmentResponseDTO;

import java.util.List;

public interface ApartmentService {
    ApartmentResponseDTO create(ApartmentRequestDTO dto);
    List<ApartmentResponseDTO> getAll();
    ApartmentResponseDTO getById(Long id);
    ApartmentResponseDTO update(Long id, ApartmentRequestDTO dto);
    void delete(Long id);
    ApartmentResponseDTO assignResident(Long apartmentId, Long residentId);
    boolean exists(Long id);
}
