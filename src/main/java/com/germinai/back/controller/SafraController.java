package com.germinai.back.controller;

import com.germinai.back.entities.Safra;
import com.germinai.back.service.interfaces.ISafraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/safra")
public class SafraController {


    private ISafraService safraService;

    @GetMapping
    public ResponseEntity<List<Safra>> listar() {
        return ResponseEntity.ok(safraService.buscarTodasAsSafras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Safra> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(safraService.buscarSafraPorId(id));
    }

    @PostMapping
    public ResponseEntity<Safra> cadastrar(@RequestBody Safra safra) {
        safraService.criarSafra(safra);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Safra> atualizar(@PathVariable Long id, @RequestBody Safra safra) {
        return ResponseEntity.ok(safraService.atualizar(id, safra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        safraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
