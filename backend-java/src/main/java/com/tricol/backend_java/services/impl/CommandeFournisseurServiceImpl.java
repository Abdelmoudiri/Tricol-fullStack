package com.tricol.backend_java.services.impl;


import com.tricol.backend_java.dto.CommandeFournisseurDTO;
import com.tricol.backend_java.entities.*;
import com.tricol.backend_java.entities.enums.StatutCommande;
import com.tricol.backend_java.entities.enums.TypeMouvement;
import com.tricol.backend_java.mappers.CommandeFournisseurMapper;
import com.tricol.backend_java.mappers.LigneCommandeFournisseurMapper;
import com.tricol.backend_java.mappers.MouvementStockMapper;
import com.tricol.backend_java.repositories.CommandeFournisseurRepository;
import com.tricol.backend_java.repositories.FournisseurRepository;
import com.tricol.backend_java.repositories.MouvementStockRepository;
import com.tricol.backend_java.repositories.ProduitRepository;
import com.tricol.backend_java.services.CommandeFournisseurService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeRepo;
    private final FournisseurRepository fournisseurRepo;
    private final ProduitRepository produitRepo;
    private final MouvementStockRepository mouvementRepo;

    private final CommandeFournisseurMapper commandeMapper;
    private final LigneCommandeFournisseurMapper ligneMapper;
    private final MouvementStockMapper mouvementMapper;

    @Override
    @Transactional
    public CommandeFournisseurDTO create(CommandeFournisseurDTO dto) {

        Fournisseur fournisseur = fournisseurRepo.findById(dto.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));

        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setFournisseur(fournisseur);
        commande.setStatut(StatutCommande.EN_ATTENTE);

        var lignes = dto.getLignesCommande().stream().map(ligneDto -> {
            Produit produit = produitRepo.findById(ligneDto.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
            LigneCommandeFournisseur ligne = ligneMapper.toEntity(ligneDto);
            ligne.setProduit(produit);
            ligne.setCommandeFournisseur(commande);
            return ligne;
        }).collect(Collectors.toList());

        commande.setLignesCommande(lignes);

        double total = lignes.stream()
                .mapToDouble(LigneCommandeFournisseur::getSousTotal)
                .sum();
        commande.setMontantTotal(total);

        CommandeFournisseur saved = commandeRepo.save(commande);
        return commandeMapper.toDto(saved);
    }

    @Override
    public CommandeFournisseurDTO update(Long id, CommandeFournisseurDTO dto) {
        return null;
    }

    @Override
    @Transactional
    public CommandeFournisseurDTO updateStatus(Long id, String statutStr) {
        CommandeFournisseur commande = commandeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        StatutCommande statut = StatutCommande.valueOf(statutStr.toUpperCase());
        commande.setStatut(statut);

        if (statut == StatutCommande.LIVREE) {
            // Génération automatique des mouvements de stock
            for (LigneCommandeFournisseur ligne : commande.getLignesCommande()) {
                Produit produit = ligne.getProduit();
                produit.setStockActuel(produit.getStockActuel() + ligne.getQuantite());
                produitRepo.save(produit);

                MouvementStock mouvement = MouvementStock.builder()
                        .type(TypeMouvement.ENTREE)
                        .produit(produit)
                        .quantite(ligne.getQuantite())
                        .coutUnitaire(ligne.getPrixUnitaire())
                        .commandeFournisseur(commande)
                        .build();
                mouvementRepo.save(mouvement);
            }
        }

        return commandeMapper.toDto(commandeRepo.save(commande));
    }

    @Override
    public CommandeFournisseurDTO getById(Long id) {
        return commandeMapper.toDto(
                commandeRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Commande non trouvée"))
        );
    }

    @Override
    public Page<CommandeFournisseurDTO> getAll(Pageable pageable) {
        return commandeRepo.findAll(pageable).map(commandeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        commandeRepo.deleteById(id);
    }
}
