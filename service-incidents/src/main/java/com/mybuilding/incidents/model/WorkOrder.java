package com.mybuilding.incidents.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "work_orders")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le ticket est obligatoire")
    @Column(nullable = false)
    private Long ticketId;

    @NotBlank(message = "L'artisan est obligatoire")
    @Column(nullable = false)
    private String artisan;

    @NotNull(message = "La date d'intervention est obligatoire")
    @Column(nullable = false)
    private LocalDate dateIntervention;

    @PositiveOrZero(message = "Le coût doit être positif ou nul")
    private BigDecimal cout;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutWorkOrder statut = StatutWorkOrder.PLANIFIE;

    public WorkOrder() {}

    public WorkOrder(Long ticketId, String artisan, LocalDate dateIntervention, BigDecimal cout) {
        this.ticketId = ticketId;
        this.artisan = artisan;
        this.dateIntervention = dateIntervention;
        this.cout = cout;
        this.statut = StatutWorkOrder.PLANIFIE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public String getArtisan() { return artisan; }
    public void setArtisan(String artisan) { this.artisan = artisan; }

    public LocalDate getDateIntervention() { return dateIntervention; }
    public void setDateIntervention(LocalDate dateIntervention) { this.dateIntervention = dateIntervention; }

    public BigDecimal getCout() { return cout; }
    public void setCout(BigDecimal cout) { this.cout = cout; }

    public StatutWorkOrder getStatut() { return statut; }
    public void setStatut(StatutWorkOrder statut) { this.statut = statut; }
}
