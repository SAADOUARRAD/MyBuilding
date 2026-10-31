package com.mybuilding.finances.service;

import com.mybuilding.finances.dto.ChargeRequestDTO;
import com.mybuilding.finances.dto.ChargeResponseDTO;

import java.util.List;

public interface ChargeService {
    ChargeResponseDTO create(ChargeRequestDTO dto);
    List<ChargeResponseDTO> getAll();
    ChargeResponseDTO getById(Long id);
    List<ChargeResponseDTO> getByApartmentId(Long apartmentId);
    List<ChargeResponseDTO> getUnpaid();
    void delete(Long id);
}
