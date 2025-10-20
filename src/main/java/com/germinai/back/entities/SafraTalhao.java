package com.germinai.back.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(exclude = {"safra"})
public class SafraTalhao {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "safra_id")
    private Safra safra;

    @ManyToOne
    @JoinColumn(name = "talhao_id")
    private Talhao talhao;

    @Column(name = "area_utilizada_ha")
    private Double areaUtilizadaHa;
}
