package com.tricol.tricolV2.unit.service;

import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.entity.Fournisseur;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.mapper.FournisseurMapper;
import com.tricol.tricolV2.repository.FournisseurRepository;
import com.tricol.tricolV2.service.FournisseurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FournisseurServiceUnitTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private FournisseurMapper fournisseurMapper;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    private FournisseurDTO fournisseurDTO;
    private Fournisseur fournisseur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fournisseurDTO = new FournisseurDTO();
        fournisseurDTO.setId(1L);
        fournisseurDTO.setSociete("Société Test");
        fournisseurDTO.setContact("Contact Test");
        fournisseurDTO.setEmail("test@test.com");
        fournisseurDTO.setTelephone("0612345678");
        fournisseurDTO.setAdresse("123 Rue Test");
        fournisseurDTO.setVille("Casablanca");
        fournisseurDTO.setIce("ICE123456");

        fournisseur = new Fournisseur();
        fournisseur.setId(1L);
        fournisseur.setSociete("Société Test");
        fournisseur.setContact("Contact Test");
        fournisseur.setEmail("test@test.com");
        fournisseur.setTelephone("0612345678");
        fournisseur.setAdresse("123 Rue Test");
        fournisseur.setVille("Casablanca");
        fournisseur.setIce("ICE123456");
    }

    @Test
    public void create_fournisseur_success_returns_fournisseurDTO() {
        // Arrange
        when(fournisseurMapper.toEntity(fournisseurDTO)).thenReturn(fournisseur);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);
        when(fournisseurMapper.toDTO(fournisseur)).thenReturn(fournisseurDTO);

        // Act
        FournisseurDTO result = fournisseurService.addFournisseur(fournisseurDTO);

        // Assert
        assertNotNull(result);
        assertEquals(fournisseurDTO.getId(), result.getId());
        assertEquals(fournisseurDTO.getSociete(), result.getSociete());
        verify(fournisseurRepository, times(1)).save(any(Fournisseur.class));
        verify(fournisseurMapper, times(1)).toEntity(fournisseurDTO);
        verify(fournisseurMapper, times(1)).toDTO(fournisseur);
    }

    @Test
    public void getById_fournisseur_success_returns_fournisseurDTO() {
        // Arrange
        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur));
        when(fournisseurMapper.toDTO(fournisseur)).thenReturn(fournisseurDTO);

        // Act
        Optional<FournisseurDTO> result = fournisseurService.getFournisseurById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(fournisseurDTO.getId(), result.get().getId());
        assertEquals(fournisseurDTO.getSociete(), result.get().getSociete());
        verify(fournisseurRepository, times(1)).findById(1L);
        verify(fournisseurMapper, times(1)).toDTO(fournisseur);
    }

    @Test
    public void update_fournisseur_success_returns_updatedFournisseurDTO() {
        // Arrange
        FournisseurDTO updatedDTO = new FournisseurDTO();
        updatedDTO.setId(1L);
        updatedDTO.setSociete("Société Modifiée");
        updatedDTO.setContact("Contact Modifié");
        updatedDTO.setEmail("updated@test.com");
        updatedDTO.setTelephone("0698765432");
        updatedDTO.setAdresse("456 Avenue Test");
        updatedDTO.setVille("Rabat");
        updatedDTO.setIce("ICE654321");

        Fournisseur updatedFournisseur = new Fournisseur();
        updatedFournisseur.setId(1L);
        updatedFournisseur.setSociete("Société Modifiée");
        updatedFournisseur.setContact("Contact Modifié");
        updatedFournisseur.setEmail("updated@test.com");
        updatedFournisseur.setTelephone("0698765432");
        updatedFournisseur.setAdresse("456 Avenue Test");
        updatedFournisseur.setVille("Rabat");
        updatedFournisseur.setIce("ICE654321");

        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur));
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(updatedFournisseur);
        when(fournisseurMapper.toDTO(updatedFournisseur)).thenReturn(updatedDTO);

        // Act
        FournisseurDTO result = fournisseurService.updateFournisseur(1L, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedDTO.getSociete(), result.getSociete());
        assertEquals(updatedDTO.getVille(), result.getVille());
        verify(fournisseurRepository, times(1)).findById(1L);
        verify(fournisseurRepository, times(1)).save(any(Fournisseur.class));
        verify(fournisseurMapper, times(1)).toDTO(updatedFournisseur);
    }
}
