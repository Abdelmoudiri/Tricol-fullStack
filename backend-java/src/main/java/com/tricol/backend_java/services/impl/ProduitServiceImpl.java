package com.tricol.backend_java.services.impl;


import com.tricol.backend_java.dto.ProduitDTO;
import com.tricol.backend_java.entities.Produit;
import com.tricol.backend_java.mappers.ProduitMapper;
import com.tricol.backend_java.repositories.ProduitRepository;
import com.tricol.backend_java.services.ProduitService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    @Override
    public ProduitDTO create(ProduitDTO dto) {
        Produit produit = produitMapper.toEntity(dto);
        produit.setStockActuel(dto.getStockActuel() != null ? dto.getStockActuel() : 0.0);
        return produitMapper.toDto(produitRepository.save(produit));
    }

    @Override
    public ProduitDTO update(Long id, ProduitDTO dto) {
        Produit existing = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        existing.setNom(dto.getNom());
        existing.setDescription(dto.getDescription());
        existing.setPrixUnitaire(dto.getPrixUnitaire());
        existing.setCategorie(dto.getCategorie());
        return produitMapper.toDto(produitRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public ProduitDTO getById(Long id) {
        return produitMapper.toDto(
                produitRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Produit non trouvé"))
        );
    }

    @Override
    public Page<ProduitDTO> getAll(Pageable pageable) {
        return produitRepository.findAll(pageable).map(produitMapper::toDto);
    }
}
