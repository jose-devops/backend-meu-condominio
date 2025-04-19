package com.api.app.models;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "imovel")
@Data
public class ImovelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private ProprietarioModel proprietario;


    @ManyToOne
    @JoinColumn(name = "inquilino_id", nullable = true)
    private MoradorModel morador;


    private String endereco;

    private String cep;

    private String uf;

    private String cidade;

    private String bairro;

    private String numero;

    private String complemento;

    @Column(name = "valor_aluguel")
    private Double valorAluguel;

    @Column(name = "valor_condominio")
    private Double valorCondominio;

    @Enumerated(EnumType.STRING)
    private StatusImovel situacao;

    @Column(columnDefinition = "TEXT")
    private String observacao;












}

