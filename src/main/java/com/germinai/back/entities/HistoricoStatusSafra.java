package com.germinai.back.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity

@Data
public class HistoricoStatusSafra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "safra_id")
    private Safra safra;

    @Column(name = "status_anterior")
    private String statusAnterior;

    @Column(name = "status_novo")
    private String statusNovo;

    @Column(name = "data_alteracao")
    private LocalDateTime dataAlteracao;
}
