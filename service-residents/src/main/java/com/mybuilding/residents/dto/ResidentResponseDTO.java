package com.mybuilding.residents.dto;

import com.mybuilding.residents.model.RoleResident;
import java.math.BigDecimal;

public class ResidentResponseDTO {

    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private RoleResident role;
    private BigDecimal solde;

    public ResidentResponseDTO() {}

    public ResidentResponseDTO(Long id, String nom, String email, String telephone, RoleResident role, BigDecimal solde) {
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

    public RoleResident getRole() { return role; }
    public void setRole(RoleResident role) { this.role = role; }

    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }
}
