package com.mybuilding.finances.service.impl;

import com.mybuilding.finances.dto.ChargeRequestDTO;
import com.mybuilding.finances.dto.ChargeResponseDTO;
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

    public ChargeServiceImpl(ChargeRepository chargeRepository, FinanceMapper financeMapper) {
        this.chargeRepository = chargeRepository;
        this.financeMapper = financeMapper;
    }

    @Override
    public ChargeResponseDTO create(ChargeRequestDTO dto) {
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
}
