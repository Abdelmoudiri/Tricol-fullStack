package com.tricol.backend_java.dto;


import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MouvementStockDTO {
    private Long id;
    private LocalDateTime dateMouvement;
    private String type;
    private Double quantite;
    private Double coutUnitaire;
    private Long produitId;
    private String produitNom;
    private Long commandeId;
}
