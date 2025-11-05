package com.tricol.tricolV2.controller;

import com.tricol.tricolV2.dto.CommandeFournisseurDTO;
import com.tricol.tricolV2.entity.enums.StatutCommande;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.service.CommandeFournisseurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/commandes-fournisseur")
@Tag(name = "Commandes Fournisseur", description = "API pour la gestion des commandes fournisseurs")
public class CommandeFournisseure {

    private final CommandeFournisseurService service;

    public CommandeFournisseure(CommandeFournisseurService service) {
        this.service = service;
    }

    @Operation(
            summary = "Créer une nouvelle commande fournisseur",
            description = "Permet de créer une nouvelle commande fournisseur avec ses lignes de commande"
    )
    @PostMapping
    public ResponseEntity<CommandeFournisseurDTO> create(@Valid @RequestBody CommandeFournisseurDTO dto) {
        CommandeFournisseurDTO created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(
            summary = "Modifier une commande fournisseur",
            description = "Permet de mettre à jour les informations d'une commande fournisseur existante"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CommandeFournisseurDTO> update(@PathVariable("id") Long id,
                                                         @Valid @RequestBody CommandeFournisseurDTO dto) {
        CommandeFournisseurDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Mettre à jour le statut d'une commande",
            description = "Permet de changer le statut d'une commande fournisseur (EN_ATTENTE, EN_COURS, LIVREE, ANNULEE)"
    )
    @PutMapping("/{id}/statut")
    public ResponseEntity<CommandeFournisseurDTO> updateStatut(@PathVariable("id") Long id,
                                                               @RequestParam("value") StatutCommande statut) {
        CommandeFournisseurDTO updated = service.updateStatut(id, statut);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Supprimer une commande fournisseur",
            description = "Permet de supprimer définitivement une commande fournisseur de la base de données"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Commande supprimée avec succès"));
    }

    @Operation(
            summary = "Récupérer une commande par son ID",
            description = "Permet de récupérer les détails complets d'une commande fournisseur en utilisant son identifiant"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CommandeFournisseurDTO> getById(@PathVariable("id") Long id) {
        CommandeFournisseurDTO dto = service.getById(id)
                .orElseThrow(() -> new NotFoundException("Commande non trouvée avec l'id : " + id));
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Récupérer toutes les commandes",
            description = "Permet de récupérer la liste complète de toutes les commandes fournisseurs"
    )
    @GetMapping
    public ResponseEntity<List<CommandeFournisseurDTO>> getAll() {
        List<CommandeFournisseurDTO> list = service.getAll();
        if (list.isEmpty()) {
            throw new NotFoundException("Aucune commande trouvée");
        }
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Récupérer les commandes avec pagination",
            description = "Permet de récupérer les commandes fournisseurs de manière paginée avec contrôle du nombre d'éléments par page"
    )
    @GetMapping("/paged")
    public ResponseEntity<Map<String, Object>> getPaged(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommandeFournisseurDTO> p = service.getPaged(pageable);
        if (p.isEmpty()) {
            throw new NotFoundException("Aucune commande trouvée");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", p.getContent());
        response.put("currentPage", p.getNumber());
        response.put("totalItems", p.getTotalElements());
        response.put("totalPages", p.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Rechercher les commandes par statut",
            description = "Permet de rechercher et filtrer les commandes fournisseurs selon leur statut avec pagination"
    )
    @GetMapping("/search/statut")
    public ResponseEntity<Page<CommandeFournisseurDTO>> searchByStatut(@RequestParam("value") StatutCommande statut,
                                                                       Pageable pageable) {
        Page<CommandeFournisseurDTO> p = service.searchByStatut(statut, pageable);
        if (p.isEmpty()) {
            throw new NotFoundException("Aucune commande trouvée pour le statut : " + statut);
        }
        return ResponseEntity.ok(p);
    }

    @Operation(
            summary = "Rechercher les commandes par fournisseur",
            description = "Permet de rechercher les commandes fournisseurs en filtrant par le nom de la société fournisseur"
    )
    @GetMapping("/search/fournisseur")
    public ResponseEntity<Page<CommandeFournisseurDTO>> searchByFournisseurSociete(@RequestParam("q") String societe,
                                                                                   Pageable pageable) {
        Page<CommandeFournisseurDTO> p = service.searchByFournisseurSociete(societe, pageable);
        if (p.isEmpty()) {
            throw new NotFoundException("Aucune commande trouvée pour le fournisseur : " + societe);
        }
        return ResponseEntity.ok(p);
    }

    @Operation(
            summary = "Rechercher les commandes par période",
            description = "Permet de rechercher les commandes fournisseurs créées entre deux dates avec pagination"
    )
    @GetMapping("/search/dateBetween")
    public ResponseEntity<Page<CommandeFournisseurDTO>> searchByDateBetween(@RequestParam("start") LocalDateTime start,
                                                                            @RequestParam("end") LocalDateTime end,
                                                                            Pageable pageable) {
        Page<CommandeFournisseurDTO> p = service.searchByDateBetween(start, end, pageable);
        if (p.isEmpty()) {
            throw new NotFoundException("Aucune commande trouvée dans cet intervalle de dates");
        }
        return ResponseEntity.ok(p);
    }
}
