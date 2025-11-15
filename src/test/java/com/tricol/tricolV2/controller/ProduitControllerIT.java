package com.tricol.tricolV2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.tricolV2.dto.ProduitDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProduitControllerIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_and_get_produit() throws Exception {
        ProduitDTO dto = new ProduitDTO(null, "ProdA", "desc", new BigDecimal("12.50"), "catA", new BigDecimal("0"), null);

        String body = objectMapper.writeValueAsString(dto);

        String location = mockMvc.perform(post("/api/v2/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get("/api/v2/produits"))
                .andExpect(status().isOk());
    }
}