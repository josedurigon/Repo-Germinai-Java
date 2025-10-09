package com.germinai.back.service.interfaces;

import com.germinai.back.entities.Safra;

import java.util.List;

public interface ISafraService {
    List<Safra> buscarTodasAsSafras();
    void criarSafra(Safra safra);
    Safra atualizar(Long id, Safra safraAtualizada);
    void deletar(Long id);
    Safra buscarSafraPorId(long id);
}
