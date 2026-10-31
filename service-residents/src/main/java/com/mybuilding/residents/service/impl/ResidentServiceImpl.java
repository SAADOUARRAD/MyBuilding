package com.mybuilding.residents.service.impl;

import com.mybuilding.residents.dto.ResidentRequestDTO;
import com.mybuilding.residents.dto.ResidentResponseDTO;
import com.mybuilding.residents.mapper.ResidentMapper;
import com.mybuilding.residents.model.Resident;
import com.mybuilding.residents.repository.ResidentRepository;
import com.mybuilding.residents.service.ResidentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;

    public ResidentServiceImpl(ResidentRepository residentRepository, ResidentMapper residentMapper) {
        this.residentRepository = residentRepository;
        this.residentMapper = residentMapper;
    }

    @Override
    public ResidentResponseDTO create(ResidentRequestDTO dto) {
        if (residentRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un résident avec cet email existe déjà : " + dto.getEmail());
        }
        Resident entity = residentMapper.toEntity(dto);
        Resident saved = residentRepository.save(entity);
        return residentMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResidentResponseDTO> getAll() {
        return residentRepository.findAll().stream()
                .map(residentMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ResidentResponseDTO getById(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Résident introuvable avec l'id : " + id));
        return residentMapper.toResponseDTO(resident);
    }

    @Override
    public ResidentResponseDTO update(Long id, ResidentRequestDTO dto) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Résident introuvable avec l'id : " + id));

        if (!resident.getEmail().equalsIgnoreCase(dto.getEmail()) && residentRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "L'email " + dto.getEmail() + " est déjà pris par un autre résident");
        }

        resident.setNom(dto.getNom());
        resident.setEmail(dto.getEmail());
        resident.setTelephone(dto.getTelephone());
        resident.setRole(dto.getRole());

        Resident updated = residentRepository.save(resident);
        return residentMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Résident introuvable avec l'id : " + id));
        residentRepository.delete(resident);
    }

    @Override
    public ResidentResponseDTO updateSolde(Long id, BigDecimal delta) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Résident introuvable avec l'id : " + id));
        resident.setSolde(resident.getSolde().add(delta));
        return residentMapper.toResponseDTO(residentRepository.save(resident));
    }
}
