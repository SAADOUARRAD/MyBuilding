package com.mybuilding.residents.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ApartmentRequestDTO {

    @NotBlank(message = "Le numéro de l'appartement est obligatoire")
    private String numero;

    @NotNull(message = "L'étage est obligatoire")
    private Integer etage;

    @NotNull(message = "La superficie est obligatoire")
    @Positive(message = "La superficie doit être strictement positive")
    private BigDecimal superficie;

    private Long residentId;

    public ApartmentRequestDTO() {}

    public ApartmentRequestDTO(String numero, Integer etage, BigDecimal superficie, Long residentId) {
        this.numero = numero;
        this.etage = etage;
        this.superficie = superficie;
        this.residentId = residentId;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Integer getEtage() { return etage; }
    public void setEtage(Integer etage) { this.etage = etage; }

    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }

    public Long getResidentId() { return residentId; }
    public void setResidentId(Long residentId) { this.residentId = residentId; }
}
