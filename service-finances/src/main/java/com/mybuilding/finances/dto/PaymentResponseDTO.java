package com.mybuilding.finances.dto;

import com.mybuilding.finances.model.MoyenPaiement;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponseDTO {

    private Long id;
    private Long chargeId;
    private LocalDate date;
    private BigDecimal montant;
    private MoyenPaiement moyen;

    public PaymentResponseDTO() {}

    public PaymentResponseDTO(Long id, Long chargeId, LocalDate date, BigDecimal montant, MoyenPaiement moyen) {
        this.id = id;
        this.chargeId = chargeId;
        this.date = date;
        this.montant = montant;
        this.moyen = moyen;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getChargeId() { return chargeId; }
    public void setChargeId(Long chargeId) { this.chargeId = chargeId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public MoyenPaiement getMoyen() { return moyen; }
    public void setMoyen(MoyenPaiement moyen) { this.moyen = moyen; }
}
