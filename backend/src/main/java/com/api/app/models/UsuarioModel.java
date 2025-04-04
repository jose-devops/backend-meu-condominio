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
    private Boolean ativo; // Considerando que "ativo" controlará o status do usuário
    private String observacao;

    @Enumerated(EnumType.STRING)
    private TipoAcesso tipoAcesso;  // Renomeando para 'TipoAcesso' seguindo convenção Java

}
