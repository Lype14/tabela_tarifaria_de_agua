package com.example.tabela_tarifaria_de_agua.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tabela_tarifaria_de_agua.dto.CalculoRequestDTO;
import com.example.tabela_tarifaria_de_agua.dto.CalculoRespostaDTO;
import com.example.tabela_tarifaria_de_agua.dto.DetalheFaixaFaturadaDTO;
import com.example.tabela_tarifaria_de_agua.dto.LimitesFaixaDTO;
import com.example.tabela_tarifaria_de_agua.model.FaixaConsumo;
import com.example.tabela_tarifaria_de_agua.model.TabelaTarifaria;
import com.example.tabela_tarifaria_de_agua.repository.TabelaTarifariaRepository;

@Service
public class CalculoService {

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    public CalculoRespostaDTO calcularContaAgua(CalculoRequestDTO request) {
        int consumoTotal = request.consumo();
        String tipoConsumidor = request.categoria();

        TabelaTarifaria tabelaAtiva = tabelaTarifariaRepository.findByAtivoTrue()
            .orElseThrow(() -> new IllegalStateException("Nenhuma tabela tarifária está ativa no sistema no momento."));

        List<DetalheFaixaFaturadaDTO> detalhamento = new ArrayList<>();
        BigDecimal valorTotalGeral = BigDecimal.ZERO;

        List<FaixaConsumo> faixasDoConsumidor = tabelaAtiva.getFaixas().stream()
        .filter(f -> f.getConsumidor().toString().equalsIgnoreCase(tipoConsumidor))
        .sorted(Comparator.comparing(FaixaConsumo::getConsumoInicial)) 
        .toList();
       
        if (faixasDoConsumidor.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma faixa encontrada para a categoria: " + tipoConsumidor);
        }

        for (FaixaConsumo faixa : faixasDoConsumidor) {
            int limiteInicial = faixa.getConsumoInicial();
            int limiteFinal = faixa.getConsumoFinal();

            if (consumoTotal < limiteInicial) {
                break;
            }

            int consumoNaFaixa;
            if (consumoTotal >= limiteFinal) {
                consumoNaFaixa = limiteFinal - limiteInicial;
            } else {
                consumoNaFaixa = consumoTotal - limiteInicial;
            }

            BigDecimal valorUnitario = BigDecimal.valueOf(faixa.getValorUnitario());
            BigDecimal subtotalFaixa = valorUnitario.multiply(BigDecimal.valueOf(consumoNaFaixa));
            
            valorTotalGeral = valorTotalGeral.add(subtotalFaixa);

            LimitesFaixaDTO limites = new LimitesFaixaDTO(limiteInicial, limiteFinal);

            detalhamento.add(new DetalheFaixaFaturadaDTO(
                limites,
                consumoNaFaixa,
                valorUnitario,
                subtotalFaixa
            ));

            if (consumoTotal <= limiteFinal) {
                break;
            }
        }

        return new CalculoRespostaDTO(
            tipoConsumidor,
            consumoTotal,
            valorTotalGeral,
            detalhamento
        );
    }
}