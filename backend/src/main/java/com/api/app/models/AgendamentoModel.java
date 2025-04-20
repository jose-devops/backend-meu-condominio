package com.api.app.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "agendamento")
@Data
public class AgendamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private String tipoAgendamento;

    @Enumerated(EnumType.STRING)
    private TipoAcesso tipo;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    private String observacao;




}
