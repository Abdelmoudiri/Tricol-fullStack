package com.tricol.tricolV2.unit.service;

import com.tricol.tricolV2.dto.MouvementStockDTO;
import com.tricol.tricolV2.entity.CommandeFournisseur;
import com.tricol.tricolV2.entity.LigneCommandeFournisseur;
import com.tricol.tricolV2.entity.MouvementStock;
import com.tricol.tricolV2.entity.Produit;
import com.tricol.tricolV2.entity.enums.TypeMouvement;
import com.tricol.tricolV2.exception.BusinessException;
import com.tricol.tricolV2.repository.MouvementStockRepository;
import com.tricol.tricolV2.repository.ProduitRepository;
import com.tricol.tricolV2.service.MouvementStockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MouvementStockServiceUnitTest {

    @Mock
    private MouvementStockRepository mouvementStockRepository;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private MouvementStockServiceImpl mouvementStockService;

    private CommandeFournisseur commande;
    private Produit produit;
    private LigneCommandeFournisseur ligne;
    private MouvementStock mouvement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produit = new Produit();
        produit.setId(1L);
        produit.setNom("Produit Test");
        produit.setStockActuel(new BigDecimal("100.00"));
        produit.setPrixUnitaire(new BigDecimal("50.00"));

        ligne = new LigneCommandeFournisseur();
        ligne.setId(1L);
        ligne.setProduit(produit);
        ligne.setQuantite(new BigDecimal("10.00"));
        ligne.setPrixUnitaire(new BigDecimal("50.00"));

        commande = new CommandeFournisseur();
        commande.setId(1L);
        commande.setLignes(Arrays.asList(ligne));

        mouvement = MouvementStock.builder()
                .id(1L)
                .dateMouvement(LocalDateTime.now())
                .type(TypeMouvement.SORTIE)
                .quantite(new BigDecimal("10.00"))
                .coutUnitaire(new BigDecimal("50.00"))
                .produit(produit)
                .commande(commande)
                .commentaire("Test mouvement")
                .build();
    }

    @Test
    public void createEntriesForCommande_success_creates_mouvements() {
        // Arrange
        when(produitRepository.findByNomIgnoreCaseAndStockActuelGreaterThanOrderByStockActuelDesc(
                produit.getNom(), BigDecimal.ZERO))
                .thenReturn(Arrays.asList(produit));
        when(mouvementStockRepository.save(any(MouvementStock.class))).thenReturn(mouvement);
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);

        // Act
        mouvementStockService.createEntriesForCommande(commande);

        // Assert
        verify(produitRepository, times(1))
                .findByNomIgnoreCaseAndStockActuelGreaterThanOrderByStockActuelDesc(produit.getNom(), BigDecimal.ZERO);
        verify(mouvementStockRepository, times(1)).save(any(MouvementStock.class));
        verify(produitRepository, times(1)).save(any(Produit.class));
    }

    @Test
    public void createEntriesForCommande_insufficientStock_throws_BusinessException() {
        // Arrange
        produit.setStockActuel(new BigDecimal("5.00")); // Stock insuffisant
        when(produitRepository.findByNomIgnoreCaseAndStockActuelGreaterThanOrderByStockActuelDesc(
                produit.getNom(), BigDecimal.ZERO))
                .thenReturn(Arrays.asList(produit));

        // Act & Assert
        assertThrows(BusinessException.class, () -> 
            mouvementStockService.createEntriesForCommande(commande));
        
        verify(produitRepository, times(1))
                .findByNomIgnoreCaseAndStockActuelGreaterThanOrderByStockActuelDesc(produit.getNom(), BigDecimal.ZERO);
        verify(mouvementStockRepository, never()).save(any());
    }

    @Test
    public void getByCommande_success_returns_page() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<MouvementStock> mouvementPage = new PageImpl<>(Arrays.asList(mouvement));
        
        when(mouvementStockRepository.findByCommande_Id(1L, pageable)).thenReturn(mouvementPage);

        // Act
        Page<MouvementStockDTO> result = mouvementStockService.getByCommande(1L, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(TypeMouvement.SORTIE, result.getContent().get(0).getType());
        verify(mouvementStockRepository, times(1)).findByCommande_Id(1L, pageable);
    }

    @Test
    public void movementsExistForCommande_exists_returns_true() {
        // Arrange
        when(mouvementStockRepository.existsByCommande_Id(1L)).thenReturn(true);

        // Act
        boolean result = mouvementStockService.movementsExistForCommande(1L);

        // Assert
        assertTrue(result);
        verify(mouvementStockRepository, times(1)).existsByCommande_Id(1L);
    }
}
