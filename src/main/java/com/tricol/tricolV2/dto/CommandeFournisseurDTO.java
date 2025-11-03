package com.tricol.tricolV2.dto;

import com.tricol.tricolV2.entity.enums.StatutCommande;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandeFournisseurDTO {

    private Long id;

    @NotNull(message = "La date de commande est obligatoire")
    @PastOrPresent(message = "La date de commande doit être dans le passé ou aujourd’hui")
    private LocalDateTime dateCommande;

    @NotNull(message = "Le statut de la commande est obligatoire")
    private StatutCommande statut;

    private BigDecimal montantTotal;

    @NotNull(message = "Le fournisseur est obligatoire")
    private Long fournisseurId;

    @NotEmpty(message = "La commande doit contenir au moins une ligne")
    private List<LigneCommandeDTO> lignes;
}
