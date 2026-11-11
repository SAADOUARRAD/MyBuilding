package com.mybuilding.incidents.service.impl;

import com.mybuilding.incidents.dto.TicketRequestDTO;
import com.mybuilding.incidents.dto.TicketResponseDTO;
import com.mybuilding.incidents.dto.TicketResponseFullDTO;
import com.mybuilding.incidents.feignClient.ApartmentDTO;
import com.mybuilding.incidents.feignClient.ResidentDTO;
import com.mybuilding.incidents.feignClient.ResidentsFeignClient;
import com.mybuilding.incidents.mapper.IncidentMapper;
import com.mybuilding.incidents.model.StatutTicket;
import com.mybuilding.incidents.model.Ticket;
import com.mybuilding.incidents.repository.TicketRepository;
import com.mybuilding.incidents.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final IncidentMapper incidentMapper;
    private final ResidentsFeignClient residentsFeignClient;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             IncidentMapper incidentMapper,
                             ResidentsFeignClient residentsFeignClient) {
        this.ticketRepository = ticketRepository;
        this.incidentMapper = incidentMapper;
        this.residentsFeignClient = residentsFeignClient;
    }

    @Override
    public TicketResponseDTO create(TicketRequestDTO dto) {
        // --- Jalon V4 : Validation préalable de la référence distante via OpenFeign ---
        // Vérifie que l'appartement existe réellement dans service-residents avant création
        residentsFeignClient.getApartmentById(dto.getApartmentId());

        Ticket ticket = incidentMapper.toEntity(dto);
        Ticket saved = ticketRepository.save(ticket);
        return incidentMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getAll() {
        return ticketRepository.findAll().stream()
                .map(incidentMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponseDTO getById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket introuvable avec l'id : " + id));
        return incidentMapper.toResponseDTO(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponseFullDTO getTicketFullById(Long id) {
        // --- Jalon V4 : Agrégation et consultation enrichie (DTO composé) ---
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket introuvable avec l'id : " + id));

        TicketResponseFullDTO fullDto = new TicketResponseFullDTO();
        fullDto.setId(ticket.getId());
        fullDto.setApartmentId(ticket.getApartmentId());
        fullDto.setDescription(ticket.getDescription());
        fullDto.setPriorite(ticket.getPriorite());
        fullDto.setStatut(ticket.getStatut());

        // 1. Récupération distante de l'Appartement via OpenFeign
        try {
            ApartmentDTO apartment = residentsFeignClient.getApartmentById(ticket.getApartmentId());
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
                        fullDto.setResidentRole(resident.getRole());
                    }
                }
            }
        } catch (Exception e) {
            // Tolérance si le service distant est temporairement dégradé lors de la lecture
        }

        return fullDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getByApartmentId(Long apartmentId) {
        return ticketRepository.findByApartmentId(apartmentId).stream()
                .map(incidentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public TicketResponseDTO updateStatus(Long id, StatutTicket newStatus) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket introuvable avec l'id : " + id));

        if (ticket.getStatut() == StatutTicket.RESOLU) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un ticket RESOLU ne peut plus être modifié (RM-TIC-02)");
        }
        if (ticket.getStatut() == StatutTicket.ANNULE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un ticket ANNULE ne peut plus être repris (RM-TIC-03)");
        }

        ticket.setStatut(newStatus);
        Ticket updated = ticketRepository.save(ticket);
        return incidentMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket introuvable avec l'id : " + id);
        }
        ticketRepository.deleteById(id);
    }
}
