package com.tricol.backend_java.services;

import com.tricol.backend_java.dto.ProduitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProduitService {
    ProduitDTO create(ProduitDTO dto);
    ProduitDTO update(Long id, ProduitDTO dto);
    void delete(Long id);
    ProduitDTO getById(Long id);
    Page<ProduitDTO> getAll(Pageable pageable);
}
