package com.tricol.backend_java.controllers;


import com.tricol.backend_java.dto.CommandeFournisseurDTO;
import com.tricol.backend_java.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/commandes-fournisseurs")
public class CommandeFournisseurController {

    @Autowired
    private CommandeFournisseurService commandeFournisseurService;

    @PostMapping
    public ResponseEntity<CommandeFournisseurDTO> create(@Validated @RequestBody CommandeFournisseurDTO dto) {
        CommandeFournisseurDTO created = commandeFournisseurService.create(dto);
        return ResponseEntity.created(URI.create("/api/commandes-fournisseurs/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeFournisseurDTO> update(@PathVariable Long id, @Validated @RequestBody CommandeFournisseurDTO dto) {
        return ResponseEntity.ok(commandeFournisseurService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeFournisseurDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeFournisseurService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CommandeFournisseurDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(commandeFournisseurService.getAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commandeFournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<CommandeFournisseurDTO> updateStatut(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(commandeFournisseurService.updateStatus(id, statut));
    }
}
