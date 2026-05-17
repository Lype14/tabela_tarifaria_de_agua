package com.example.tabela_tarifaria_de_agua.dto;

import com.example.tabela_tarifaria_de_agua.model.Consumidor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record FaixaConsumoRequestDTO(
    @NotNull(message = "O tipo de consumidor é obrigatório")
    Consumidor consumidor,

    @NotNull(message = "O consumo inicial é obrigatório")
    @Min(value = 0, message = "O consumo inicial não pode ser negativo")
    Integer consumoInicial,

    @NotNull(message = "O consumo final é obrigatório")
    Integer consumoFinal,

    @NotNull(message = "O valor unitário é obrigatório")
    @Min(value = 0, message = "O valor unitário não pode ser negativo")
    BigDecimal valorUnitario
) {}