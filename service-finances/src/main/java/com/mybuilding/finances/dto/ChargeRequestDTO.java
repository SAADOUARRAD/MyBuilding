package com.mybuilding.finances.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ChargeRequestDTO {

    @NotNull(message = "L'appartement est obligatoire (RM-CHG-01)")
    private Long apartmentId;

    @NotBlank(message = "La période est obligatoire")
    @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "La période doit être au format YYYY-MM (ex: 2026-11)")
    private String periode;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être strictement positif (RM-CHG-05)")
    private BigDecimal montant;

    public ChargeRequestDTO() {}

    public ChargeRequestDTO(Long apartmentId, String periode, BigDecimal montant) {
        this.apartmentId = apartmentId;
        this.periode = periode;
        this.montant = montant;
    }

    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }

    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }
}
