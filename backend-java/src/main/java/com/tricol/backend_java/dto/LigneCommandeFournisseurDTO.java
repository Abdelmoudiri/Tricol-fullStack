package com.tricol.backend_java.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneCommandeFournisseurDTO {
    private Long id;
    private Long produitId;
    private String produitNom;
    private Double quantite;
    private Double prixUnitaire;
}
