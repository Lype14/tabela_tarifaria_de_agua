package com.example.tabela_tarifaria_de_agua.service;

import com.example.tabela_tarifaria_de_agua.dto.FaixaConsumoRequestDTO;
import com.example.tabela_tarifaria_de_agua.dto.TabelaTarifariaRequestDTO;
import com.example.tabela_tarifaria_de_agua.model.FaixaConsumo;
import com.example.tabela_tarifaria_de_agua.model.TabelaTarifaria;
import com.example.tabela_tarifaria_de_agua.repository.TabelaTarifariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service // 1. Avisa ao Spring que esta classe é um componente de serviço (Diz ao @Autowired do Controller que ela existe)
public class TabelaTarifariaService {

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    @Transactional // 2. Garante transação: se der erro em uma faixa, desfaz tudo e não quebra o banco
    public void salvarTabelaCompleta(TabelaTarifariaRequestDTO dto) {
        
        // 3. Agrupar as faixas enviadas no JSON por Tipo de Consumidor (PARTICULAR, INDUSTRIAL...)
        Map<com.example.tabela_tarifaria_de_agua.model.Consumidor, List<FaixaConsumoRequestDTO>> faixasPorConsumidor = 
            dto.faixas().stream().collect(Collectors.groupingBy(FaixaConsumoRequestDTO::consumidor));

        // 4. Validar as regras de negócio do PDF para cada grupo isoladamente
        for (Map.Entry<com.example.tabela_tarifaria_de_agua.model.Consumidor, List<FaixaConsumoRequestDTO>> entry : faixasPorConsumidor.entrySet()) {
            validarRegrasDasFaixas(entry.getKey().toString(), entry.getValue());
        }

        // 5. Se todas as regras passaram, desativa as tabelas antigas antes de salvar a nova (Regra contra ambiguidade!)
        tabelaTarifariaRepository.findByAtivoTrue().ifPresent(tabelaAntiga -> {
            tabelaAntiga.setAtivo(false);
            tabelaTarifariaRepository.save(tabelaAntiga);
        });

        // 6. Converter o DTO para a Entidade TabelaTarifaria
        TabelaTarifaria tabela = new TabelaTarifaria();
        tabela.setNome(dto.nome());
        tabela.setDataVigencia(dto.dataVigencia());

        // 7. Converter a lista de DTOs de faixas para as Entidades FaixaConsumo
        List<FaixaConsumo> entidadesFaixas = dto.faixas().stream().map(faixaDto -> {
            FaixaConsumo faixa = new FaixaConsumo();
            faixa.setConsumidor(faixaDto.consumidor());
            faixa.setConsumoInicial(faixaDto.consumoInicial());
            faixa.setConsumoFinal(faixaDto.consumoFinal());
            faixa.setValorUnitario(faixaDto.valorUnitario().floatValue()); // Converte BigDecimal para o float do seu model
            faixa.setTabelaTarifaria(tabela); // Amarração da Chave Estrangeira
            return faixa;
        }).collect(Collectors.toList());

        tabela.setFaixas(entidadesFaixas);

        // 8. O CascadeType.ALL vai salvar a tabela e todas as faixas juntas de uma vez só!
        tabelaTarifariaRepository.save(tabela);
    }

    private void validarRegrasDasFaixas(String categoria, List<FaixaConsumoRequestDTO> faixas) {
        // Garantir que as faixas estão ordenadas para validar a sequência lógica
        faixas.sort(Comparator.comparing(FaixaConsumoRequestDTO::consumoInicial));

        // Regra A: Cobertura Mínima Completa (Toda categoria obrigatoriamente deve começar em 0)
        if (faixas.get(0).consumoInicial() != 0) {
            throw new IllegalArgumentException("Erro na categoria " + categoria + ": O consumo inicial da primeira faixa deve ser 0 m³.");
        }

        // Regra B: Ordem válida e Não sobreposição (Sem frestas ou sobreposições)
        for (int i = 0; i < faixas.size() - 1; i++) {
            FaixaConsumoRequestDTO atual = faixas.get(i);
            FaixaConsumoRequestDTO proxima = faixas.get(i + 1);

            if (atual.consumoInicial() >= atual.consumoFinal()) {
                throw new IllegalArgumentException("Erro na categoria " + categoria + ": O consumo inicial não pode ser maior ou igual ao final.");
            }

            // Se a faixa atual termina em 10, a próxima deve começar rigorosamente em 11
            if (proxima.consumoInicial() != atual.consumoFinal() + 1) {
                throw new IllegalArgumentException("Erro de continuidade na categoria " + categoria + ": Existe um buraco ou uma sobreposição entre " + atual.consumoFinal() + " e " + proxima.consumoInicial());
            }
        }

        // Regra C: Cobertura Máxima Suficiente (A última faixa deve obrigatoriamente cobrir até o infinito técnico: 99999)
        FaixaConsumoRequestDTO ultimaFaixa = faixas.get(faixas.size() - 1);
        if (ultimaFaixa.consumoFinal() != 99999) {
            throw new IllegalArgumentException("Erro na categoria " + categoria + ": A última faixa deve terminar em 99999 m³ para garantir cobertura infinita.");
        }
    }
}