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

    private String nome;
    private String razaoSocial;

    @Enumerated(EnumType.STRING)
    private TipoProprietario tipo;

    private String cpfCnpj;
    private String telefonePrincipal;
    private String telefoneSecundario;
    private String email;
    private String observacao;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
