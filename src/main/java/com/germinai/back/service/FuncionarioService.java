package com.germinai.back.service;

import com.germinai.back.entities.Funcionario;
import com.germinai.back.repository.interfaces.IFuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final IFuncionarioRepository funcionarioRepository;

    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    public Funcionario criar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
        Funcionario existente = buscarPorId(id);
        existente.setNome(funcionarioAtualizado.getNome());
        existente.setCargo(funcionarioAtualizado.getCargo());
        existente.setTipoContratacao(funcionarioAtualizado.getTipoContratacao());
        existente.setCargaHorariaSemanal(funcionarioAtualizado.getCargaHorariaSemanal());
        return funcionarioRepository.save(existente);
    }

    public void deletar(Long id) {
        funcionarioRepository.deleteById(id);
    }
}
