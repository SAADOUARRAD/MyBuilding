package com.mybuilding.incidents.service.impl;

import com.mybuilding.incidents.dto.WorkOrderRequestDTO;
import com.mybuilding.incidents.dto.WorkOrderResponseDTO;
import com.mybuilding.incidents.mapper.IncidentMapper;
import com.mybuilding.incidents.model.StatutTicket;
import com.mybuilding.incidents.model.StatutWorkOrder;
import com.mybuilding.incidents.model.Ticket;
import com.mybuilding.incidents.model.WorkOrder;
import com.mybuilding.incidents.repository.TicketRepository;
import com.mybuilding.incidents.repository.WorkOrderRepository;
import com.mybuilding.incidents.service.WorkOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final TicketRepository ticketRepository;
    private final IncidentMapper incidentMapper;

    public WorkOrderServiceImpl(WorkOrderRepository workOrderRepository, TicketRepository ticketRepository, IncidentMapper incidentMapper) {
        this.workOrderRepository = workOrderRepository;
        this.ticketRepository = ticketRepository;
        this.incidentMapper = incidentMapper;
    }

    @Override
    public WorkOrderResponseDTO create(WorkOrderRequestDTO dto) {
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket introuvable : id=" + dto.getTicketId() + " (RM-WO-01)"));

        if (ticket.getStatut() == StatutTicket.OUVERT) {
            ticket.setStatut(StatutTicket.EN_COURS);
            ticketRepository.save(ticket);
        } else if (ticket.getStatut() == StatutTicket.RESOLU || ticket.getStatut() == StatutTicket.ANNULE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de créer un ordre de travail pour un ticket RESOLU ou ANNULE (RM-WO-02)");
        }

        WorkOrder wo = incidentMapper.toEntity(dto);
        WorkOrder saved = workOrderRepository.save(wo);
        return incidentMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderResponseDTO> getAll() {
        return workOrderRepository.findAll().stream()
                .map(incidentMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WorkOrderResponseDTO getById(Long id) {
        WorkOrder wo = workOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkOrder introuvable avec l'id : " + id));
        return incidentMapper.toResponseDTO(wo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderResponseDTO> getByTicketId(Long ticketId) {
        return workOrderRepository.findByTicketId(ticketId).stream()
                .map(incidentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public WorkOrderResponseDTO updateStatus(Long id, StatutWorkOrder newStatus) {
        WorkOrder wo = workOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkOrder introuvable avec l'id : " + id));

        if (wo.getStatut() == StatutWorkOrder.CLOTURE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une intervention CLOTURE ne peut plus être modifiée (RM-WO-05)");
        }

        wo.setStatut(newStatus);
        return incidentMapper.toResponseDTO(workOrderRepository.save(wo));
    }

    @Override
    public WorkOrderResponseDTO close(Long id, BigDecimal finalCost) {
        WorkOrder wo = workOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkOrder introuvable avec l'id : " + id));

        if (wo.getStatut() == StatutWorkOrder.CLOTURE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "WorkOrder déjà clôturé");
        }

        if (finalCost != null) {
            if (finalCost.compareTo(BigDecimal.ZERO) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le coût doit être >= 0 (RM-WO-03)");
            }
            wo.setCout(finalCost);
        } else if (wo.getCout() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une intervention CLOTURE doit obligatoirement avoir un coût (RM-WO-04)");
        }

        wo.setStatut(StatutWorkOrder.CLOTURE);
        WorkOrder saved = workOrderRepository.save(wo);

        ticketRepository.findById(wo.getTicketId()).ifPresent(ticket -> {
            ticket.setStatut(StatutTicket.RESOLU);
            ticketRepository.save(ticket);
        });

        return incidentMapper.toResponseDTO(saved);
    }

    @Override
    public void delete(Long id) {
        WorkOrder wo = workOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkOrder introuvable avec l'id : " + id));
        workOrderRepository.delete(wo);
    }
}
