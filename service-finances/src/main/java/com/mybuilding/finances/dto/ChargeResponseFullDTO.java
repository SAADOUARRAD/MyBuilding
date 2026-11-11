package com.mybuilding.finances.dto;

import com.mybuilding.finances.model.StatutCharge;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO composé pour l'agrégation : données locales de Charge
 * + données distantes de Apartment et Resident récupérées via OpenFeign.
 * Jalon V4 - Élément 2 : Agrégation et consultation enrichie.
 */
@Data
public class ChargeResponseFullDTO {

    // --- Données locales (service-finances) ---
    private Long id;
    private Long apartmentId;
    private String periode;
    private BigDecimal montant;
    private BigDecimal montantPaye;
    private StatutCharge statut;

    // --- Données distantes : Appartement (service-residents via Feign) ---
    private String apartmentNumero;
    private Integer etage;
    private Double superficie;

    // --- Données distantes : Résident (service-residents via Feign) ---
    private Long residentId;
    private String residentNom;
    private String residentEmail;
    private String residentTelephone;
}
