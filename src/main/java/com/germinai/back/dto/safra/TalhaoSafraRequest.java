package com.germinai.back.dto.safra;

import jakarta.validation.constraints.*;

public record TalhaoSafraRequest(
    @NotNull(message = "ID do talhão é obrigatório")
    Long talhaoId,

    @NotNull(message = "Área utilizada é obrigatória")
    @Positive(message = "Área deve ser maior que zero")
    Double areaUtilizadaHa
) {}
