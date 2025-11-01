package com.tricol.backend_java.dto;


import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandeFournisseurDTO {
    private Long id;
    private LocalDate dateCommande;
    private String statut;
    private Double montantTotal;
    private Long fournisseurId;
    private String fournisseurSociete;
    private List<LigneCommandeFournisseurDTO> lignesCommande;
}
