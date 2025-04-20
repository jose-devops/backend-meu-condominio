package com.api.app.dtos;

import com.api.app.models.StatusImovel;
import lombok.Data;

@Data
public class ImovelDTO {

    private Long id;

    private String descricao;

    private Long proprietarioId;

    private Long moradorId;

    private String endereco;

    private String cep;

    private String uf;

    private String cidade;

    private String bairro;

    private String numero;

    private String complemento;

    private Double valorAluguel;

    private Double valorCondominio;

    private StatusImovel situacao;

    private String observacao;
}
