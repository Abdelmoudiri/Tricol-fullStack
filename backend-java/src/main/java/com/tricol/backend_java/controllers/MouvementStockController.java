package com.tricol.backend_java.controllers;

import com.tricol.backend_java.dto.MouvementStockDTO;
import com.tricol.backend_java.services.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mouvements-stock")
public class MouvementStockController {

    @Autowired
    private MouvementStockService mouvementStockService;

    @GetMapping
    public ResponseEntity<Page<MouvementStockDTO>> getAll(Pageable pageable,
                                                          @RequestParam(required = false) String type,
                                                          @RequestParam(required = false) Long produitId,
                                                          @RequestParam(required = false) Long commandeId)
    {
        return ResponseEntity.ok(mouvementStockService.gettAll(pageable));
    }
}
