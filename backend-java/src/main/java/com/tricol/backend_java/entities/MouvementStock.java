package com.tricol.backend_java.entities;


import com.tricol.backend_java.entities.enums.TypeMouvement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateMouvement = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TypeMouvement type;

    private Double quantite;

    private Double coutUnitaire;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private CommandeFournisseur commandeFournisseur;
}
