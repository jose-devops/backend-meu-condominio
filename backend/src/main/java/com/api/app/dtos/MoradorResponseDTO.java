package com.api.app.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MoradorResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataAniversario;
    private String telefonePrincipal;
    private String telefoneSecundario;
    private String profissao;
    private BigDecimal rendaMensal;
    private String observacao;

    private String usuarioEmail;
}
