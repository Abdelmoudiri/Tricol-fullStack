package com.tricol.tricolV2.service;

import com.tricol.tricolV2.config.AppProperties;
import com.tricol.tricolV2.dto.ProduitDTO;
import com.tricol.tricolV2.entity.MouvementStock;
import com.tricol.tricolV2.entity.Produit;
import com.tricol.tricolV2.mapper.ProduitMapper;
import com.tricol.tricolV2.repository.MouvementStockRepository;
import com.tricol.tricolV2.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceImplTest {

    @Mock
    ProduitRepository produitRepository;
    @Mock
    MouvementStockRepository mouvementStockRepository;
    @Mock
    ProduitMapper produitMapper;
    @Mock
    AppProperties appProperties;

    @InjectMocks
    ProduitServiceImpl service;

    @BeforeEach
    void setup() {
        // no global stubbing to keep Mockito strictness clean per test
    }

    @Test
    void addProduit_with_initial_stock_should_create_mouvement_and_set_avg_cost() {
        when(appProperties.getValuationMethod()).thenReturn(AppProperties.ValuationMethod.FIFO);
        ProduitDTO input = new ProduitDTO(null, "P1", "desc", new BigDecimal("10.00"), "cat", new BigDecimal("5"), null);
        Produit entity = new Produit();
        entity.setPrixUnitaire(new BigDecimal("10.00"));
        entity.setStockActuel(new BigDecimal("5"));

        Produit saved = new Produit();
        saved.setId(1L);
        saved.setPrixUnitaire(new BigDecimal("10.00"));
        saved.setStockActuel(new BigDecimal("5"));

        when(produitMapper.toEntity(input)).thenReturn(entity);
        when(produitRepository.save(entity)).thenReturn(saved);
        when(produitMapper.toDTO(any(Produit.class))).thenAnswer(inv -> {
            Produit p = inv.getArgument(0);
            return new ProduitDTO(p.getId(), "P1", "desc", p.getPrixUnitaire(), "cat", p.getStockActuel(), p.getCoutUnitaireMoyen());
        });

        ProduitDTO result = service.addProduit(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(mouvementStockRepository).save(any(MouvementStock.class));
        verify(produitRepository, atLeastOnce()).save(any(Produit.class));
    }

    @Test
    void getProduits_should_map_page() {
        PageRequest pageable = PageRequest.of(0, 5);
        Produit p = new Produit();
        p.setId(3L);
        Page<Produit> page = new PageImpl<>(List.of(p));
        when(produitRepository.findAll(pageable)).thenReturn(page);
        when(produitMapper.toDTO(p)).thenReturn(new ProduitDTO());

        Page<?> res = service.getProduits(pageable);
        assertThat(res.getTotalElements()).isEqualTo(1);
    }
}