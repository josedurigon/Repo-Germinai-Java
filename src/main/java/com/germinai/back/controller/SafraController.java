package com.germinai.back.controller;

import com.germinai.back.dto.safra.SafraCreateRequest;
import com.germinai.back.dto.safra.SafraResponse;
import com.germinai.back.entities.Safra;
import com.germinai.back.service.SafraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/safra")
@RequiredArgsConstructor
public class SafraController {

    private final SafraService safraService;

    @GetMapping
    public ResponseEntity<List<SafraResponse>> listar() {
        return ResponseEntity.ok(safraService.buscarTodasAsSafrasDto());
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<SafraResponse>> listarAtivas() {
        return ResponseEntity.ok(safraService.buscarSafrasAtivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SafraResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(safraService.buscarSafraPorIdDto(id));
    }

    @PostMapping
    public ResponseEntity<SafraResponse> cadastrar(@Valid @RequestBody SafraCreateRequest request) {
        SafraResponse response = safraService.criarSafraCompleta(request);
        return ResponseEntity.ok(response);
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
