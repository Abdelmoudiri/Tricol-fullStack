package com.tricol.tricolV2.integration.controller;

import com.tricol.tricolV2.controller.MouvementStockController;
import com.tricol.tricolV2.dto.MouvementStockDTO;
import com.tricol.tricolV2.entity.enums.TypeMouvement;
import com.tricol.tricolV2.service.MouvementStockServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MouvementStockController.class)
public class MouvementStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MouvementStockServiceImpl mouvementStockService;

    @Test
    void getAll_mouvements_returns_mouvement_page() throws Exception {
        // Arrange
        MouvementStockDTO mouvement1 = MouvementStockDTO.builder()
                .id(1L)
                .type(TypeMouvement.ENTREE)
                .quantite(new BigDecimal("50.00"))
                .coutUnitaire(new BigDecimal("100.00"))
                .produitId(1L)
                .dateMouvement(LocalDateTime.now())
                .commentaire("Entrée initiale")
                .build();

        MouvementStockDTO mouvement2 = MouvementStockDTO.builder()
                .id(2L)
                .type(TypeMouvement.SORTIE)
                .quantite(new BigDecimal("20.00"))
                .coutUnitaire(new BigDecimal("100.00"))
                .produitId(1L)
                .dateMouvement(LocalDateTime.now())
                .commentaire("Sortie commande")
                .build();

        List<MouvementStockDTO> mouvements = Arrays.asList(mouvement1, mouvement2);
        Page<MouvementStockDTO> page = new PageImpl<>(mouvements, PageRequest.of(0, 10), 2);

        when(mouvementStockService.getPaged(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/v2/mouvements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].type").value("ENTREE"))
                .andExpect(jsonPath("$.content[1].type").value("SORTIE"))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void getByProduit_mouvements_returns_filtered_mouvements() throws Exception {
        // Arrange
        MouvementStockDTO mouvement = MouvementStockDTO.builder()
                .id(1L)
                .type(TypeMouvement.ENTREE)
                .quantite(new BigDecimal("100.00"))
                .coutUnitaire(new BigDecimal("50.00"))
                .produitId(1L)
                .dateMouvement(LocalDateTime.now())
                .commentaire("Entrée produit 1")
                .build();

        Page<MouvementStockDTO> page = new PageImpl<>(Arrays.asList(mouvement), PageRequest.of(0, 10), 1);

        when(mouvementStockService.getByProduit(eq(1L), any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/v2/mouvements/by-produit")
                        .param("produitId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].produitId").value(1))
                .andExpect(jsonPath("$.content[0].type").value("ENTREE"));
    }

    @Test
    void getByType_mouvements_returns_filtered_by_type() throws Exception {
        // Arrange
        MouvementStockDTO mouvement = MouvementStockDTO.builder()
                .id(1L)
                .type(TypeMouvement.SORTIE)
                .quantite(new BigDecimal("30.00"))
                .coutUnitaire(new BigDecimal("75.00"))
                .produitId(2L)
                .dateMouvement(LocalDateTime.now())
                .commentaire("Sortie stock")
                .build();

        Page<MouvementStockDTO> page = new PageImpl<>(Arrays.asList(mouvement), PageRequest.of(0, 10), 1);

        when(mouvementStockService.getByType(eq(TypeMouvement.SORTIE), any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/v2/mouvements/by-type")
                        .param("type", "SORTIE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].type").value("SORTIE"))
                .andExpect(jsonPath("$.content[0].quantite").value(30.00));
    }
}
