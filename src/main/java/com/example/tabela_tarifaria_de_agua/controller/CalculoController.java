package com.example.tabela_tarifaria_de_agua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tabela_tarifaria_de_agua.dto.CalculoRequestDTO;
import com.example.tabela_tarifaria_de_agua.dto.CalculoRespostaDTO;
import com.example.tabela_tarifaria_de_agua.service.CalculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/calculos")
public class CalculoController {

    @Autowired
    private CalculoService calculoService;

    @PostMapping
    public ResponseEntity<CalculoRespostaDTO> calcularConta(@Valid @RequestBody CalculoRequestDTO request) {
        
        CalculoRespostaDTO resultado = calculoService.calcularContaAgua(request);
        return ResponseEntity.ok(resultado);
    }
}