package com.germinai.back.dto.safra;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record SafraCreateRequest(
    @NotBlank(message = "Nome da safra é obrigatório")
    String nome,

    @NotNull(message = "Cultura é obrigatória")
    Long culturaId,

    @NotNull(message = "Responsável é obrigatório")
    Long responsavelId,

    @NotNull(message = "Data de início é obrigatória")
    LocalDate dataInicio,

    LocalDate dataFim, // Opcional - pode ser calculada

    @NotNull(message = "Área total é obrigatória")
    @Positive(message = "Área deve ser maior que zero")
    Double areaTotalHa,

    // Talhões a serem associados
    List<TalhaoSafraRequest> talhoes,

    // Meta (opcional)
    MetaSafraRequest meta,

    // Valores estimados
    Double receitaEstimada,
    Double lucroPrevisto
) {}
