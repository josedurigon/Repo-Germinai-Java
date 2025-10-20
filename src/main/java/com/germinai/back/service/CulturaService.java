package com.germinai.back.service;

import com.germinai.back.entities.Cultura;
import com.germinai.back.repository.interfaces.ICulturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CulturaService {

    private final ICulturaRepository culturaRepository;

    public List<Cultura> buscarTodas() {
        return culturaRepository.findAll();
    }

    public Cultura buscarPorId(Long id) {
        return culturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cultura não encontrada"));
    }

    public Cultura criar(Cultura cultura) {
        return culturaRepository.save(cultura);
    }

    public Cultura atualizar(Long id, Cultura culturaAtualizada) {
        Cultura existente = buscarPorId(id);
        existente.setNome(culturaAtualizada.getNome());
        existente.setCicloDias(culturaAtualizada.getCicloDias());
        existente.setEpocaPlantio(culturaAtualizada.getEpocaPlantio());
        existente.setEpocaColheita(culturaAtualizada.getEpocaColheita());
        existente.setTipoSolo(culturaAtualizada.getTipoSolo());
        existente.setEspacamento(culturaAtualizada.getEspacamento());
        existente.setSementesPorHa(culturaAtualizada.getSementesPorHa());
        existente.setProdutividadeMedia(culturaAtualizada.getProdutividadeMedia());
        return culturaRepository.save(existente);
    }

    public void deletar(Long id) {
        culturaRepository.deleteById(id);
    }
}
