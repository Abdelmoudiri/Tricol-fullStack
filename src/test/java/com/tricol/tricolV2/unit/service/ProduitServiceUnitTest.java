package com.tricol.tricolV2.unit.service;

import com.tricol.tricolV2.config.AppProperties;
import com.tricol.tricolV2.dto.ProduitDTO;
import com.tricol.tricolV2.entity.Produit;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.mapper.ProduitMapper;
import com.tricol.tricolV2.repository.MouvementStockRepository;
import com.tricol.tricolV2.repository.ProduitRepository;
import com.tricol.tricolV2.service.ProduitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProduitServiceUnitTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private MouvementStockRepository mouvementStockRepository;

    @Mock
    private ProduitMapper produitMapper;

    @Mock
    private AppProperties appProperties;

    @InjectMocks
    private ProduitServiceImpl produitService;

    private ProduitDTO produitDTO;
    private Produit produit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produitDTO = new ProduitDTO();
        produitDTO.setId(1L);
        produitDTO.setNom("Produit Test");
        produitDTO.setDescription("Description Test");
        produitDTO.setCategorie("Catégorie Test");
        produitDTO.setPrixUnitaire(new BigDecimal("100.00"));
        produitDTO.setStockActuel(new BigDecimal("50.00"));

        produit = new Produit();
        produit.setId(1L);
        produit.setNom("Produit Test");
        produit.setDescription("Description Test");
        produit.setCategorie("Catégorie Test");
        produit.setPrixUnitaire(new BigDecimal("100.00"));
        produit.setStockActuel(new BigDecimal("50.00"));
    }

    @Test
    public void create_produit_success_returns_produitDTO() {
        // Arrange
        when(produitMapper.toEntity(produitDTO)).thenReturn(produit);
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);
        when(produitMapper.toDTO(produit)).thenReturn(produitDTO);
        when(appProperties.getValuationMethod()).thenReturn(AppProperties.ValuationMethod.CUMP);

        // Act
        ProduitDTO result = produitService.addProduit(produitDTO);

        // Assert
        assertNotNull(result);
        assertEquals(produitDTO.getId(), result.getId());
        assertEquals(produitDTO.getNom(), result.getNom());
        assertEquals(produitDTO.getPrixUnitaire(), result.getPrixUnitaire());
        verify(produitRepository, times(2)).save(any(Produit.class)); 
        verify(produitMapper, times(1)).toEntity(produitDTO);
        verify(produitMapper, times(1)).toDTO(produit);
        verify(mouvementStockRepository, times(1)).save(any());
    }

    @Test
    public void delete_produit_success_deletes_produit() {
        // Arrange
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        doNothing().when(produitRepository).delete(produit);

        // Act
        produitService.deleteProduit(1L);

        // Assert
        verify(produitRepository, times(1)).findById(1L);
        verify(produitRepository, times(1)).delete(produit);
    }

    @Test
    public void delete_produit_notFound_throws_NotFoundException() {
        // Arrange
        when(produitRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> produitService.deleteProduit(1L));
        verify(produitRepository, times(1)).findById(1L);
        verify(produitRepository, never()).delete(any());
    }

    @Test
    public void getAll_produits_success_returns_list() {
        // Arrange
        Produit produit2 = new Produit();
        produit2.setId(2L);
        produit2.setNom("Produit Test 2");

        ProduitDTO produitDTO2 = new ProduitDTO();
        produitDTO2.setId(2L);
        produitDTO2.setNom("Produit Test 2");

        List<Produit> produits = Arrays.asList(produit, produit2);
        
        when(produitRepository.findAll()).thenReturn(produits);
        when(produitMapper.toDTO(produit)).thenReturn(produitDTO);
        when(produitMapper.toDTO(produit2)).thenReturn(produitDTO2);

        // Act
        List<ProduitDTO> result = produitService.getAllProduits();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(produitDTO.getNom(), result.get(0).getNom());
        assertEquals(produitDTO2.getNom(), result.get(1).getNom());
        verify(produitRepository, times(1)).findAll();
        verify(produitMapper, times(2)).toDTO(any(Produit.class));
    }
}
