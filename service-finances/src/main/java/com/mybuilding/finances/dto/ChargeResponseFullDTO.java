package com.mybuilding.finances.dto;

import com.mybuilding.finances.model.StatutCharge;

import java.math.BigDecimal;

/**
 * DTO composé pour l'agrégation : données locales de Charge
 * + données distantes de Apartment et Resident récupérées via OpenFeign.
 * Jalon V4 - Élément 2 : Agrégation et consultation enrichie.
 */
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
    private BigDecimal superficie;

    // --- Données distantes : Résident (service-residents via Feign) ---
    private Long residentId;
    private String residentNom;
    private String residentEmail;
    private String residentTelephone;

    public ChargeResponseFullDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }

    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public BigDecimal getMontantPaye() { return montantPaye; }
    public void setMontantPaye(BigDecimal montantPaye) { this.montantPaye = montantPaye; }

    public StatutCharge getStatut() { return statut; }
    public void setStatut(StatutCharge statut) { this.statut = statut; }

    public String getApartmentNumero() { return apartmentNumero; }
    public void setApartmentNumero(String apartmentNumero) { this.apartmentNumero = apartmentNumero; }

    public Integer getEtage() { return etage; }
    public void setEtage(Integer etage) { this.etage = etage; }

    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }

    public Long getResidentId() { return residentId; }
    public void setResidentId(Long residentId) { this.residentId = residentId; }

    public String getResidentNom() { return residentNom; }
    public void setResidentNom(String residentNom) { this.residentNom = residentNom; }

    public String getResidentEmail() { return residentEmail; }
    public void setResidentEmail(String residentEmail) { this.residentEmail = residentEmail; }

    public String getResidentTelephone() { return residentTelephone; }
    public void setResidentTelephone(String residentTelephone) { this.residentTelephone = residentTelephone; }
}
