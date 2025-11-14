package com.tricol.tricolV2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.tricolV2.config.AbstractIntegrationTest;
import com.tricol.tricolV2.dto.FournisseurDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FournisseurControllerIT extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void crud_and_search() throws Exception {
        FournisseurDTO dto = new FournisseurDTO(null, "1 rue", "Societe A", "John Doe", "john@acme.com", "+33123456789", "Paris", "ICE123456789");
        String body = objectMapper.writeValueAsString(dto);

        String response = mockMvc.perform(post("/api/v2/fournisseurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn().getResponse().getContentAsString();

        FournisseurDTO created = objectMapper.readValue(response, FournisseurDTO.class);

        mockMvc.perform(get("/api/v2/fournisseurs/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.societe", is("Societe A")));

        dto.setSociete("Societe B");
        String updateBody = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v2/fournisseurs/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.societe", is("Societe B")));

        mockMvc.perform(get("/api/v2/fournisseurs/searchBySociete/Societe"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v2/fournisseurs/searchByVille/Paris"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v2/fournisseurs/searchByIce/ICE123456789"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/v2/fournisseurs/" + created.getId()))
                .andExpect(status().isOk());
    }
}
