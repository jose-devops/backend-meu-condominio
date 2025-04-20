package com.api.app.dtos;

import lombok.Data;

@Data
public class ImovelResponseDTO {
    private Long id;
    private String endereco;
    private String cep;
    private String cidade;
    private String estado;
    private String descricao;
    private Long proprietarioId;
}
