package com.tricol.tricolV2.controller;

import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.service.FournisseurServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/fournisseurs")
@Tag(name = "Fournisseurs", description = "API pour la gestion des fournisseurs")
public class FournisseurController {

    private final FournisseurServiceImpl fournisseurService;
    public FournisseurController(FournisseurServiceImpl fournisseurService){
        this.fournisseurService =fournisseurService;
    }

    @Operation(
            summary = "Récupérer un fournisseur par son ID",
            description = "Permet de récupérer les détails complets d'un fournisseur en utilisant son identifiant"
    )
    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO>getFournisseurById(@PathVariable("id") Long id){
       FournisseurDTO dto = fournisseurService.getFournisseurById(id)
               .orElseThrow(()->new NotFoundException("Fournisseur non trouvé avec l'id : " + id));
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Créer un nouveau fournisseur",
            description = "Permet de créer un nouveau fournisseur avec ses informations (nom, adresse, contact, ICE, etc.)"
    )
    @PostMapping
    public ResponseEntity<FournisseurDTO>addFournisseur(@Valid @RequestBody  FournisseurDTO fournisseurDTO){

        FournisseurDTO dto = fournisseurService.addFournisseur(fournisseurDTO);

        return ResponseEntity.status(201).body(dto);
    }

    @Operation(
            summary = "Modifier un fournisseur",
            description = "Permet de mettre à jour les informations d'un fournisseur existant"
    )
    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDTO>updateFournisseur(@PathVariable("id") Long id , @Valid @RequestBody FournisseurDTO fournisseurDTO){
        FournisseurDTO dto = fournisseurService.updateFournisseur(id, fournisseurDTO);
        return  ResponseEntity.ok().body(dto);
    }

    @Operation(
            summary = "Récupérer tous les fournisseurs",
            description = "Permet de récupérer la liste complète de tous les fournisseurs enregistrés"
    )
    @GetMapping
    public ResponseEntity<List<FournisseurDTO>>getAll(){
        List<FournisseurDTO> fournisseurs = fournisseurService.getAllFournisseurs();
        if(fournisseurs.isEmpty()){
            throw new NotFoundException("Aucun fournisseur trouvé");
        }
        return  ResponseEntity.ok(fournisseurs);
    }

    @Operation(
            summary = "Récupérer les fournisseurs avec pagination",
            description = "Permet de récupérer les fournisseurs de manière paginée avec contrôle du nombre d'éléments par page"
    )
    @GetMapping("/paged")
    public ResponseEntity<Map<String, Object>> getFournisseurs(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FournisseurDTO> fournisseurPage = fournisseurService.getFournisseurs(pageable);

        if (fournisseurPage.isEmpty()) {
            throw new NotFoundException("Aucun fournisseur trouvé");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", fournisseurPage.getContent());
        response.put("currentPage", fournisseurPage.getNumber());
        response.put("totalItems", fournisseurPage.getTotalElements());
        response.put("totalPages", fournisseurPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Rechercher les fournisseurs par société",
            description = "Permet de rechercher les fournisseurs en filtrant par le nom de la société"
    )
    @GetMapping("/searchBySociete/{societe}")
    public ResponseEntity<List<FournisseurDTO>>getFournisseursBysociete(@PathVariable("societe") String societe){
        List<FournisseurDTO>fournisseurDTOList = fournisseurService.searchBySociete(societe);

        if(fournisseurDTOList.isEmpty()) {
            throw new NotFoundException("Aucun fournisseur trouvé pour la société : " + societe);
        }

        return ResponseEntity.ok(fournisseurDTOList);
    }

    @Operation(
            summary = "Rechercher les fournisseurs par ville",
            description = "Permet de rechercher les fournisseurs en filtrant par la ville"
    )
    @GetMapping("/searchByVille/{ville}")
    public ResponseEntity<List<FournisseurDTO>>getFourinsseursByVille(@PathVariable("ville") String ville){
        List<FournisseurDTO> fournisseurDTOList = fournisseurService.searchByVille(ville);

        if(fournisseurDTOList.isEmpty()){
            throw new NotFoundException("Aucun fournisseur trouvé pour la ville : " + ville);
        }
        return ResponseEntity.ok(fournisseurDTOList);
    }

    @Operation(
            summary = "Rechercher un fournisseur par ICE",
            description = "Permet de rechercher un fournisseur unique en utilisant son numéro ICE (Identifiant Commun de l'Entreprise)"
    )
    @GetMapping("/searchByIce/{ice}")
    public ResponseEntity<FournisseurDTO>getFournisseurByIce(@PathVariable("ice") String ice){
        FournisseurDTO fournisseurDTO = fournisseurService.searchByIce(ice)
                .orElseThrow(()->new NotFoundException("Aucun fournisseur trouvé pour la ICE : " + ice));
        return ResponseEntity.ok(fournisseurDTO);
    }

    @Operation(
            summary = "Supprimer un fournisseur",
            description = "Permet de supprimer définitivement un fournisseur de la base de données"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        fournisseurService.deleteFournisseur(id);
        String message = "Fournisseur supprimé avec succès";
        return ResponseEntity.ok().body(Map.of("message" , message));
    }


}
