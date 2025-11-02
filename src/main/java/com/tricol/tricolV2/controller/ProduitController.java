package com.tricol.tricolV2.controller;

import com.tricol.tricolV2.dto.ProduitDTO;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.service.ProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping
    public ResponseEntity<ProduitDTO> addProduit(@Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO dto = produitService.addProduit(produitDTO);
        return ResponseEntity.status(201).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> updateProduit(@PathVariable("id") Long id, @Valid @RequestBody ProduitDTO produitDTO) {
        ProduitDTO dto = produitService.updateProduit(id, produitDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduit(@PathVariable("id") Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.ok().body("Produit supprimé avec succès");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable("id") Long id) {
        ProduitDTO dto = produitService.getProduitById(id)
                .orElseThrow(() -> new NotFoundException("Produit non trouvé avec l'id : " + id));
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProduitDTO>> getAllProduits() {
        List<ProduitDTO> produits = produitService.getAllProduits();
        if (produits.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé");
        }
        return ResponseEntity.ok(produits);
    }

    // Pagination
    @GetMapping("/paged")
    public ResponseEntity<Page<ProduitDTO>> getProduits(Pageable pageable) {
        Page<ProduitDTO> page = produitService.getProduits(pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé");
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/searchByNom/{nom}")
    public ResponseEntity<Page<ProduitDTO>> searchByNom(@PathVariable("nom") String nom, Pageable pageable) {
        Page<ProduitDTO> page = produitService.searchByNom(nom, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé avec le nom : " + nom);
        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/searchByCategorie/{categorie}")
    public ResponseEntity<Page<ProduitDTO>> searchByCategorie(@PathVariable("categorie") String categorie, Pageable pageable) {
        Page<ProduitDTO> page = produitService.searchByCategorie(categorie, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé dans la catégorie : " + categorie);
        }
        return ResponseEntity.ok(page);
    }

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

    // Stock inférieur
    @GetMapping("/stock/lessThan/{stock}")
    public ResponseEntity<List<ProduitDTO>> searchByStockLessThan(@PathVariable("stock") BigDecimal stock) {
        List<ProduitDTO> list = produitService.searchByStockLessThan(stock);
        if (list.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé avec stock inférieur à : " + stock);
        }
        return ResponseEntity.ok(list);
    }

    // Stock supérieur
    @GetMapping("/stock/greaterThan/{stock}")
    public ResponseEntity<List<ProduitDTO>> searchByStockGreaterThan(@PathVariable("stock") BigDecimal stock) {
        List<ProduitDTO> list = produitService.searchByStockGreaterThan(stock);
        if (list.isEmpty()) {
            throw new NotFoundException("Aucun produit trouvé avec stock supérieur à : " + stock);
        }
        return ResponseEntity.ok(list);
    }
}
