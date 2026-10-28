package com.mybuilding.residents.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le numéro est obligatoire")
    @Column(nullable = false, unique = true)
    private String numero;

    @NotNull(message = "L'étage est obligatoire")
    @Column(nullable = false)
    private Integer etage;

    @NotNull(message = "La superficie est obligatoire")
    @Positive(message = "La superficie doit être strictement positive")
    @Column(nullable = false)
    private BigDecimal superficie;

    private Long residentId;

    public Apartment() {}

    public Apartment(String numero, Integer etage, BigDecimal superficie, Long residentId) {
        this.numero = numero;
        this.etage = etage;
        this.superficie = superficie;
        this.residentId = residentId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Integer getEtage() { return etage; }
    public void setEtage(Integer etage) { this.etage = etage; }

    public BigDecimal getSuperficie() { return superficie; }
    public void setSuperficie(BigDecimal superficie) { this.superficie = superficie; }

    public Long getResidentId() { return residentId; }
    public void setResidentId(Long residentId) { this.residentId = residentId; }
}
