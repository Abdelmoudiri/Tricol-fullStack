package com.tricol.tricolV2.integration.controller;

import com.tricol.tricolV2.controller.ProduitController;
import com.tricol.tricolV2.dto.ProduitDTO;
import com.tricol.tricolV2.service.ProduitServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProduitController.class)
public class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduitServiceImpl produitService;

    @Test
    void getAll_produits_returns_produit_list() throws Exception {
        // Arrange
        ProduitDTO produit1 = new ProduitDTO();
        produit1.setId(1L);
        produit1.setNom("Produit 1");
        produit1.setPrixUnitaire(new BigDecimal("100.00"));

        ProduitDTO produit2 = new ProduitDTO();
        produit2.setId(2L);
        produit2.setNom("Produit 2");
        produit2.setPrixUnitaire(new BigDecimal("200.00"));

        List<ProduitDTO> produits = Arrays.asList(produit1, produit2);
        when(produitService.getAllProduits()).thenReturn(produits);

        // Act & Assert
        mockMvc.perform(get("/api/v2/produits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nom").value("Produit 1"))
                .andExpect(jsonPath("$[1].nom").value("Produit 2"));
    }

    @Test
    void getById_produit_returns_produit() throws Exception {
        // Arrange
        ProduitDTO produit = new ProduitDTO();
        produit.setId(1L);
        produit.setNom("Produit Test");
        produit.setDescription("Description Test");
        produit.setPrixUnitaire(new BigDecimal("150.00"));

        when(produitService.getProduitById(1L)).thenReturn(Optional.of(produit));

        // Act & Assert
        mockMvc.perform(get("/api/v2/produits/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Produit Test"))
                .andExpect(jsonPath("$.description").value("Description Test"))
                .andExpect(jsonPath("$.prixUnitaire").value(150.00));
    }

    @Test
    void create_produit_returns_created_produit() throws Exception {
        // Arrange
        ProduitDTO produit = new ProduitDTO();
        produit.setId(1L);
        produit.setNom("Nouveau Produit");
        produit.setDescription("Nouvelle Description");
        produit.setCategorie("Catégorie Test");
        produit.setPrixUnitaire(new BigDecimal("250.00"));
        produit.setStockActuel(new BigDecimal("100.00"));

        when(produitService.addProduit(org.mockito.ArgumentMatchers.any(ProduitDTO.class)))
                .thenReturn(produit);

        String produitJson = """
                {
                    "nom": "Nouveau Produit",
                    "description": "Nouvelle Description",
                    "categorie": "Catégorie Test",
                    "prixUnitaire": 250.00,
                    "stockActuel": 100.00
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/v2/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produitJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nom").value("Nouveau Produit"))
                .andExpect(jsonPath("$.prixUnitaire").value(250.00));
    }
}
