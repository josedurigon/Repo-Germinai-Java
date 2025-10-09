package com.germinai.back.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Cultura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ciclo_dias", nullable = false)
    private Integer cicloDias;

    @Column(name = "epoca_plantio")
    private String epocaPlantio;

    @Column(name = "epoca_colheita")
    private String epocaColheita;

    @Column(name = "tipo_solo")
    private String tipoSolo;

    private String espacamento;

    @Column(name = "sementes_por_ha")
    private Double sementesPorHa;

    @Column(name = "produtividade_media")
    private Double produtividadeMedia;

    @ManyToMany
    @JoinTable(
            name = "cultura_recurso",
            joinColumns = @JoinColumn(name = "cultura_id"),
            inverseJoinColumns = @JoinColumn(name = "recurso_id")
    )
    private Set<Recurso> recursos;

    @OneToMany(mappedBy = "cultura")
    private Set<Safra> safras;

}
