package com.mybuilding.incidents.mapper;

import com.mybuilding.incidents.dto.TicketRequestDTO;
import com.mybuilding.incidents.dto.TicketResponseDTO;
import com.mybuilding.incidents.dto.WorkOrderRequestDTO;
import com.mybuilding.incidents.dto.WorkOrderResponseDTO;
import com.mybuilding.incidents.model.StatutTicket;
import com.mybuilding.incidents.model.StatutWorkOrder;
import com.mybuilding.incidents.model.Ticket;
import com.mybuilding.incidents.model.WorkOrder;
import org.springframework.stereotype.Component;

@Component
public class IncidentMapper {

    public Ticket toEntity(TicketRequestDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setApartmentId(dto.getApartmentId());
        ticket.setDescription(dto.getDescription());
        ticket.setPriorite(dto.getPriorite());
        ticket.setStatut(StatutTicket.OUVERT);
        return ticket;
    }

    public TicketResponseDTO toResponseDTO(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getApartmentId(),
                ticket.getDescription(),
                ticket.getPriorite(),
                ticket.getStatut()
        );
    }

    public WorkOrder toEntity(WorkOrderRequestDTO dto) {
        WorkOrder wo = new WorkOrder();
        wo.setTicketId(dto.getTicketId());
        wo.setArtisan(dto.getArtisan());
        wo.setDateIntervention(dto.getDateIntervention());
        wo.setCout(dto.getCout());
        wo.setStatut(StatutWorkOrder.PLANIFIE);
        return wo;
    }

    public WorkOrderResponseDTO toResponseDTO(WorkOrder wo) {
        return new WorkOrderResponseDTO(
                wo.getId(),
                wo.getTicketId(),
                wo.getArtisan(),
                wo.getDateIntervention(),
                wo.getCout(),
                wo.getStatut()
        );
    }
}
