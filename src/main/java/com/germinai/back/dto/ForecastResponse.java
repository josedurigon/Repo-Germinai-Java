package com.germinai.back.dto;


import java.util.List;

public record ForecastResponse(
        String commodity,
        List<Ponto> historico,
        List<Ponto> previsao
) {
    public record Ponto(String data, Double preco) {}
}
