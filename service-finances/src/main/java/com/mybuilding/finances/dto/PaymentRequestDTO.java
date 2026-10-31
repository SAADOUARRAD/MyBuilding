package com.mybuilding.finances.dto;

import com.mybuilding.finances.model.MoyenPaiement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentRequestDTO {

    @NotNull(message = "L'identifiant de la charge est obligatoire (RM-PAY-01)")
    private Long chargeId;

    @NotNull(message = "La date de paiement est obligatoire")
    @PastOrPresent(message = "La date de paiement ne peut pas être dans le futur")
    private LocalDate date;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être strictement positif (RM-PAY-02)")
    private BigDecimal montant;

    @NotNull(message = "Le moyen de paiement est obligatoire (VIREMENT, ESPECES, CHEQUE, CARTE)")
    private MoyenPaiement moyen;

    public PaymentRequestDTO() {}

    public PaymentRequestDTO(Long chargeId, LocalDate date, BigDecimal montant, MoyenPaiement moyen) {
        this.chargeId = chargeId;
        this.date = date;
        this.montant = montant;
        this.moyen = moyen;
    }

    public Long getChargeId() { return chargeId; }
    public void setChargeId(Long chargeId) { this.chargeId = chargeId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public MoyenPaiement getMoyen() { return moyen; }
    public void setMoyen(MoyenPaiement moyen) { this.moyen = moyen; }
}
