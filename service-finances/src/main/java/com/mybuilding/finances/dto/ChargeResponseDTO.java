package com.mybuilding.finances.dto;

import com.mybuilding.finances.model.StatutCharge;
import java.math.BigDecimal;

public class ChargeResponseDTO {

    private Long id;
    private Long apartmentId;
    private String periode;
    private BigDecimal montant;
    private BigDecimal montantPaye;
    private BigDecimal resteAPayer;
    private StatutCharge statut;

    public ChargeResponseDTO() {}

    public ChargeResponseDTO(Long id, Long apartmentId, String periode, BigDecimal montant, BigDecimal montantPaye, BigDecimal resteAPayer, StatutCharge statut) {
        this.id = id;
        this.apartmentId = apartmentId;
        this.periode = periode;
        this.montant = montant;
        this.montantPaye = montantPaye;
        this.resteAPayer = resteAPayer;
        this.statut = statut;
    }

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

    public BigDecimal getResteAPayer() { return resteAPayer; }
    public void setResteAPayer(BigDecimal resteAPayer) { this.resteAPayer = resteAPayer; }

    public StatutCharge getStatut() { return statut; }
    public void setStatut(StatutCharge statut) { this.statut = statut; }
}
