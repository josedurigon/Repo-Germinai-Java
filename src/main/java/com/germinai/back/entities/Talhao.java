package com.germinai.back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Talhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "area_ha")
    private Double areaHa;

    @Column(name = "analise_solo")
    private String analiseSolo;

}
