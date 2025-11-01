package com.tricol.tricolV2.controller;

import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.entity.Fournisseur;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.service.FournisseurServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/api/v2/fournisseurs")
public class FournisseurController {

    private final FournisseurServiceImpl fournisseurService;
    public FournisseurController(FournisseurServiceImpl fournisseurService){
        this.fournisseurService =fournisseurService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO>getFournisseurById(@PathVariable("id") Long id){
       FournisseurDTO dto = fournisseurService.getFournisseurById(id)
               .orElseThrow(()->new NotFoundException("Fournisseur non trouvé avec l'id : " + id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<FournisseurDTO>addFournisseur(@Valid @RequestBody  FournisseurDTO fournisseurDTO){

        FournisseurDTO dto = fournisseurService.addFournisseur(fournisseurDTO);

        return ResponseEntity.status(201).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDTO>updateFournisseur(@PathVariable("id") Long id , @Valid @RequestBody FournisseurDTO fournisseurDTO){
        FournisseurDTO dto = fournisseurService.updateFournisseur(id, fournisseurDTO);

        return  ResponseEntity.ok().body(dto);
    }

}
