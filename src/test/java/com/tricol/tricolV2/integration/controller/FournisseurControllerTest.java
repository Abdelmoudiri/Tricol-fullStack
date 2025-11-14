package com.tricol.tricolV2.integration.controller;


import com.tricol.tricolV2.controller.FournisseurController;
import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.service.FournisseurService;
import com.tricol.tricolV2.service.FournisseurServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FournisseurController.class)
public class FournisseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FournisseurServiceImpl fournisseurService;


    @Test
    void  getAll_Fournisseur_Returns_Fournisseur_List() throws Exception {
        List<FournisseurDTO> fournissurs = List.of(new FournisseurDTO(),new FournisseurDTO(),new FournisseurDTO());
        when(fournisseurService.getAllFournisseurs()).thenReturn(fournissurs);
        mockMvc.perform(get("/api/v2/fournisseurs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));

    }
}
