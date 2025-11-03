package com.tricol.backend_java.repositories;


import com.tricol.backend_java.entities.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    List<MouvementStock> findByProduitId(Long produitId);
    List<MouvementStock> findByType(String type);
    List<MouvementStock> findByCommandeFournisseurId(Long commandeId);
}
