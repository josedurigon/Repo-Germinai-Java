package com.germinai.back.service;

import com.germinai.back.entities.Talhao;
import com.germinai.back.repository.interfaces.ITalhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TalhaoService {

    private final ITalhaoRepository talhaoRepository;

    public List<Talhao> buscarTodos() {
        return talhaoRepository.findAll();
    }

    public Talhao buscarPorId(Long id) {
        return talhaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado"));
    }

    public Talhao criar(Talhao talhao) {
        return talhaoRepository.save(talhao);
    }

    public Talhao atualizar(Long id, Talhao talhaoAtualizado) {
        Talhao existente = buscarPorId(id);
        existente.setNome(talhaoAtualizado.getNome());
        existente.setAreaHa(talhaoAtualizado.getAreaHa());
        existente.setAnaliseSolo(talhaoAtualizado.getAnaliseSolo());
        return talhaoRepository.save(existente);
    }

    public void deletar(Long id) {
        talhaoRepository.deleteById(id);
    }
}
