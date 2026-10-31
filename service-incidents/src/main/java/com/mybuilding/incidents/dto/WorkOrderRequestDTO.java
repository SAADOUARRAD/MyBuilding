package com.mybuilding.incidents.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkOrderRequestDTO {

    @NotNull(message = "L'identifiant du ticket est obligatoire")
    private Long ticketId;

    @NotBlank(message = "Le nom de l'artisan est obligatoire")
    private String artisan;

    @NotNull(message = "La date d'intervention est obligatoire")
    private LocalDate dateIntervention;

    @PositiveOrZero(message = "Le coût doit être supérieur ou égal à zéro")
    private BigDecimal cout;

    public WorkOrderRequestDTO() {}

    public WorkOrderRequestDTO(Long ticketId, String artisan, LocalDate dateIntervention, BigDecimal cout) {
        this.ticketId = ticketId;
        this.artisan = artisan;
        this.dateIntervention = dateIntervention;
        this.cout = cout;
    }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public String getArtisan() { return artisan; }
    public void setArtisan(String artisan) { this.artisan = artisan; }

    public LocalDate getDateIntervention() { return dateIntervention; }
    public void setDateIntervention(LocalDate dateIntervention) { this.dateIntervention = dateIntervention; }

    public BigDecimal getCout() { return cout; }
    public void setCout(BigDecimal cout) { this.cout = cout; }
}
