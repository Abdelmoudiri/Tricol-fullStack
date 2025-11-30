package com.tricol.backend_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    private String description;

    @NotNull
    private Double prixUnitaire;

    private String categorie;

    @Column(nullable = false)
    private Double stockActuel = 0.0;
}
