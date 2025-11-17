package com.germinai.back.controller;

import com.germinai.back.dto.ForecastResponse;
import com.germinai.back.service.ForecastingService;
import com.germinai.back.service.PriceForecastPythonExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/previsao")
@RequiredArgsConstructor
public class ForecastController {

    private final ForecastingService forecastingService;
    private final PriceForecastPythonExecutor priceForecastPythonExecutor;

    @GetMapping("/{commodity}/{dias}")
    public ForecastResponse prever(
            @PathVariable String commodity,
            @PathVariable int dias
    ){
        var serie = forecastingService.obterSerie90Dias(commodity);
        var prices = forecastingService.extrairPrecos(serie);

        var forecast = priceForecastPythonExecutor.prever(prices, dias);

        // converte histórico
        var historico = serie.stream()
                .map(s -> new ForecastResponse.Ponto(
                        s.getData().toString(),
                        s.getPreco().doubleValue()))
                .toList();

        // gera datas futuras
        var lastDate = serie.getLast().getData();

        var previsao = new java.util.ArrayList<ForecastResponse.Ponto>();
        for (int i = 0; i < forecast.size(); i++){
            previsao.add(
                    new ForecastResponse.Ponto(
                            lastDate.plusDays(i + 1).toString(),
                            forecast.get(i)
                    )
            );
        }

        return new ForecastResponse(commodity.toUpperCase(), historico, previsao);

    }
}
