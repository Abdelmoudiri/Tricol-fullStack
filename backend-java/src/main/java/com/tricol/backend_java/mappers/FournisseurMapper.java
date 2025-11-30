package com.tricol.backend_java.mappers;

import com.tricol.backend_java.dto.FournisseurDTO;
import com.tricol.backend_java.entities.Fournisseur;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {
    FournisseurMapper INSTANCE = Mappers.getMapper(FournisseurMapper.class);
    FournisseurDTO toDto(Fournisseur entity);
    Fournisseur toEntity(FournisseurDTO dto);
}