package com.example.tabela_tarifaria_de_agua.repository;

import com.example.tabela_tarifaria_de_agua.model.FaixaConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaixaConsumoRepository extends JpaRepository<FaixaConsumo, Integer> {
    // Herdando o JpaRepository, o Spring já cria todos os métodos básicos (salvar, deletar, buscar)
}