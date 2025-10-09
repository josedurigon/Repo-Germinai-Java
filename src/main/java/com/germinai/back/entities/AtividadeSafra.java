package com.germinai.back.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class AtividadeSafra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "safra_id")
    private Safra safra;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    private String descricao;

    @Column(name = "data_execucao")
    private LocalDate dataExecucao;

    private String status; // Pendente, Em execução, Concluída
}
