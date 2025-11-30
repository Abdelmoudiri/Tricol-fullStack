package com.tricol.backend_java.services;


import com.tricol.backend_java.dto.FournisseurDTO;
import com.tricol.backend_java.dto.ProduitDTO;
import com.tricol.backend_java.entities.Fournisseur;
import com.tricol.backend_java.mappers.FournisseurMapper;
import com.tricol.backend_java.repositories.FournisseurRepository;
import com.tricol.backend_java.services.impl.FournisseurServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("dev")
@Transactional
class FournisseurServiceIntegrationTest {

    @Autowired
    private FournisseurService fournisseurService;

    @Test
    void testAjouterFournisseur() {

        FournisseurDTO dto = new FournisseurDTO();
        dto.setSociete("hamama");
        dto.setVille("safi");
        dto.setIce("hhhh");

        FournisseurDTO result = fournisseurService.create(dto);

        assertNotNull(result);
        assertEquals("hamama", result.getSociete());
    }

}


