package com.mybuilding.incidents.controller;

import com.mybuilding.incidents.dto.TicketRequestDTO;
import com.mybuilding.incidents.dto.TicketResponseDTO;
import com.mybuilding.incidents.model.PrioriteTicket;
import com.mybuilding.incidents.model.StatutTicket;
import com.mybuilding.incidents.repository.TicketRepository;
import com.mybuilding.incidents.service.TicketService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    public TicketController(TicketService ticketService, TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    @PostConstruct
    public void initData() {
        if (ticketRepository.count() == 0) {
            ticketService.create(new TicketRequestDTO(1L, "Fuite d'eau au robinet de la cuisine", PrioriteTicket.HAUTE));
            ticketService.create(new TicketRequestDTO(2L, "Panne interphone entrée principale", PrioriteTicket.MOYENNE));
        }
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAll() {
        return ResponseEntity.ok(ticketService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getById(id));
    }

    @GetMapping("/apartment/{apartmentId}")
    public ResponseEntity<List<TicketResponseDTO>> getByApartment(@PathVariable Long apartmentId) {
        return ResponseEntity.ok(ticketService.getByApartmentId(apartmentId));
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> create(@Valid @RequestBody TicketRequestDTO dto) {
        TicketResponseDTO created = ticketService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponseDTO> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        StatutTicket statut = StatutTicket.valueOf(payload.get("statut"));
        return ResponseEntity.ok(ticketService.updateStatus(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
