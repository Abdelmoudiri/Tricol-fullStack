package com.tricol.tricolV2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String adresse;

    @Column(length = 150)
    private String societe;

    @Column(length = 100)
    private String contact;

    @Column
    private String email;

    @Column(length = 15)
    private String telephone;

    @Column
    private String ville;

    @Column(length = 15, unique = true, nullable = false)
    private String ice;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeFournisseur> commandes;
}
