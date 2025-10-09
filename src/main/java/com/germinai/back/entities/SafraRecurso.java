package com.germinai.back.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
public class SafraRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "recurso_id")
    private Recurso recurso;

    @Column(name = "quantidade_utilizada")
    private Double quantidadeUtilizada;

    @Column(name = "custo_unitario")
    private Double custoUnitario;

    @ManyToOne
    @JoinColumn(name= "safra_id")
    private Safra safra;
}
