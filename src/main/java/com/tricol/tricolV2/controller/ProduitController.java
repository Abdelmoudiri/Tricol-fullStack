package com.tricol.tricolV2.controller;

import com.tricol.tricolV2.dto.ProduitDTO;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/produits")
@Tag(name = "Produits", description = "API pour la gestion des produits et du stock")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @Operation(
            summary = "Créer un nouveau produit",
            description = "Permet de créer un nouveau produit avec ses informations (nom, description, prix, catégorie, stock initial)"
    )
    @PostMapping
    public ResponseEntity<ProduitDTO> addProduit(@Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO dto = produitService.addProduit(produitDTO);
        return ResponseEntity.status(201).body(dto);
    }

    @Operation(
            summary = "Modifier un produit",
            description = "Permet de mettre à jour les informations d'un produit existant"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> updateProduit(@PathVariable("id") Long id, @Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO dto = produitService.updateProduit(id, produitDTO);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Supprimer un produit",
            description = "Permet de supprimer définitivement un produit de la base de données"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduit(@PathVariable("id") Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.ok().body("Produit supprimé avec succès");
    }

    @Operation(
            summary = "Récupérer un produit par son ID",
            description = "Permet de récupérer les détails complets d'un produit en utilisant son identifiant"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable("id") Long id) {
        ProduitDTO dto = produitService.getProduitById(id)
                .orElseThrow(() -> new NotFoundException("Produit non trouvé avec l'id : " + id));
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Récupérer tous les produits",
            description = "Permet de récupérer la liste complète de tous les produits enregistrés"
    )
    @GetMapping
    public ResponseEntity<List<ProduitDTO>> getAllProduits() {
        List<ProduitDTO> produits = produitService.getAllProduits();
        if (produits.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé");
        }
        return ResponseEntity.ok(produits);
    }

    @Operation(
            summary = "Récupérer les produits avec pagination",
            description = "Permet de récupérer les produits de manière paginée avec contrôle du nombre d'éléments par page"
    )
    @GetMapping("/paged")
    public ResponseEntity<Page<ProduitDTO>> getProduits(Pageable pageable) {
        Page<ProduitDTO> page = produitService.getProduits(pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé");
        }
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Rechercher les produits par nom",
            description = "Permet de rechercher les produits en filtrant par le nom avec pagination"
    )
    @GetMapping("/searchByNom/{nom}")
    public ResponseEntity<Page<ProduitDTO>> searchByNom(@PathVariable("nom") String nom, Pageable pageable) {
        Page<ProduitDTO> page = produitService.searchByNom(nom, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé avec le nom : " + nom);
        }
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Rechercher les produits par catégorie",
            description = "Permet de rechercher les produits en filtrant par la catégorie avec pagination"
    )
    @GetMapping("/searchByCategorie/{categorie}")
    public ResponseEntity<Page<ProduitDTO>> searchByCategorie(@PathVariable("categorie") String categorie, Pageable pageable) {
        Page<ProduitDTO> page = produitService.searchByCategorie(categorie, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé dans la catégorie : " + categorie);
        }
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Rechercher les produits par fourchette de prix",
            description = "Permet de rechercher les produits dont le prix est compris entre deux valeurs avec pagination"
    )
    @GetMapping("/searchByPrix")
    public ResponseEntity<Page<ProduitDTO>> searchByPrix(@RequestParam BigDecimal min,
                                                         @RequestParam BigDecimal max,
                                                         Pageable pageable) {
        Page<ProduitDTO> page = produitService.searchByPrixBetween(min, max, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé dans cette fourchette de prix");
        }
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Rechercher les produits avec stock inférieur à une valeur",
            description = "Permet de rechercher les produits dont le stock actuel est inférieur à une valeur donnée (utile pour les alertes de réapprovisionnement)"
    )
    @GetMapping("/stock/lessThan/{stock}")
    public ResponseEntity<List<ProduitDTO>> searchByStockLessThan(@PathVariable("stock") BigDecimal stock) {
        List<ProduitDTO> list = produitService.searchByStockLessThan(stock);
        if (list.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé avec stock inférieur à : " + stock);
        }
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Rechercher les produits avec stock supérieur à une valeur",
            description = "Permet de rechercher les produits dont le stock actuel est supérieur à une valeur donnée"
    )
    @GetMapping("/stock/greaterThan/{stock}")
    public ResponseEntity<List<ProduitDTO>> searchByStockGreaterThan(@PathVariable("stock") BigDecimal stock) {
        List<ProduitDTO> list = produitService.searchByStockGreaterThan(stock);
        if (list.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé avec stock supérieur à : " + stock);
        }
        return ResponseEntity.ok(list);
    }
}
