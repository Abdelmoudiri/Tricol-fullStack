package com.tricol.tricolV2.controller;

import com.tricol.tricolV2.dto.MouvementStockDTO;
import com.tricol.tricolV2.entity.enums.TypeMouvement;
import com.tricol.tricolV2.service.MouvementStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/mouvements")
@Tag(name = "Mouvements de Stock", description = "API pour la consultation des mouvements de stock")
public class MouvementStockController {

    private final MouvementStockService mouvementService;

    public MouvementStockController(MouvementStockService mouvementService) {
        this.mouvementService = mouvementService;
    }

    @Operation(
            summary = "Récupérer tous les mouvements de stock avec pagination",
            description = "Permet de récupérer l'historique de tous les mouvements de stock (entrées et sorties) de manière paginée"
    )
    @GetMapping
    public Page<MouvementStockDTO> getPaged(@ParameterObject Pageable pageable) {
        return mouvementService.getPaged(pageable);
    }

    @Operation(
            summary = "Récupérer les mouvements de stock par produit",
            description = "Permet de consulter l'historique des mouvements de stock pour un produit spécifique avec pagination"
    )
    @GetMapping("/by-produit")
    public Page<MouvementStockDTO> byProduit(@RequestParam Long produitId, @ParameterObject Pageable pageable) {
        return mouvementService.getByProduit(produitId, pageable);
    }

    @Operation(
            summary = "Récupérer les mouvements de stock par commande",
            description = "Permet de consulter tous les mouvements de stock associés à une commande fournisseur spécifique avec pagination"
    )
    @GetMapping("/by-commande")
    public Page<MouvementStockDTO> byCommande(@RequestParam Long commandeId, @ParameterObject Pageable pageable) {
        return mouvementService.getByCommande(commandeId, pageable);
    }

    @Operation(
            summary = "Récupérer les mouvements de stock par type",
            description = "Permet de filtrer les mouvements de stock par type (ENTREE ou SORTIE) avec pagination"
    )
    @GetMapping("/by-type")
    public Page<MouvementStockDTO> byType(@RequestParam TypeMouvement type, @ParameterObject Pageable pageable) {
        return mouvementService.getByType(type, pageable);
    }
}

