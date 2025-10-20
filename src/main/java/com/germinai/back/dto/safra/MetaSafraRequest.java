package com.germinai.back.dto.safra;

import jakarta.validation.constraints.Positive;

public record MetaSafraRequest(
    @Positive(message = "Produtividade alvo deve ser maior que zero")
    Double produtividadeAlvo,

    @Positive(message = "Custo estimado deve ser maior que zero")
    Double custoEstimadoTotal
) {}
