package com.mybuilding.incidents.controller;

import com.mybuilding.incidents.dto.WorkOrderRequestDTO;
import com.mybuilding.incidents.dto.WorkOrderResponseDTO;
import com.mybuilding.incidents.model.StatutWorkOrder;
import com.mybuilding.incidents.repository.WorkOrderRepository;
import com.mybuilding.incidents.service.WorkOrderService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final WorkOrderRepository workOrderRepository;

    public WorkOrderController(WorkOrderService workOrderService, WorkOrderRepository workOrderRepository) {
        this.workOrderService = workOrderService;
        this.workOrderRepository = workOrderRepository;
    }

    @PostConstruct
    public void initData() {
        if (workOrderRepository.count() == 0) {
            workOrderService.create(new WorkOrderRequestDTO(1L, "Plomberie Express", LocalDate.now().plusDays(1), new BigDecimal("450.00")));
        }
    }

    @GetMapping
    public ResponseEntity<List<WorkOrderResponseDTO>> getAll() {
        return ResponseEntity.ok(workOrderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workOrderService.getById(id));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<WorkOrderResponseDTO>> getByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(workOrderService.getByTicketId(ticketId));
    }

    @PostMapping
    public ResponseEntity<WorkOrderResponseDTO> create(@Valid @RequestBody WorkOrderRequestDTO dto) {
        WorkOrderResponseDTO created = workOrderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<WorkOrderResponseDTO> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        StatutWorkOrder statut = StatutWorkOrder.valueOf(payload.get("statut"));
        return ResponseEntity.ok(workOrderService.updateStatus(id, statut));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<WorkOrderResponseDTO> close(@PathVariable Long id, @RequestBody(required = false) Map<String, BigDecimal> payload) {
        BigDecimal finalCost = payload != null ? payload.get("cout") : null;
        return ResponseEntity.ok(workOrderService.close(id, finalCost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
