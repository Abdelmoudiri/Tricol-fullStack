package com.tricol.tricolV2.dto;

import com.tricol.tricolV2.entity.enums.TypeMouvement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MouvementStockDTO {
    private Long id;
    private LocalDateTime dateMouvement;
    private TypeMouvement type;
    private BigDecimal quantite;
    private BigDecimal coutUnitaire;
    private Long produitId;
    private Long commandeId;
    private String commentaire;
}
