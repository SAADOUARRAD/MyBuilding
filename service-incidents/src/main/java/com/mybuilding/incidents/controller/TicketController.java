package com.mybuilding.incidents.controller;

import com.mybuilding.incidents.model.PrioriteTicket;
import com.mybuilding.incidents.model.Ticket;
import com.mybuilding.incidents.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostConstruct
    public void initData() {
        if (ticketRepository.count() == 0) {
            ticketRepository.save(new Ticket(1L, "Fuite d'eau au robinet de la cuisine", PrioriteTicket.HAUTE));
            ticketRepository.save(new Ticket(2L, "Panne interphone entrée principale", PrioriteTicket.MOYENNE));
        }
    }

    @GetMapping
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@Valid @RequestBody Ticket ticket) {
        Ticket saved = ticketRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
