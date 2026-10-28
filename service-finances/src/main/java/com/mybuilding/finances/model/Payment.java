package com.mybuilding.finances.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La charge est obligatoire")
    @Column(nullable = false)
    private Long chargeId;

    @NotNull(message = "La date est obligatoire")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être strictement positif")
    @Column(nullable = false)
    private BigDecimal montant;

    @NotNull(message = "Le moyen de paiement est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MoyenPaiement moyen;

    public Payment() {}

    public Payment(Long chargeId, LocalDate date, BigDecimal montant, MoyenPaiement moyen) {
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
