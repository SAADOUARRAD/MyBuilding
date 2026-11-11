package com.mybuilding.incidents.dto;

import com.mybuilding.incidents.model.StatutWorkOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO composé pour l'agrégation : données locales de WorkOrder
 * + données distantes du Ticket + Apartment + Resident récupérées via OpenFeign.
 * Jalon V4 - Élément 2 : Agrégation et consultation enrichie.
 */
@Data
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
}
