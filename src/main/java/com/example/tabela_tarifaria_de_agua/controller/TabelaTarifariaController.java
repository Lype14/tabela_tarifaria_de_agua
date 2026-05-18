package com.example.tabela_tarifaria_de_agua.controller;

import com.example.tabela_tarifaria_de_agua.dto.TabelaTarifariaRequestDTO;
import com.example.tabela_tarifaria_de_agua.model.TabelaTarifaria;
import com.example.tabela_tarifaria_de_agua.service.TabelaTarifariaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tabelas-tarifarias") 
public class TabelaTarifariaController {

    @Autowired
    private TabelaTarifariaService tabelaTarifariaService; 

    @PostMapping
    public ResponseEntity<String> criarTabela(@Valid @RequestBody TabelaTarifariaRequestDTO payload) {
        
        
        tabelaTarifariaService.salvarTabelaCompleta(payload);
        
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Tabela tarifária cadastrada com sucesso com suas respectivas faixas!");
    }

    @GetMapping
    public ResponseEntity<List<TabelaTarifaria>> listarTodas() {
        List<TabelaTarifaria> tabelas = tabelaTarifariaService.listarTodasAsTabelas();
        return ResponseEntity.ok(tabelas); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTabela(@PathVariable Integer id) {
        tabelaTarifariaService.excluirTabelaPorId(id);
        return ResponseEntity.ok("tabela tarifária e todas as suas faixas de consumo foram excluídas com sucesso!"); 
    }

}