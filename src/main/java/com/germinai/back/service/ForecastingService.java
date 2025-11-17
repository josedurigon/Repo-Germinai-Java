package com.germinai.back.service;

import com.germinai.back.entities.SerieHistoricaSoja;
import com.germinai.back.repository.interfaces.SerieHistoricaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class ForecastingService {
    private final SerieHistoricaRepository repository;


    public List<Double> obterUltimosPrecos(String commodity, int window) {
        List<Double> valores =  repository.findTop15ByCommodityOrderByDataDesc(commodity).stream()
                .map(s -> s.getPreco().doubleValue())
                .collect(Collectors.toList()); // ordem cronológica crescente, necessária para o modelo!

        List<Double> ordered = new ArrayList<>(valores);
        Collections.reverse(ordered);
        return ordered;
    }

    public List<SerieHistoricaSoja> obterSerie90Dias(String commodity) {
        return repository.findTop90ByCommodityOrderByDataDesc(commodity)
                .stream()
                .sorted((a,b) -> a.getData().compareTo(b.getData())) // garante ordem crescente
                .toList();
    }

    public List<Double> extrairPrecos(List<SerieHistoricaSoja> serie){
        return serie.stream()
                .map(s -> s.getPreco().doubleValue())
                .toList();
    }


}
