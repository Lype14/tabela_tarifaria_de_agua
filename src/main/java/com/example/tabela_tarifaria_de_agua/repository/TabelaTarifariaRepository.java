package com.example.tabela_tarifaria_de_agua.repository;

import com.example.tabela_tarifaria_de_agua.model.TabelaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifaria, Long> {
    
    Optional<TabelaTarifaria> findByAtivoTrue();
}