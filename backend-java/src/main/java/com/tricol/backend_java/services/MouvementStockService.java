package com.tricol.backend_java.services;

import com.tricol.backend_java.dto.MouvementStockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MouvementStockService {
    MouvementStockDTO create(MouvementStockDTO dto);
    List<MouvementStockDTO> getByProduit(Long produitId);
    List<MouvementStockDTO> getByType(String type);
    List<MouvementStockDTO> getByCommande(Long commandeId);
    Page<MouvementStockDTO> gettAll(Pageable pageable);
}