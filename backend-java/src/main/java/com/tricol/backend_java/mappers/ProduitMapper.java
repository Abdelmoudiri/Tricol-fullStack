package com.tricol.backend_java.mappers;

import com.tricol.backend_java.dto.ProduitDTO;
import com.tricol.backend_java.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitMapper INSTANCE = Mappers.getMapper(ProduitMapper.class);
    ProduitDTO toDto(Produit entity);
    Produit toEntity(ProduitDTO dto);
}