package com.api.app.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String senha;
    private Boolean ativo; // Indica se o usuário está ativo
    private String observacao;

    @Enumerated(EnumType.STRING)
    private TipoAcesso tipoAcesso;  // Enum com valores, por exemplo, PROPRIETARIO e INQUILINO

    // Relacionamento OneToOne com o perfil Inquilino
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private InquilinoModel inquilino;

    // Relacionamento OneToOne com o perfil Proprietário
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private ProprietarioModel proprietario;
}
