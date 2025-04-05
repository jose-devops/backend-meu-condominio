package com.api.app.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "proprietario") // Se quiser personalizar o nome da tabela
@Data
public class ProprietarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String email;
    private String cnpj;
    private String razaoSocial;
    // Outros atributos específicos de proprietário

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
