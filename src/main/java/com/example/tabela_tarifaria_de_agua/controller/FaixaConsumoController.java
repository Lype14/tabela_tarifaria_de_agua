package com.example.tabela_tarifaria_de_agua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tabela_tarifaria_de_agua.dto.AtualizarPrecoFaixaDTO;
import com.example.tabela_tarifaria_de_agua.service.FaixaConsumoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/faixa-consumo")
public class FaixaConsumoController {

    @Autowired
    private FaixaConsumoService faixaConsumoService;


    @PutMapping("/{id}")
    public ResponseEntity<String> editarFaixa(@PathVariable Integer id,@Valid @RequestBody AtualizarPrecoFaixaDTO novoValorUnitario){
        
        faixaConsumoService.editarFaixaConsumo(id,novoValorUnitario);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Faixa de consumo editada com sucesso");
    }

}
