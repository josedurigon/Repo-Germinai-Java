package com.germinai.back.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
public class MetaSafra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "safra_id", unique = true)
    private Safra safra;

    @Column(name = "produtividade_alvo")
    private Double produtividadeAlvo;

    @Column(name = "custo_estimado_total")
    private Double custoEstimadoTotal;
}
