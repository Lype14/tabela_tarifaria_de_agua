package com.example.tabela_tarifaria_de_agua.dto;

import java.math.BigDecimal;
import java.util.List;

public record CalculoRespostaDTO(
    String categoria,
    int consumoTotal,
    BigDecimal valorTotal,
    List<DetalheFaixaFaturadaDTO> detalhamento 
) {}