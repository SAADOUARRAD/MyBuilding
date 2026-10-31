package com.mybuilding.incidents.dto;

import com.mybuilding.incidents.model.StatutWorkOrder;
import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkOrderResponseDTO {

    private Long id;
    private Long ticketId;
    private String artisan;
    private LocalDate dateIntervention;
    private BigDecimal cout;
    private StatutWorkOrder statut;

    public WorkOrderResponseDTO() {}

    public WorkOrderResponseDTO(Long id, Long ticketId, String artisan, LocalDate dateIntervention, BigDecimal cout, StatutWorkOrder statut) {
        this.id = id;
        this.ticketId = ticketId;
        this.artisan = artisan;
        this.dateIntervention = dateIntervention;
        this.cout = cout;
        this.statut = statut;
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
