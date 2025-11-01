package com.tricol.tricolV2.controller;

import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.service.FournisseurServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
