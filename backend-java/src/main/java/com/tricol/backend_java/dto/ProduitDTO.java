package com.tricol.backend_java.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitDTO {
    private Long id;
    private String nom;
    private String description;
    private Double prixUnitaire;
    private String categorie;
    private Double stockActuel;
}
