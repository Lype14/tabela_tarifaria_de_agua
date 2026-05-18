package com.example.tabela_tarifaria_de_agua.dto;

import java.math.BigDecimal;

public record DetalheFaixaFaturadaDTO(
    LimitesFaixaDTO faixa, 
    int m3Cobrados,        
    BigDecimal valorUnitario,
    BigDecimal subtotal
) {}