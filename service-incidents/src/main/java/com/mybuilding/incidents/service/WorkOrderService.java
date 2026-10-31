package com.mybuilding.incidents.service;

import com.mybuilding.incidents.dto.WorkOrderRequestDTO;
import com.mybuilding.incidents.dto.WorkOrderResponseDTO;
import com.mybuilding.incidents.model.StatutWorkOrder;

import java.math.BigDecimal;
import java.util.List;

public interface WorkOrderService {
    WorkOrderResponseDTO create(WorkOrderRequestDTO dto);
    List<WorkOrderResponseDTO> getAll();
    WorkOrderResponseDTO getById(Long id);
    List<WorkOrderResponseDTO> getByTicketId(Long ticketId);
    WorkOrderResponseDTO updateStatus(Long id, StatutWorkOrder newStatus);
    WorkOrderResponseDTO close(Long id, BigDecimal finalCost);
    void delete(Long id);
}
