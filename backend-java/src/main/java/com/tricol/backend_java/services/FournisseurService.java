package com.tricol.backend_java.services;


import com.tricol.backend_java.dto.FournisseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FournisseurService {
    FournisseurDTO create(FournisseurDTO dto);
    FournisseurDTO update(Long id, FournisseurDTO dto);
    void delete(Long id);
    FournisseurDTO getById(Long id);
    Page<FournisseurDTO> getAll(Pageable pageable);
}
