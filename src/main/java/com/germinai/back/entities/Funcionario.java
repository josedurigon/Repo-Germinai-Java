package com.germinai.back.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String cargo;

    @Column(name = "tipo_contratacao")
    private String tipoContratacao;

    @Column(name = "carga_horaria_semanal")
    private Integer cargaHorariaSemanal;
}
