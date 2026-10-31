package com.mybuilding.residents.service;

import com.mybuilding.residents.dto.ResidentRequestDTO;
import com.mybuilding.residents.dto.ResidentResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ResidentService {
    ResidentResponseDTO create(ResidentRequestDTO dto);
    List<ResidentResponseDTO> getAll();
    ResidentResponseDTO getById(Long id);
    ResidentResponseDTO update(Long id, ResidentRequestDTO dto);
    void delete(Long id);
    ResidentResponseDTO updateSolde(Long id, BigDecimal delta);
}
