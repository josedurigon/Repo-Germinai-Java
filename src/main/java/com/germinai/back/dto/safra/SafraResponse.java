package com.germinai.back.dto.safra;

import com.germinai.back.dto.CulturaSimpleDto;
import com.germinai.back.dto.FuncionarioSimpleDto;
import java.time.LocalDate;
import java.util.List;

public record SafraResponse(
    Long id,
    String nome,
    CulturaSimpleDto cultura,
    FuncionarioSimpleDto responsavel,
    LocalDate dataInicio,
    LocalDate dataFim,
    String status,
    Double areaTotalHa,
    Integer diasAteColheita,
    Integer progressoPercentual,
    Double receitaEstimada,
    Double lucroPrevisto,
    List<TalhaoSafraDto> talhoes,
    MetaSafraDto meta
) {}
