package com.tricol.backend_java.mappers;

import com.tricol.backend_java.dto.CommandeFournisseurDTO;
import com.tricol.backend_java.entities.CommandeFournisseur;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { LigneCommandeFournisseurMapper.class })
public interface CommandeFournisseurMapper {

    @Mapping(source = "fournisseur.id", target = "fournisseurId")
    @Mapping(source = "fournisseur.societe", target = "fournisseurSociete")
    CommandeFournisseurDTO toDto(CommandeFournisseur entity);

    @Mapping(source = "fournisseurId", target = "fournisseur.id")
    CommandeFournisseur toEntity(CommandeFournisseurDTO dto);
}