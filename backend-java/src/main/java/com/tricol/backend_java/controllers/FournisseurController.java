package com.tricol.backend_java.controllers;



import com.tricol.backend_java.dto.FournisseurDTO;
import com.tricol.backend_java.exceptions.GlobalExceptionHandler;
import com.tricol.backend_java.exceptions.ResourceNotFoundException;
import com.tricol.backend_java.services.FournisseurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;


    @PostMapping
    public ResponseEntity<FournisseurDTO> create(@Valid @RequestBody FournisseurDTO dto) {

        if (fournisseurService.existsByEmail(dto.getEmail()))
        {
            throw new ResourceNotFoundException("le email deja trouve dans la base de donnée");
        }

        FournisseurDTO created = fournisseurService.create(dto);
        return ResponseEntity.created(URI.create("/api/fournisseurs/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDTO> update(@PathVariable Long id, @Validated @RequestBody FournisseurDTO dto) {
        return ResponseEntity.ok(fournisseurService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FournisseurDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(fournisseurService.getAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
