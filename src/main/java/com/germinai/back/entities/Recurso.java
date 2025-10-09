package com.germinai.back.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipo; // Semente, Fertilizante, Defensivo, Outro

    @Column(nullable = false)
    private String unidade; // kg, L, sc, ton

    private String grupo;

}
