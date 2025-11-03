package com.tricol.backend_java.mappers;

import com.tricol.backend_java.dto.MouvementStockDTO;
import com.tricol.backend_java.entities.MouvementStock;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "produit.nom", target = "produitNom")
    @Mapping(source = "commandeFournisseur.id", target = "commandeId")
    MouvementStockDTO toDto(MouvementStock entity);

    @Mapping(source = "produitId", target = "produit.id")
    @Mapping(source = "commandeId", target = "commandeFournisseur.id")
    MouvementStock toEntity(MouvementStockDTO dto);
}