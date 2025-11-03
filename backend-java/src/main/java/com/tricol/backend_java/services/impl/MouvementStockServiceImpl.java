package com.tricol.backend_java.services.impl;


import com.tricol.backend_java.dto.MouvementStockDTO;
import com.tricol.backend_java.entities.MouvementStock;
import com.tricol.backend_java.mappers.MouvementStockMapper;
import com.tricol.backend_java.repositories.MouvementStockRepository;
import com.tricol.backend_java.services.MouvementStockService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MouvementStockServiceImpl implements MouvementStockService {

    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;

    @Override
    public MouvementStockDTO create(MouvementStockDTO dto) {
        MouvementStock mouvement = mouvementStockMapper.toEntity(dto);
        return mouvementStockMapper.toDto(mouvementStockRepository.save(mouvement));
    }

    @Override
    public List<MouvementStockDTO> getByProduit(Long produitId) {
        return mouvementStockRepository.findByProduitId(produitId)
                .stream().map(mouvementStockMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MouvementStockDTO> getByType(String type) {
        return mouvementStockRepository.findByType(type)
                .stream().map(mouvementStockMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MouvementStockDTO> getByCommande(Long commandeId) {
        return mouvementStockRepository.findByCommandeFournisseurId(commandeId)
                .stream().map(mouvementStockMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MouvementStockDTO> gettAll(Pageable pageable) {

        return mouvementStockRepository.findAll(pageable).map(mouvementStockMapper::toDto);
    }
}
