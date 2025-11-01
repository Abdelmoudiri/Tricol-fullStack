package com.tricol.tricolV2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurDTO {
    private Long id;

    @NotBlank(message = "L'adresse ne peut pas être vide")
    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    private String adresse;

    @NotBlank(message = "Le nom de la société est obligatoire")
    @Size(max = 150, message = "Le nom de la société ne doit pas dépasser 150 caractères")
    private String societe;

    @NotBlank(message = "Le nom du contact est obligatoire")
    @Size(max = 100, message = "Le contact ne doit pas dépasser 100 caractères")
    private String contact;

    @Email(message = "L'adresse email n'est pas valide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{8,15}$", message = "Numéro de téléphone invalide")
    private String telephone;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "Le code ICE est obligatoire")
    @Size(min = 10, max = 15, message = "Le code ICE doit être entre 10 et 15 caractères")
    private String ice;
}
