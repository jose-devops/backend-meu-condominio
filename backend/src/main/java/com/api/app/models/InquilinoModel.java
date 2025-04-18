package com.api.app.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inquilino")  // Se quiser personalizar o nome da tabela
@Data
public class InquilinoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String email;
    private String cpf;
    private String telefone;

    // Outros atributos específicos de inquilino

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
