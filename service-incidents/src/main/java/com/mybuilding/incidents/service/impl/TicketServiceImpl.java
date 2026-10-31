package com.mybuilding.incidents.service.impl;

import com.mybuilding.incidents.dto.TicketRequestDTO;
import com.mybuilding.incidents.dto.TicketResponseDTO;
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

    public TicketServiceImpl(TicketRepository ticketRepository, IncidentMapper incidentMapper) {
        this.ticketRepository = ticketRepository;
        this.incidentMapper = incidentMapper;
    }

    @Override
    public TicketResponseDTO create(TicketRequestDTO dto) {
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
        return incidentMapper.toResponseDTO(ticketRepository.save(ticket));
    }

    @Override
    public void delete(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket introuvable avec l'id : " + id));
        ticketRepository.delete(ticket);
    }
}
