package com.example.tabela_tarifaria_de_agua.controller;

import com.example.tabela_tarifaria_de_agua.dto.TabelaTarifariaRequestDTO;
import com.example.tabela_tarifaria_de_agua.service.TabelaTarifariaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tabelas-tarifarias") 
public class TabelaTarifariaController {

    @Autowired
    private TabelaTarifariaService tabelaTarifariaService; 

    @PostMapping("/criar-tabela") 
    public ResponseEntity<String> criarTabela(@Valid @RequestBody TabelaTarifariaRequestDTO payload) {
        
        
        tabelaTarifariaService.salvarTabelaCompleta(payload);
        
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Tabela tarifária cadastrada com sucesso com suas respectivas faixas!");
    }
}