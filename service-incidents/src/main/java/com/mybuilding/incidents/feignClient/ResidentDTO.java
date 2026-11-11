package com.mybuilding.incidents.feignClient;

import java.math.BigDecimal;

public class ResidentDTO {
    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private String role;
    private BigDecimal solde;

    public ResidentDTO() {}

    public ResidentDTO(Long id, String nom, String email, String telephone, String role, BigDecimal solde) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
        this.solde = solde;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }
}
