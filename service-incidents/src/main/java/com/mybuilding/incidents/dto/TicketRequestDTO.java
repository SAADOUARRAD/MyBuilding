package com.mybuilding.incidents.dto;

import com.mybuilding.incidents.model.PrioriteTicket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketRequestDTO {

    @NotNull(message = "L'appartement est obligatoire")
    private Long apartmentId;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "La priorité est obligatoire (BASSE, MOYENNE, HAUTE, URGENTE)")
    private PrioriteTicket priorite;

    public TicketRequestDTO() {}

    public TicketRequestDTO(Long apartmentId, String description, PrioriteTicket priorite) {
        this.apartmentId = apartmentId;
        this.description = description;
        this.priorite = priorite;
    }

    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PrioriteTicket getPriorite() { return priorite; }
    public void setPriorite(PrioriteTicket priorite) { this.priorite = priorite; }
}
