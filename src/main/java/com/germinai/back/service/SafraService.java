package com.germinai.back.service;

import com.germinai.back.entities.Safra;
import com.germinai.back.repository.interfaces.ISafraRepository;
import com.germinai.back.service.interfaces.ISafraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SafraService implements ISafraService {

    private final ISafraRepository safraRepository;

    @Override
    public List<Safra> buscarTodasAsSafras() {
        return this.safraRepository.findAll().stream().toList();
    }

    @Override
    public void criarSafra(Safra safra) {
        safra.setStatus("Planejada");
        this.safraRepository.save(safra);
    }

    public Safra buscarPorId(Long id) {
        return safraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Safra não encontrada"));
    }

    public Safra atualizar(Long id, Safra safraAtualizada) {
        Safra existente = buscarPorId(id);
        existente.setNome(safraAtualizada.getNome());
        existente.setCultura(safraAtualizada.getCultura());
        existente.setResponsavel(safraAtualizada.getResponsavel());
        existente.setDataInicio(safraAtualizada.getDataInicio());
        existente.setDataFim(safraAtualizada.getDataFim());
        existente.setStatus(safraAtualizada.getStatus());
        existente.setAreaTotalHa(safraAtualizada.getAreaTotalHa());
        return safraRepository.save(existente);
    }

    public void deletar(Long id) {
        safraRepository.deleteById(id);
    }

    @Override
    public Safra buscarSafraPorId(long id) {
        return this.safraRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Safra nao encontrada"));
    }
}
