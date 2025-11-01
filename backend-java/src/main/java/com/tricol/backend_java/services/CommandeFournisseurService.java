package com.tricol.backend_java.services;

import com.tricol.backend_java.dto.CommandeFournisseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommandeFournisseurService {
    CommandeFournisseurDTO create(CommandeFournisseurDTO dto);
    CommandeFournisseurDTO update(Long id,CommandeFournisseurDTO dto);
    CommandeFournisseurDTO updateStatus(Long id, String statut);
    CommandeFournisseurDTO getById(Long id);
    Page<CommandeFournisseurDTO> getAll(Pageable pageable);
    void delete(Long id);
}