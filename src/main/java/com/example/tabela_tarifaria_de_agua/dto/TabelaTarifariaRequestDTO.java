package com.example.tabela_tarifaria_de_agua.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record TabelaTarifariaRequestDTO(
    @NotBlank(message = "O nome da tabela é obrigatório")
    String nome,

    @NotNull(message = "A data de vigência é obrigatória")
    LocalDate dataVigencia,

    @NotNull(message = "A lista de faixas não pode estar vazia")
    @Valid // Diz ao Spring para validar também os itens dentro da lista
    List<FaixaConsumoRequestDTO> faixas
) {}