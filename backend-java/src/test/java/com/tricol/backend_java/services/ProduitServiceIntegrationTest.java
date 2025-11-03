package com.tricol.backend_java.services;


import com.tricol.backend_java.dto.ProduitDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
@Transactional
public class ProduitServiceIntegrationTest {

    @Autowired
    private ProduitService produitService;

    @Test
    void ajouterProduitTest()
    {
        ProduitDTO dto=new ProduitDTO();
        dto.setNom("p1");
        dto.setPrixUnitaire(15.20);
        dto.setStockActuel(15.0);

        ProduitDTO result=produitService.create(dto);
        assertNotNull(result);
        assertEquals("p1",result.getNom());
    }

    @Test
    void afficherProduitTest()
    {
        ProduitDTO dto1=new ProduitDTO();
        dto1.setNom("p1");
        dto1.setPrixUnitaire(15.20);
        dto1.setStockActuel(15.0);

        ProduitDTO dto2=new ProduitDTO();
        dto2.setNom("p2");
        dto2.setPrixUnitaire(15.20);
        dto2.setStockActuel(15.0);

        var produits=produitService.getAll(Pageable.unpaged());

        assertNotNull(produits);
        //assertEquals(2,produits.Size());


    }
}
