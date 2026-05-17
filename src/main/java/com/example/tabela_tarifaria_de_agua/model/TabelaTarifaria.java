package com.example.tabela_tarifaria_de_agua.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_tabela_tarifaria")
@Getter
@Setter
public class TabelaTarifaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;

    @Column(name = "data_vigencia", nullable = false)
    private LocalDate dataVigencia;

    @Column(nullable = false)
    private boolean ativo = true;

    //Vinculação das faixas (se deletar a tabela, todas a faixar relacionadas também serão deletadas)
    @OneToMany(mappedBy = "tabelaTarifaria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaixaConsumo> faixas = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDate.now();
    }
}
