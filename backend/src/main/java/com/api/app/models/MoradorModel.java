package com.api.app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "morador")
@Data
public class MoradorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;

    private LocalDate dataAniversario;

    private String telefonePrincipal;
    private String telefoneSecundario;

    private String profissao;

    @Column(precision = 10, scale = 2)
    private BigDecimal rendaMensal;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
