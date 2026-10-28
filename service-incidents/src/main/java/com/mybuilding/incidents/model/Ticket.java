package com.mybuilding.incidents.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'appartement est obligatoire")
    @Column(nullable = false)
    private Long apartmentId;

    @NotBlank(message = "La description est obligatoire")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull(message = "La priorité est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioriteTicket priorite;

    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutTicket statut = StatutTicket.OUVERT;

    public Ticket() {}

    public Ticket(Long apartmentId, String description, PrioriteTicket priorite) {
        this.apartmentId = apartmentId;
        this.description = description;
        this.priorite = priorite;
        this.statut = StatutTicket.OUVERT;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApartmentId() { return apartmentId; }
    public void setApartmentId(Long apartmentId) { this.apartmentId = apartmentId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public PrioriteTicket getPriorite() { return priorite; }
    public void setPriorite(PrioriteTicket priorite) { this.priorite = priorite; }

    public StatutTicket getStatut() { return statut; }
    public void setStatut(StatutTicket statut) { this.statut = statut; }
}
