package com.mybuilding.residents.service.impl;

import com.mybuilding.residents.dto.ApartmentRequestDTO;
import com.mybuilding.residents.dto.ApartmentResponseDTO;
import com.mybuilding.residents.mapper.ResidentMapper;
import com.mybuilding.residents.model.Apartment;
import com.mybuilding.residents.repository.ApartmentRepository;
import com.mybuilding.residents.repository.ResidentRepository;
import com.mybuilding.residents.service.ApartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ResidentRepository residentRepository, ResidentMapper residentMapper) {
        this.apartmentRepository = apartmentRepository;
        this.residentRepository = residentRepository;
        this.residentMapper = residentMapper;
    }

    @Override
    public ApartmentResponseDTO create(ApartmentRequestDTO dto) {
        if (apartmentRepository.existsByNumero(dto.getNumero())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un appartement avec ce numéro existe déjà : " + dto.getNumero());
        }
        if (dto.getResidentId() != null && !residentRepository.existsById(dto.getResidentId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le résident spécifié n'existe pas : id=" + dto.getResidentId());
        }
        Apartment apartment = residentMapper.toEntity(dto);
        Apartment saved = apartmentRepository.save(apartment);
        return residentMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApartmentResponseDTO> getAll() {
        return apartmentRepository.findAll().stream()
                .map(residentMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ApartmentResponseDTO getById(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appartement introuvable avec l'id : " + id));
        return residentMapper.toResponseDTO(apartment);
    }

    @Override
    public ApartmentResponseDTO update(Long id, ApartmentRequestDTO dto) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appartement introuvable avec l'id : " + id));

        if (!apartment.getNumero().equalsIgnoreCase(dto.getNumero()) && apartmentRepository.existsByNumero(dto.getNumero())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Le numéro d'appartement " + dto.getNumero() + " existe déjà");
        }
        if (dto.getResidentId() != null && !residentRepository.existsById(dto.getResidentId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le résident spécifié n'existe pas : id=" + dto.getResidentId());
        }

        apartment.setNumero(dto.getNumero());
        apartment.setEtage(dto.getEtage());
        apartment.setSuperficie(dto.getSuperficie());
        apartment.setResidentId(dto.getResidentId());

        Apartment updated = apartmentRepository.save(apartment);
        return residentMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appartement introuvable avec l'id : " + id));
        apartmentRepository.delete(apartment);
    }

    @Override
    public ApartmentResponseDTO assignResident(Long apartmentId, Long residentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appartement introuvable avec l'id : " + apartmentId));

        if (residentId != null && !residentRepository.existsById(residentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Résident introuvable avec l'id : " + residentId);
        }

        apartment.setResidentId(residentId);
        return residentMapper.toResponseDTO(apartmentRepository.save(apartment));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return apartmentRepository.existsById(id);
    }
}
