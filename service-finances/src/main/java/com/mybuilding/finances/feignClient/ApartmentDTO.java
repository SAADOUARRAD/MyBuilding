package com.mybuilding.finances.feignClient;

import java.math.BigDecimal;

public class ApartmentDTO {
    private Long id;
    private String numero;
    private Integer etage;
    private BigDecimal superficie;
    private Long residentId;

    public ApartmentDTO() {}

    public ApartmentDTO(Long id, String numero, Integer etage, BigDecimal superficie, Long residentId) {
        this.id = id;
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
