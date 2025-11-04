package com.tricol.backend_java.repositories;

import com.tricol.backend_java.dto.FournisseurDTO;
import com.tricol.backend_java.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    Boolean existsByEmail(String email);
}