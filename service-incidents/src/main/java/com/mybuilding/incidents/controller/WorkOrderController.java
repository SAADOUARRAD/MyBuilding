package com.mybuilding.incidents.controller;

import com.mybuilding.incidents.model.WorkOrder;
import com.mybuilding.incidents.repository.WorkOrderRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderRepository workOrderRepository;

    public WorkOrderController(WorkOrderRepository workOrderRepository) {
        this.workOrderRepository = workOrderRepository;
    }

    @PostConstruct
    public void initData() {
        if (workOrderRepository.count() == 0) {
            workOrderRepository.save(new WorkOrder(1L, "Plomberie Express", LocalDate.now().plusDays(1), new BigDecimal("450.00")));
        }
    }

    @GetMapping
    public List<WorkOrder> getAll() {
        return workOrderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getById(@PathVariable Long id) {
        return workOrderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WorkOrder> create(@Valid @RequestBody WorkOrder workOrder) {
        WorkOrder saved = workOrderRepository.save(workOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
