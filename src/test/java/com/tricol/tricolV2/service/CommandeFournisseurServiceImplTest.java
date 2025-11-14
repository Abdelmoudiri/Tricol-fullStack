package com.tricol.tricolV2.service;

import com.tricol.tricolV2.dto.CommandeFournisseurDTO;
import com.tricol.tricolV2.dto.LigneCommandeDTO;
import com.tricol.tricolV2.entity.CommandeFournisseur;
import com.tricol.tricolV2.entity.Fournisseur;
import com.tricol.tricolV2.entity.LigneCommandeFournisseur;
import com.tricol.tricolV2.entity.Produit;
import com.tricol.tricolV2.entity.enums.StatutCommande;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.mapper.CommandeFournisseurMapper;
import com.tricol.tricolV2.mapper.LigneCommandeMapper;
import com.tricol.tricolV2.repository.CommandeFournisseurRepository;
import com.tricol.tricolV2.repository.FournisseurRepository;
import com.tricol.tricolV2.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandeFournisseurServiceImplTest {

    @Mock CommandeFournisseurRepository commandeRepo;
    @Mock FournisseurRepository fournisseurRepo;
    @Mock ProduitRepository produitRepo;
    @Mock CommandeFournisseurMapper commandeMapper;
    @Mock LigneCommandeMapper ligneMapper;
    @Mock MouvementStockService mouvementStockService;

    @InjectMocks
    CommandeFournisseurServiceImpl service;

    @Test
    void create_should_bind_products_compute_total_and_return_dto() {
        when(fournisseurRepo.findById(1L)).thenReturn(Optional.of(new Fournisseur()));

        LigneCommandeDTO l = LigneCommandeDTO.builder()
                .produitId(10L)
                .quantite(new BigDecimal("3"))
                .prixUnitaire(new BigDecimal("0")) // will be overridden by produit price
                .build();
        CommandeFournisseurDTO in = new CommandeFournisseurDTO(null, LocalDateTime.now(), StatutCommande.EN_ATTENTE, null, 1L, List.of(l));

        CommandeFournisseur entity = new CommandeFournisseur();
        LigneCommandeFournisseur le = new LigneCommandeFournisseur();
        le.setProduit(new Produit());
        le.getProduit().setId(10L);
        le.setQuantite(new BigDecimal("3"));
        entity.setLignes(List.of(le));
        when(commandeMapper.toEntity(in)).thenReturn(entity);

        Produit produit = new Produit();
        produit.setId(10L);
        produit.setPrixUnitaire(new BigDecimal("25.00"));
        when(produitRepo.findById(10L)).thenReturn(Optional.of(produit));

        when(commandeRepo.save(any(CommandeFournisseur.class))).thenAnswer(inv -> inv.getArgument(0));

        when(commandeMapper.toDTO(any(CommandeFournisseur.class))).thenAnswer(inv -> {
            CommandeFournisseur c = inv.getArgument(0);
            CommandeFournisseurDTO out = new CommandeFournisseurDTO();
            out.setMontantTotal(c.getMontantTotal());
            out.setStatut(c.getStatut());
            return out;
        });

        CommandeFournisseurDTO res = service.create(in);

        assertThat(res.getMontantTotal()).isEqualByComparingTo(new BigDecimal("75.00"));
    }

    @Test
    void updateStatut_to_livree_should_call_mouvement_service_when_needed() {
        CommandeFournisseur existing = new CommandeFournisseur();
        existing.setId(5L);
        existing.setStatut(StatutCommande.EN_ATTENTE);
        when(commandeRepo.findById(5L)).thenReturn(Optional.of(existing));
        when(mouvementStockService.movementsExistForCommande(5L)).thenReturn(false);
        when(commandeRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(commandeMapper.toDTO(any())).thenReturn(new CommandeFournisseurDTO());

        service.updateStatut(5L, StatutCommande.LIVREE);

        verify(mouvementStockService).createEntriesForCommande(existing);
        verify(commandeRepo).save(existing);
    }

    @Test
    void update_should_throw_when_not_found() {
        when(commandeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.update(99L, new CommandeFournisseurDTO()));
    }

    @Test
    void getPaged_should_map_page() {
        PageRequest pageable = PageRequest.of(0, 5);
        when(commandeRepo.findAll(pageable)).thenReturn(new PageImpl<>(List.of(new CommandeFournisseur())));
        when(commandeMapper.toDTO(any())).thenReturn(new CommandeFournisseurDTO());
        Page<CommandeFournisseurDTO> res = service.getPaged(pageable);
        assertThat(res.getTotalElements()).isEqualTo(1);
    }
}
