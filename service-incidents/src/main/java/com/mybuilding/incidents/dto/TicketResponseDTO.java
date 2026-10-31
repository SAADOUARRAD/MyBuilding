package com.mybuilding.incidents.dto;

import com.mybuilding.incidents.model.PrioriteTicket;
import com.mybuilding.incidents.model.StatutTicket;

public class TicketResponseDTO {

    private Long id;
    private Long apartmentId;
    private String description;
    private PrioriteTicket priorite;
    private StatutTicket statut;

    public TicketResponseDTO() {}

    public TicketResponseDTO(Long id, Long apartmentId, String description, PrioriteTicket priorite, StatutTicket statut) {
        this.id = id;
        this.apartmentId = apartmentId;
        this.description = description;
        this.priorite = priorite;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PrioriteTicket getPriorite() { return priorite; }
    public void setPriorite(PrioriteTicket priorite) { this.priorite = priorite; }

    public StatutTicket getStatut() { return statut; }
    public void setStatut(StatutTicket statut) { this.statut = statut; }
}
