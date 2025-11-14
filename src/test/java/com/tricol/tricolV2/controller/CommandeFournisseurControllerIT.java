package com.tricol.tricolV2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.tricolV2.config.AbstractIntegrationTest;
import com.tricol.tricolV2.dto.CommandeFournisseurDTO;
import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.dto.LigneCommandeDTO;
import com.tricol.tricolV2.dto.ProduitDTO;
import com.tricol.tricolV2.entity.enums.StatutCommande;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommandeFournisseurControllerIT extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_updateStatut_and_verify_stock_movements() throws Exception {

        FournisseurDTO f = new FournisseurDTO(null, "10 av.", "CmdCorp", "Alice", "alice@corp.com", "+212600000000", "Casa", "ICE999999999");
        String fJson = objectMapper.writeValueAsString(f);
        String fResp = mockMvc.perform(post("/api/v2/fournisseurs").contentType(MediaType.APPLICATION_JSON).content(fJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        FournisseurDTO createdF = objectMapper.readValue(fResp, FournisseurDTO.class);

        ProduitDTO p = new ProduitDTO(null, "ProdCmd", "desc", new BigDecimal("20.00"), "Cat", new BigDecimal("10"), null);
        String pJson = objectMapper.writeValueAsString(p);
        String pResp = mockMvc.perform(post("/api/v2/produits").contentType(MediaType.APPLICATION_JSON).content(pJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ProduitDTO createdP = objectMapper.readValue(pResp, ProduitDTO.class);

        LigneCommandeDTO l1 = LigneCommandeDTO.builder()
                .produitId(createdP.getId())
                .quantite(new BigDecimal("3"))
                .prixUnitaire(new BigDecimal("20.00"))
                .build();
        CommandeFournisseurDTO cmd = new CommandeFournisseurDTO(null,
                LocalDateTime.now(),
                StatutCommande.EN_ATTENTE,
                null,
                createdF.getId(),
                List.of(l1));

        String cJson = objectMapper.writeValueAsString(cmd);
        String cResp = mockMvc.perform(post("/api/v2/commandes-fournisseur").contentType(MediaType.APPLICATION_JSON).content(cJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        CommandeFournisseurDTO createdCmd = objectMapper.readValue(cResp, CommandeFournisseurDTO.class);

        mockMvc.perform(put("/api/v2/commandes-fournisseur/" + createdCmd.getId() + "/statut")
                        .param("value", StatutCommande.LIVREE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut", is(StatutCommande.LIVREE.name())));

        mockMvc.perform(get("/api/v2/mouvements/by-commande").param("commandeId", String.valueOf(createdCmd.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThan(0))));
    }
}
