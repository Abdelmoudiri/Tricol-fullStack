package com.tricol.backend_java.entities;


import com.tricol.backend_java.entities.enums.MethodeValorisation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MethodeValorisation methodeValorisation = MethodeValorisation.CUMP;
}
