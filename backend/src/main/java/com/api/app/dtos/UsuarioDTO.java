package com.api.app.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String email;
    private Boolean ativo;
    private String observacao;
    private String tipoAcesso;

    public UsuarioDTO(Long id, String email, Boolean ativo, String observacao, String tipoAcesso) {
        this.id = id;
        this.email = email;
        this.ativo = ativo;
        this.observacao = observacao;
        this.tipoAcesso = tipoAcesso;
    }
}
