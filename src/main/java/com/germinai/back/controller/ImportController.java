package com.germinai.back.controller;

import com.germinai.back.service.SerieHistoricaImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
public class ImportController {

    private final SerieHistoricaImportService importService;

    @GetMapping("/soja")
    public String importarSoja() {
        Path csv = Path.of("C:\\Github\\projeto_integrador\\machine_learning\\soja_v2.csv");
        Path csvCont = Path.of("C:\\Github\\projeto_integrador\\machine_learning\\milho_v2.csv");
        importService.importarCsvMilho(csvCont);
        return "Importação concluída com sucesso.";
    }
}