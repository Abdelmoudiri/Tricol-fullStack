package com.tricol.tricolV2.service;

import com.tricol.tricolV2.entity.CommandeFournisseur;
import com.tricol.tricolV2.entity.LigneCommandeFournisseur;
import com.tricol.tricolV2.entity.Produit;
import com.tricol.tricolV2.entity.enums.TypeMouvement;
import com.tricol.tricolV2.exception.BusinessException;
import com.tricol.tricolV2.repository.MouvementStockRepository;
import com.tricol.tricolV2.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MouvementStockServiceImplTest {

    @Mock
    MouvementStockRepository mouvementRepo;
    @Mock
    ProduitRepository produitRepo;

    @InjectMocks
    MouvementStockServiceImpl service;

    @Test
    void createEntriesForCommande_should_throw_when_insufficient_stock() {
        Produit p = new Produit();
        p.setNom("X");
        p.setStockActuel(new BigDecimal("0"));

        LigneCommandeFournisseur l = new LigneCommandeFournisseur();
        l.setProduit(p);
        l.setQuantite(new BigDecimal("5"));

        CommandeFournisseur c = new CommandeFournisseur();
        c.setId(1L);
        c.setLignes(List.of(l));

        when(produitRepo.findByNomIgnoreCaseAndStockActuelGreaterThanOrderByStockActuelDesc(eq("X"), any()))
                .thenReturn(List.of());

        assertThrows(BusinessException.class, () -> service.createEntriesForCommande(c));
        verifyNoInteractions(mouvementRepo);
    }

    @Test
    void createEntriesForCommande_should_create_movements_and_update_stock() {
        Produit pReq = new Produit();
        pReq.setNom("X");
        LigneCommandeFournisseur l = new LigneCommandeFournisseur();
        l.setProduit(pReq);
        l.setQuantite(new BigDecimal("7"));

        CommandeFournisseur c = new CommandeFournisseur();
        c.setId(10L);
        c.setLignes(List.of(l));

        Produit stockA = new Produit();
        stockA.setId(1L);
        stockA.setNom("X");
        stockA.setStockActuel(new BigDecimal("5"));
        stockA.setPrixUnitaire(new BigDecimal("2.00"));

        Produit stockB = new Produit();
        stockB.setId(2L);
        stockB.setNom("X");
        stockB.setStockActuel(new BigDecimal("5"));
        stockB.setPrixUnitaire(new BigDecimal("2.50"));

        when(produitRepo.findByNomIgnoreCaseAndStockActuelGreaterThanOrderByStockActuelDesc(eq("X"), any()))
                .thenReturn(List.of(stockB, stockA));

        service.createEntriesForCommande(c);

        verify(mouvementRepo, times(2)).save(any());
        ArgumentCaptor<Produit> produitCaptor = ArgumentCaptor.forClass(Produit.class);
        verify(produitRepo, atLeast(2)).save(produitCaptor.capture());
        assertThat(produitCaptor.getAllValues())
                .extracting(Produit::getStockActuel)
                .containsExactlyInAnyOrder(new BigDecimal("0"), new BigDecimal("3"));
    }
}
