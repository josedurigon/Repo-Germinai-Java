package com.germinai.back.service;

import com.germinai.back.entities.SerieHistoricaSoja;
import com.germinai.back.repository.interfaces.SerieHistoricaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SerieHistoricaImportService {

    private final SerieHistoricaRepository repository;

    public void importarCsvSoja(Path csvPath) {
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {

            // pula o header
            String linha = br.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            while ((linha = br.readLine()) != null) {

                String[] parts = linha.split(",");

                String rawData = parts[0].trim();
                String rawPreco = parts[1].trim();

                rawData = rawData.replace("\"", "");   // remove aspas
                rawPreco = rawPreco.replace("\"", ""); // remove aspas

                LocalDate data = LocalDate.parse(rawData, formatter);

                rawPreco = rawPreco.replace(".", "").replace(",", ".");
                BigDecimal preco = new BigDecimal(rawPreco);

                SerieHistoricaSoja entity = SerieHistoricaSoja.builder()
                        .commodity("soja")
                        .data(data)
                        .preco(preco)
                        .build();

                // verifica se já existe
                var existenteOpt = repository.findByCommodityAndData("soja", data);

                if (existenteOpt.isPresent()) {
                    SerieHistoricaSoja existente = existenteOpt.get();

                    // se o preço mudou, atualiza
                    if (existente.getPreco().compareTo(preco) != 0) {
                        existente.setPreco(preco);
                        repository.save(existente);
                    }

                } else {
                    // cria novo registro
                    SerieHistoricaSoja novo = SerieHistoricaSoja.builder()
                            .commodity("soja")
                            .data(data)
                            .preco(preco)
                            .build();

                    repository.save(novo);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar CSV de soja", e);
        }
    }

    public void importarCsvMilho(Path csvPath) {
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {

            // pula o header
            String linha = br.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            while ((linha = br.readLine()) != null) {

                String[] parts = linha.split(",");

                String rawData = parts[0].trim();
                String rawPreco = parts[1].trim();

                rawData = rawData.replace("\"", "");   // remove aspas
                rawPreco = rawPreco.replace("\"", ""); // remove aspas

                LocalDate data = LocalDate.parse(rawData, formatter);

                rawPreco = rawPreco.replace(".", "").replace(",", ".");
                BigDecimal preco = new BigDecimal(rawPreco);

                SerieHistoricaSoja entity = SerieHistoricaSoja.builder()
                        .commodity("milho")
                        .data(data)
                        .preco(preco)
                        .build();

                // verifica se já existe
                var existenteOpt = repository.findByCommodityAndData("milho", data);

                if (existenteOpt.isPresent()) {
                    SerieHistoricaSoja existente = existenteOpt.get();

                    // se o preço mudou, atualiza
                    if (existente.getPreco().compareTo(preco) != 0) {
                        existente.setPreco(preco);
                        repository.save(existente);
                    }

                } else {
                    // cria novo registro
                    SerieHistoricaSoja novo = SerieHistoricaSoja.builder()
                            .commodity("milho")
                            .data(data)
                            .preco(preco)
                            .build();

                    repository.save(novo);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar CSV de soja", e);
        }
    }

}

