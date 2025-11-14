package com.tricol.tricolV2.integration.controller;

import com.tricol.tricolV2.controller.CommandeFournisseure;
import com.tricol.tricolV2.dto.CommandeFournisseurDTO;
import com.tricol.tricolV2.entity.enums.StatutCommande;
import com.tricol.tricolV2.service.CommandeFournisseurServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommandeFournisseure.class)
public class CommandeFournisseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommandeFournisseurServiceImpl commandeFournisseurService;

    @Test
    void getAll_commandes_returns_commande_list() throws Exception {
        // Arrange
        CommandeFournisseurDTO commande1 = new CommandeFournisseurDTO();
        commande1.setId(1L);
        commande1.setFournisseurId(1L);
        commande1.setDateCommande(LocalDateTime.now());
        commande1.setStatut(StatutCommande.EN_ATTENTE);
        commande1.setMontantTotal(new BigDecimal("500.00"));

        CommandeFournisseurDTO commande2 = new CommandeFournisseurDTO();
        commande2.setId(2L);
        commande2.setFournisseurId(2L);
        commande2.setDateCommande(LocalDateTime.now());
        commande2.setStatut(StatutCommande.LIVREE);
        commande2.setMontantTotal(new BigDecimal("750.00"));

        List<CommandeFournisseurDTO> commandes = Arrays.asList(commande1, commande2);
        when(commandeFournisseurService.getAll()).thenReturn(commandes);

        // Act & Assert
        mockMvc.perform(get("/api/v2/commandes-fournisseur"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].statut").value("EN_ATTENTE"))
                .andExpect(jsonPath("$[1].statut").value("LIVREE"));
    }

    @Test
    void getById_commande_returns_commande() throws Exception {
        // Arrange
        CommandeFournisseurDTO commande = new CommandeFournisseurDTO();
        commande.setId(1L);
        commande.setFournisseurId(1L);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(StatutCommande.EN_ATTENTE);
        commande.setMontantTotal(new BigDecimal("1200.00"));

        when(commandeFournisseurService.getById(1L)).thenReturn(Optional.of(commande));

        // Act & Assert
        mockMvc.perform(get("/api/v2/commandes-fournisseur/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fournisseurId").value(1))
                .andExpect(jsonPath("$.statut").value("EN_ATTENTE"))
                .andExpect(jsonPath("$.montantTotal").value(1200.00));
    }

    @Test
    void updateStatut_commande_returns_updated_commande() throws Exception {
        // Arrange
        CommandeFournisseurDTO commande = new CommandeFournisseurDTO();
        commande.setId(1L);
        commande.setFournisseurId(1L);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(StatutCommande.LIVREE);
        commande.setMontantTotal(new BigDecimal("800.00"));

        when(commandeFournisseurService.updateStatut(1L, StatutCommande.LIVREE)).thenReturn(commande);

        // Act & Assert
        mockMvc.perform(put("/api/v2/commandes-fournisseur/1/statut")
                        .param("value", "LIVREE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.statut").value("LIVREE"))
                .andExpect(jsonPath("$.montantTotal").value(800.00));
    }
}
