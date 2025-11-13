package com.tricol.tricolV2.unit.service;

import com.tricol.tricolV2.dto.CommandeFournisseurDTO;
import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.entity.CommandeFournisseur;
import com.tricol.tricolV2.entity.Fournisseur;
import com.tricol.tricolV2.mapper.CommandeFournisseurMapper;
import com.tricol.tricolV2.mapper.LigneCommandeMapper;
import com.tricol.tricolV2.repository.CommandeFournisseurRepository;
import com.tricol.tricolV2.repository.FournisseurRepository;
import com.tricol.tricolV2.repository.ProduitRepository;
import com.tricol.tricolV2.service.CommandeFournisseurService;
import com.tricol.tricolV2.service.CommandeFournisseurServiceImpl;
import com.tricol.tricolV2.service.MouvementStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class CommandeFournisseurServiceUnitTest {


    @Mock
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;
    @Mock
    private ProduitRepository produitRepository;
    @Mock
    private CommandeFournisseurMapper commandeFournisseurMapperMapper;
    @Mock
    private LigneCommandeMapper ligneMapper;
    @Mock
    private MouvementStockService mouvementStockService;



    private CommandeFournisseurDTO commandeFournisseurDTO;
    private  CommandeFournisseur commandeFournisseur;
    private Fournisseur fournisseur ;

    private  FournisseurDTO fournisseurDTO;

    @InjectMocks
    private CommandeFournisseurServiceImpl commandeFournisseurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

         fournisseurDTO = new FournisseurDTO();
        fournisseurDTO.setId(1L);
        fournisseurDTO.setContact("f-123");

         fournisseur = new Fournisseur();
        fournisseur.setId(1L);

         commandeFournisseurDTO = new CommandeFournisseurDTO();
         commandeFournisseurDTO.setId(1L);
        commandeFournisseurDTO.setFournisseurId(1L);

        commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(1L);

    }

    @Test
    public void create_commandeFournisseur_succes_return_commandeFournisseurDto() {

        // Arrange
        when(fournisseurRepository.findById(commandeFournisseurDTO.getFournisseurId())).thenReturn(Optional.of(fournisseur));
        when(commandeFournisseurMapperMapper.toEntity(commandeFournisseurDTO)).thenReturn(commandeFournisseur);
        when(commandeFournisseurMapperMapper.toDTO(any(CommandeFournisseur.class))).thenReturn(commandeFournisseurDTO);
        when(commandeFournisseurRepository.save(any(CommandeFournisseur.class))).thenReturn(commandeFournisseur);

        // Act
        CommandeFournisseurDTO result = commandeFournisseurService.create(commandeFournisseurDTO);

        // Assert
        assertNotNull(result);
        assertEquals(result.getId(),commandeFournisseurDTO.getId());
        verify(fournisseurRepository,times(1)).findById(1L);
        verify(commandeFournisseurRepository,times(1)).save(commandeFournisseur);
    }






}
