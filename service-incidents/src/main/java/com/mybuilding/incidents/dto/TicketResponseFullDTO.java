package com.mybuilding.incidents.dto;

import com.mybuilding.incidents.model.PrioriteTicket;
import com.mybuilding.incidents.model.StatutTicket;

import java.math.BigDecimal;

/**
 * DTO composé (Agrégation Feign) combinant les données locales du Ticket
 * avec les données distantes de l'Appartement et du Résident récupérées via Feign.
 */
public class TicketResponseFullDTO {

    // --- Données locales du Ticket ---
    private Long id;
    private Long apartmentId;
    private String description;
    private PrioriteTicket priorite;
    private StatutTicket statut;

    // --- Données enrichies de l'Appartement (service-residents) ---
    private String apartmentNumero;
    private Integer apartmentEtage;
    private BigDecimal apartmentSuperficie;

    // --- Données enrichies du Résident (service-residents) ---
    private Long residentId;
    private String residentNom;
    private String residentEmail;
    private String residentTelephone;
    private String residentRole;

    public TicketResponseFullDTO() {}

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

    public String getApartmentNumero() { return apartmentNumero; }
    public void setApartmentNumero(String apartmentNumero) { this.apartmentNumero = apartmentNumero; }

    public Integer getEtage() { return apartmentEtage; }
    public void setEtage(Integer apartmentEtage) { this.apartmentEtage = apartmentEtage; }

    public BigDecimal getSuperficie() { return apartmentSuperficie; }
    public void setSuperficie(BigDecimal apartmentSuperficie) { this.apartmentSuperficie = apartmentSuperficie; }

    public Long getResidentId() { return residentId; }
    public void setResidentId(Long residentId) { this.residentId = residentId; }

    public String getResidentNom() { return residentNom; }
    public void setResidentNom(String residentNom) { this.residentNom = residentNom; }

    public String getResidentEmail() { return residentEmail; }
    public void setResidentEmail(String residentEmail) { this.residentEmail = residentEmail; }

    public String getResidentTelephone() { return residentTelephone; }
    public void setResidentTelephone(String residentTelephone) { this.residentTelephone = residentTelephone; }

    public String getResidentRole() { return residentRole; }
    public void setResidentRole(String residentRole) { this.residentRole = residentRole; }
}
