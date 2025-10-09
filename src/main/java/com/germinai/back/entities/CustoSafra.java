package com.germinai.back.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
public class CustoSafra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "safra_id")
    private Safra safra;

    @Column(name = "tipo_custo")
    private String tipoCusto; // Insumo, Mão de obra, Transporte, etc.

    private String descricao;

    private Double valor;
}
