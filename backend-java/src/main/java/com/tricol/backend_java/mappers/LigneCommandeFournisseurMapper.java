package com.tricol.backend_java.mappers;

import com.tricol.backend_java.dto.LigneCommandeFournisseurDTO;
import com.tricol.backend_java.entities.LigneCommandeFournisseur;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LigneCommandeFournisseurMapper {

    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "produit.nom", target = "produitNom")
    LigneCommandeFournisseurDTO toDto(LigneCommandeFournisseur entity);

    @Mapping(source = "produitId", target = "produit.id")
    LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDTO dto);
}