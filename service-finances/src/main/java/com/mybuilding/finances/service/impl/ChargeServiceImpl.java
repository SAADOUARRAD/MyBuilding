package com.mybuilding.finances.service.impl;

import com.mybuilding.finances.dto.ChargeRequestDTO;
import com.mybuilding.finances.dto.ChargeResponseDTO;
import com.mybuilding.finances.dto.ChargeResponseFullDTO;
import com.mybuilding.finances.feignClient.ApartmentDTO;
import com.mybuilding.finances.feignClient.ResidentDTO;
import com.mybuilding.finances.feignClient.ResidentsFeignClient;
import com.mybuilding.finances.mapper.FinanceMapper;
import com.mybuilding.finances.model.Charge;
import com.mybuilding.finances.model.StatutCharge;
import com.mybuilding.finances.repository.ChargeRepository;
import com.mybuilding.finances.service.ChargeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ChargeServiceImpl implements ChargeService {

    private final ChargeRepository chargeRepository;
    private final FinanceMapper financeMapper;
    private final ResidentsFeignClient residentsFeignClient;

    public ChargeServiceImpl(ChargeRepository chargeRepository,
                             FinanceMapper financeMapper,
                             ResidentsFeignClient residentsFeignClient) {
        this.chargeRepository = chargeRepository;
        this.financeMapper = financeMapper;
        this.residentsFeignClient = residentsFeignClient;
    }

    @Override
    public ChargeResponseDTO create(ChargeRequestDTO dto) {
        // --- Jalon V4 : Validation préalable de la référence distante via OpenFeign ---
        // Vérifie que l'appartement existe réellement dans service-residents avant création
        residentsFeignClient.getApartmentById(dto.getApartmentId());

        if (chargeRepository.existsByApartmentIdAndPeriode(dto.getApartmentId(), dto.getPeriode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Une charge a déjà été émise pour l'appartement " + dto.getApartmentId() + " sur la période " + dto.getPeriode() + " (RM-CHG-04)");
        }

        Charge charge = financeMapper.toEntity(dto);
        Charge saved = chargeRepository.save(charge);
        return financeMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChargeResponseDTO> getAll() {
        return chargeRepository.findAll().stream()
                .map(financeMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ChargeResponseDTO getById(Long id) {
        Charge charge = chargeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Charge introuvable avec l'id : " + id));
        return financeMapper.toResponseDTO(charge);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChargeResponseDTO> getByApartmentId(Long apartmentId) {
        return chargeRepository.findByApartmentId(apartmentId).stream()
                .map(financeMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChargeResponseDTO> getUnpaid() {
        return chargeRepository.findAll().stream()
                .filter(c -> c.getStatut() != StatutCharge.PAYEE)
                .map(financeMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Charge charge = chargeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Charge introuvable avec l'id : " + id));
        chargeRepository.delete(charge);
    }

    @Override
    @Transactional(readOnly = true)
    public ChargeResponseFullDTO getChargeFullById(Long id) {
        // --- Jalon V4 : Agrégation et consultation enrichie (DTO composé) ---
        Charge charge = chargeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Charge introuvable avec l'id : " + id));

        ChargeResponseFullDTO fullDto = new ChargeResponseFullDTO();
        fullDto.setId(charge.getId());
        fullDto.setApartmentId(charge.getApartmentId());
        fullDto.setPeriode(charge.getPeriode());
        fullDto.setMontant(charge.getMontant());
        fullDto.setMontantPaye(charge.getMontantPaye());
        fullDto.setStatut(charge.getStatut());

        // 1. Récupération distante de l'Appartement via OpenFeign
        try {
            ApartmentDTO apartment = residentsFeignClient.getApartmentById(charge.getApartmentId());
            if (apartment != null) {
                fullDto.setApartmentNumero(apartment.getNumero());
                fullDto.setEtage(apartment.getEtage());
                fullDto.setSuperficie(apartment.getSuperficie());
                fullDto.setResidentId(apartment.getResidentId());

                // 2. Récupération distante du Résident associé via OpenFeign
                if (apartment.getResidentId() != null) {
                    ResidentDTO resident = residentsFeignClient.getResidentById(apartment.getResidentId());
                    if (resident != null) {
                        fullDto.setResidentNom(resident.getNom());
                        fullDto.setResidentEmail(resident.getEmail());
                        fullDto.setResidentTelephone(resident.getTelephone());
                    }
                }
            }
        } catch (Exception e) {
            // Tolérance si le service distant est temporairement dégradé lors de la lecture
        }

        return fullDto;
    }
}
