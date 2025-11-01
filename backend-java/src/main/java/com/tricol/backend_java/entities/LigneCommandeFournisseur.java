package com.tricol.backend_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneCommandeFournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    @NotNull
    private CommandeFournisseur commandeFournisseur;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    @NotNull
    private Produit produit;

    @NotNull
    private Double quantite;

    private Double prixUnitaire;

    public Double getSousTotal() {
        return (prixUnitaire != null ? prixUnitaire : 0) * quantite;
    }
}
