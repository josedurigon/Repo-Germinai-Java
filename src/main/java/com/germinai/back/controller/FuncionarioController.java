package com.germinai.back.controller;

import com.germinai.back.entities.Funcionario;
import com.germinai.back.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario) {
        return ResponseEntity.ok(funcionarioService.criar(funcionario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        return ResponseEntity.ok(funcionarioService.atualizar(id, funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
