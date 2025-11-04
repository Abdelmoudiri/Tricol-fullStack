package com.tricol.tricolV2.service;

import com.tricol.tricolV2.dto.MouvementStockDTO;
import com.tricol.tricolV2.entity.CommandeFournisseur;
import com.tricol.tricolV2.entity.enums.TypeMouvement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MouvementStockService {
    void createEntriesForCommande(CommandeFournisseur commande);
    boolean movementsExistForCommande(Long commandeId);

    Page<MouvementStockDTO> getPaged(Pageable pageable);
    Page<MouvementStockDTO> getByProduit(Long produitId, Pageable pageable);
    Page<MouvementStockDTO> getByCommande(Long commandeId, Pageable pageable);
    Page<MouvementStockDTO> getByType(TypeMouvement type, Pageable pageable);
}
