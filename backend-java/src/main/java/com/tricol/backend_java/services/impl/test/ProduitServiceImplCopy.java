package com.tricol.backend_java.services.impl.test;


import com.tricol.backend_java.dto.ProduitDTO;
import com.tricol.backend_java.entities.Produit;
import com.tricol.backend_java.mappers.ProduitMapper;
import com.tricol.backend_java.repositories.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitServiceImplCopy  {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;




       public ProduitDTO getById(Long id) {
        return produitMapper.toDto(
                produitRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Produit non trouvé"))
        );
    }
Exception e;
    public List<Produit> getAll() {
        return produitRepository.findAll();
    }
}
