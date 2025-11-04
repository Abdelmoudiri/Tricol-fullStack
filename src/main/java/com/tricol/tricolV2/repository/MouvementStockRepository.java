package com.tricol.tricolV2.repository;

import com.tricol.tricolV2.entity.MouvementStock;
import com.tricol.tricolV2.entity.enums.TypeMouvement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    Page<MouvementStock> findByProduit_Id(Long produitId, Pageable pageable);
    Page<MouvementStock> findByCommande_Id(Long commandeId, Pageable pageable);
    Page<MouvementStock> findByType(TypeMouvement type, Pageable pageable);

    boolean existsByCommande_Id(Long commandeId);
}
