package com.germinai.back.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Safra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "cultura_id")
    private Cultura cultura;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Funcionario responsavel;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    private String status; // Planejada, Ativa, Concluída

    @Column(name = "area_total_ha")
    private Double areaTotalHa;

    @OneToMany(mappedBy = "safra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SafraTalhao> talhoes;

    @OneToMany(mappedBy = "safra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SafraRecurso> recursos;

    @OneToMany(mappedBy = "safra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SafraFuncionario> funcionarios;

    @OneToOne(mappedBy = "safra", cascade = CascadeType.ALL)
    private MetaSafra meta;

    @OneToMany(mappedBy = "safra", cascade = CascadeType.ALL)
    private Set<CustoSafra> custos;

    @OneToMany(mappedBy = "safra", cascade = CascadeType.ALL)
    private Set<AtividadeSafra> atividades;

    @OneToMany(mappedBy = "safra", cascade = CascadeType.ALL)
    private Set<HistoricoStatusSafra> historicoStatus;
}

