package com.mybuilding.incidents.service;

import com.mybuilding.incidents.dto.TicketRequestDTO;
import com.mybuilding.incidents.dto.TicketResponseDTO;
import com.mybuilding.incidents.model.StatutTicket;

import java.util.List;

public interface TicketService {
    TicketResponseDTO create(TicketRequestDTO dto);
    List<TicketResponseDTO> getAll();
    TicketResponseDTO getById(Long id);
    List<TicketResponseDTO> getByApartmentId(Long apartmentId);
    TicketResponseDTO updateStatus(Long id, StatutTicket newStatus);
    void delete(Long id);
}
