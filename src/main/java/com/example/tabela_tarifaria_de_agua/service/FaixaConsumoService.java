package com.example.tabela_tarifaria_de_agua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabela_tarifaria_de_agua.dto.AtualizarPrecoFaixaDTO;
import com.example.tabela_tarifaria_de_agua.model.FaixaConsumo;
import com.example.tabela_tarifaria_de_agua.repository.FaixaConsumoRepository;

@Service
public class FaixaConsumoService {

    @Autowired
    private FaixaConsumoRepository faixaConsumoRepository;

    @Transactional 
    public void editarFaixaConsumo(Integer id, AtualizarPrecoFaixaDTO novoValorUnitario) {
        
        FaixaConsumo faixaExistente = faixaConsumoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Faixa de consumo com o ID " + id + " não foi encontrada."));

        
        faixaExistente.setValorUnitario(novoValorUnitario.novoValorUnitario());
        

        faixaConsumoRepository.save(faixaExistente);
    }
}