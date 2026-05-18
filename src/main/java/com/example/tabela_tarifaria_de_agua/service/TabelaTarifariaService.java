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

@Service 
public class TabelaTarifariaService {

    @Autowired
    private TabelaTarifariaRepository tabelaTarifariaRepository;

    @Transactional 
    public void salvarTabelaCompleta(TabelaTarifariaRequestDTO dto) {
        
        Map<com.example.tabela_tarifaria_de_agua.model.Consumidor, List<FaixaConsumoRequestDTO>> faixasPorConsumidor = 
            dto.faixas().stream().collect(Collectors.groupingBy(FaixaConsumoRequestDTO::consumidor));

        for (Map.Entry<com.example.tabela_tarifaria_de_agua.model.Consumidor, List<FaixaConsumoRequestDTO>> entry : faixasPorConsumidor.entrySet()) {
            validarRegrasDasFaixas(entry.getKey().toString(), entry.getValue());
        }

        tabelaTarifariaRepository.findByAtivoTrue().ifPresent(tabelaAntiga -> {
            tabelaAntiga.setAtivo(false);
            tabelaTarifariaRepository.save(tabelaAntiga);
        });

        TabelaTarifaria tabela = new TabelaTarifaria();
        tabela.setNome(dto.nome());
        tabela.setDataVigencia(dto.dataVigencia());

        List<FaixaConsumo> entidadesFaixas = dto.faixas().stream().map(faixaDto -> {
            FaixaConsumo faixa = new FaixaConsumo();
            faixa.setConsumidor(faixaDto.consumidor());
            faixa.setConsumoInicial(faixaDto.consumoInicial());
            faixa.setConsumoFinal(faixaDto.consumoFinal());
            faixa.setValorUnitario(faixaDto.valorUnitario().floatValue()); 
            faixa.setTabelaTarifaria(tabela); 
            return faixa;
        }).collect(Collectors.toList());

        tabela.setFaixas(entidadesFaixas);

        tabelaTarifariaRepository.save(tabela);
    }

    private void validarRegrasDasFaixas(String categoria, List<FaixaConsumoRequestDTO> faixas) {
        faixas.sort(Comparator.comparing(FaixaConsumoRequestDTO::consumoInicial));

        if (faixas.get(0).consumoInicial() != 0) {
            throw new IllegalArgumentException("Erro na categoria " + categoria + ": O consumo inicial da primeira faixa deve ser 0 m³.");
        }

        for (int i = 0; i < faixas.size() - 1; i++) {
            FaixaConsumoRequestDTO atual = faixas.get(i);
            FaixaConsumoRequestDTO proxima = faixas.get(i + 1);

            if (atual.consumoInicial() >= atual.consumoFinal()) {
                throw new IllegalArgumentException("Erro na categoria " + categoria + ": O consumo inicial não pode ser maior ou igual ao final.");
            }

            if (proxima.consumoInicial() != atual.consumoFinal() + 1) {
                throw new IllegalArgumentException("Erro de continuidade na categoria " + categoria + ": Existe um buraco ou uma sobreposição entre " + atual.consumoFinal() + " e " + proxima.consumoInicial());
            }
        }

        FaixaConsumoRequestDTO ultimaFaixa = faixas.get(faixas.size() - 1);
        if (ultimaFaixa.consumoFinal() != 99999) {
            throw new IllegalArgumentException("Erro na categoria " + categoria + ": A última faixa deve terminar em 99999 m³ para garantir cobertura infinita.");
        }
    }

    @Transactional(readOnly = true) 
    public List<TabelaTarifaria> listarTodasAsTabelas() {
        return tabelaTarifariaRepository.findAll();
    }

    @Transactional 
    public void excluirTabelaPorId(Integer id) {

        if (!tabelaTarifariaRepository.existsById(id)) {
            throw new IllegalArgumentException("Não foi possível excluir: Tabela tarifária com o ID " + id + " não encontrada.");
        }
    
        tabelaTarifariaRepository.deleteById(id);
    }

}