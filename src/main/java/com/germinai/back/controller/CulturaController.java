package com.germinai.back.controller;

import com.germinai.back.entities.Cultura;
import com.germinai.back.service.CulturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultura")
@RequiredArgsConstructor
public class CulturaController {

    private final CulturaService culturaService;

    @GetMapping
    public ResponseEntity<List<Cultura>> listarTodas() {
        return ResponseEntity.ok(culturaService.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cultura> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(culturaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Cultura> criar(@RequestBody Cultura cultura) {
        return ResponseEntity.ok(culturaService.criar(cultura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cultura> atualizar(@PathVariable Long id, @RequestBody Cultura cultura) {
        return ResponseEntity.ok(culturaService.atualizar(id, cultura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        culturaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
