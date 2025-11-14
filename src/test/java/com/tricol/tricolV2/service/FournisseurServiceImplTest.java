package com.tricol.tricolV2.service;

import com.tricol.tricolV2.dto.FournisseurDTO;
import com.tricol.tricolV2.entity.Fournisseur;
import com.tricol.tricolV2.exception.NotFoundException;
import com.tricol.tricolV2.mapper.FournisseurMapper;
import com.tricol.tricolV2.repository.FournisseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FournisseurServiceImplTest {

    @Mock
    FournisseurRepository repository;
    @Mock
    FournisseurMapper mapper;

    @InjectMocks
    FournisseurServiceImpl service;

    @Test
    void addFournisseur_should_map_and_save_and_return_dto() {
        FournisseurDTO input = new FournisseurDTO();
        input.setSociete("ACME");
        Fournisseur entity = new Fournisseur();
        Fournisseur saved = new Fournisseur();
        saved.setId(1L);
        FournisseurDTO expected = new FournisseurDTO();
        expected.setId(1L);

        when(mapper.toEntity(input)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(expected);

        FournisseurDTO result = service.addFournisseur(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).save(entity);
    }

    @Test
    void updateFournisseur_should_throw_when_not_found() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.updateFournisseur(99L, new FournisseurDTO()));
    }

    @Test
    void getFournisseurs_should_return_page_of_dtos() {
        PageRequest pageable = PageRequest.of(0, 10);
        Fournisseur entity = new Fournisseur();
        entity.setId(5L);
        Page<Fournisseur> page = new PageImpl<>(List.of(entity));
        FournisseurDTO dto = new FournisseurDTO();
        dto.setId(5L);

        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.toDTO(entity)).thenReturn(dto);

        Page<FournisseurDTO> result = service.getFournisseurs(pageable);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(5L);
    }

    @Test
    void deleteFournisseur_should_delete_when_exists() {
        Fournisseur e = new Fournisseur();
        e.setId(2L);
        when(repository.findById(2L)).thenReturn(Optional.of(e));

        service.deleteFournisseur(2L);
        verify(repository).delete(e);
    }
}
