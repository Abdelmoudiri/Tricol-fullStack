package com.tricol.backend_java.services.impl;


import com.tricol.backend_java.dto.FournisseurDTO;
import com.tricol.backend_java.entities.Fournisseur;
import com.tricol.backend_java.mappers.FournisseurMapper;
import com.tricol.backend_java.repositories.FournisseurRepository;
import com.tricol.backend_java.services.FournisseurService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;

    @Override
    public FournisseurDTO create(FournisseurDTO dto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(dto);
        return fournisseurMapper.toDto(fournisseurRepository.save(fournisseur));
    }

    @Override
    public FournisseurDTO update(Long id, FournisseurDTO dto) {
        Fournisseur existing = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        existing.setSociete(dto.getSociete());
        existing.setAdresse(dto.getAdresse());
        existing.setContact(dto.getContact());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        existing.setVille(dto.getVille());
        existing.setIce(dto.getIce());
        return fournisseurMapper.toDto(fournisseurRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        fournisseurRepository.deleteById(id);
    }

    @Override
    public FournisseurDTO getById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        return fournisseurMapper.toDto(fournisseur);
    }

    @Override
    public Page<FournisseurDTO> getAll(Pageable pageable) {
        return fournisseurRepository.findAll(pageable).map(fournisseurMapper::toDto);
    }
}
