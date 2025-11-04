package com.tricol.backend_java.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String societe;

    private String adresse;

    private String contact;

    @Email
    private String email;

    private String telephone;

    private String ville;

    @NotBlank
    private String ice;
}
