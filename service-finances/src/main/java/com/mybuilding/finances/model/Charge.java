package com.mybuilding.finances.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "charges", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"apartmentId", "periode"})
})
public class Charge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'appartement est obligatoire")
    @Column(nullable = false)
    private Long apartmentId;

    @NotNull(message = "La période est obligatoire")
    @Column(nullable = false)
    private String periode; // Format: YYYY-MM (ex: 2026-11)

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être strictement positif")
    @Column(nullable = false)
    private BigDecimal montant;

    @NotNull
    @Column(nullable = false)
    private BigDecimal montantPaye = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCharge statut = StatutCharge.EMISE;

    public Charge() {}

    public Charge(Long apartmentId, String periode, BigDecimal montant) {
        this.apartmentId = apartmentId;
        this.periode = periode;
        this.montant = montant;
        this.montantPaye = BigDecimal.ZERO;
        this.statut = StatutCharge.EMISE;
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

    public StatutCharge getStatut() { return statut; }
    public void setStatut(StatutCharge statut) { this.statut = statut; }
}
