package com.mybuilding.residents.mapper;

import com.mybuilding.residents.dto.ApartmentRequestDTO;
import com.mybuilding.residents.dto.ApartmentResponseDTO;
import com.mybuilding.residents.dto.ResidentRequestDTO;
import com.mybuilding.residents.dto.ResidentResponseDTO;
import com.mybuilding.residents.model.Apartment;
import com.mybuilding.residents.model.Resident;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ResidentMapper {

    public Resident toEntity(ResidentRequestDTO dto) {
        Resident resident = new Resident();
        resident.setNom(dto.getNom());
        resident.setEmail(dto.getEmail());
        resident.setTelephone(dto.getTelephone());
        resident.setRole(dto.getRole());
        resident.setSolde(BigDecimal.ZERO);
        return resident;
    }

    public ResidentResponseDTO toResponseDTO(Resident resident) {
        return new ResidentResponseDTO(
                resident.getId(),
                resident.getNom(),
                resident.getEmail(),
                resident.getTelephone(),
                resident.getRole(),
                resident.getSolde()
        );
    }

    public Apartment toEntity(ApartmentRequestDTO dto) {
        Apartment apartment = new Apartment();
        apartment.setNumero(dto.getNumero());
        apartment.setEtage(dto.getEtage());
        apartment.setSuperficie(dto.getSuperficie());
        apartment.setResidentId(dto.getResidentId());
        return apartment;
    }

    public ApartmentResponseDTO toResponseDTO(Apartment apartment) {
        return new ApartmentResponseDTO(
                apartment.getId(),
                apartment.getNumero(),
                apartment.getEtage(),
                apartment.getSuperficie(),
                apartment.getResidentId()
        );
    }
}
