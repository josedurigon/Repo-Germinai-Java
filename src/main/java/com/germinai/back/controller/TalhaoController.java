package com.germinai.back.controller;

import com.germinai.back.entities.Talhao;
import com.germinai.back.service.TalhaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talhao")
@RequiredArgsConstructor
public class TalhaoController {

    private final TalhaoService talhaoService;

    @GetMapping
    public ResponseEntity<List<Talhao>> listarTodos() {
        return ResponseEntity.ok(talhaoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talhao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(talhaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Talhao> criar(@RequestBody Talhao talhao) {
        return ResponseEntity.ok(talhaoService.criar(talhao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talhao> atualizar(@PathVariable Long id, @RequestBody Talhao talhao) {
        return ResponseEntity.ok(talhaoService.atualizar(id, talhao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        talhaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
