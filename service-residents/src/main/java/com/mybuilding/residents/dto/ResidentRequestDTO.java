package com.mybuilding.residents.dto;

import com.mybuilding.residents.model.RoleResident;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ResidentRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String telephone;

    @NotNull(message = "Le rôle est obligatoire (PROPRIETAIRE ou LOCATAIRE)")
    private RoleResident role;

    public ResidentRequestDTO() {}

    public ResidentRequestDTO(String nom, String email, String telephone, RoleResident role) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public RoleResident getRole() { return role; }
    public void setRole(RoleResident role) { this.role = role; }
}
