package com.example.tabela_tarifaria_de_agua.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CalculoRequestDTO(
    @NotNull(message = "A categoria é obrigatória.")
    String categoria,
    
    @PositiveOrZero(message = "O consumo informado não pode ser negativo.")
    int consumo
) {}