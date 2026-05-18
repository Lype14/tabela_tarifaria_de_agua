package com.example.tabela_tarifaria_de_agua.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record AtualizarPrecoFaixaDTO(@PositiveOrZero(message = "O novo valor unitário não pode ser negativo.") float novoValorUnitario) {}
