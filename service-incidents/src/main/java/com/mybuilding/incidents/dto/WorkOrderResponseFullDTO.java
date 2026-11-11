package com.mybuilding.incidents.dto;

import com.mybuilding.incidents.model.StatutWorkOrder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO composé pour l'agrégation : données locales de WorkOrder
 * + données distantes du Ticket + Apartment + Resident récupérées via OpenFeign.
 * Jalon V4 - Élément 2 : Agrégation et consultation enrichie.
 */
public class WorkOrderResponseFullDTO {

    // --- Données locales (service-incidents) ---
    private Long id;
    private Long ticketId;
    private String artisan;
    private LocalDate dateIntervention;
    private BigDecimal cout;
    private StatutWorkOrder statut;

    // --- Données du Ticket parent (en base locale) ---
    private String ticketDescription;
    private String ticketPriorite;
    private String ticketStatut;
    private Long apartmentId;

    // --- Données distantes : Appartement (service-residents via Feign) ---
    private String apartmentNumero;
    private Integer etage;

    // --- Données distantes : Résident (service-residents via Feign) ---
    private Long residentId;
    private String residentNom;
    private String residentEmail;

    public WorkOrderResponseFullDTO() {}

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

    public String getTicketDescription() { return ticketDescription; }
    public void setTicketDescription(String ticketDescription) { this.ticketDescription = ticketDescription; }

    public String getTicketPriorite() { return ticketPriorite; }
    public void setTicketPriorite(String ticketPriorite) { this.ticketPriorite = ticketPriorite; }

    public String getTicketStatut() { return ticketStatut; }
    public void setTicketStatut(String ticketStatut) { this.ticketStatut = ticketStatut; }

    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }

    public String getApartmentNumero() { return apartmentNumero; }
    public void setApartmentNumero(String apartmentNumero) { this.apartmentNumero = apartmentNumero; }

    public Integer getEtage() { return etage; }
    public void setEtage(Integer etage) { this.etage = etage; }

    public Long getResidentId() { return residentId; }
    public void setResidentId(Long residentId) { this.residentId = residentId; }

    public String getResidentNom() { return residentNom; }
    public void setResidentNom(String residentNom) { this.residentNom = residentNom; }

    public String getResidentEmail() { return residentEmail; }
    public void setResidentEmail(String residentEmail) { this.residentEmail = residentEmail; }
}
