package com.example.tabela_tarifaria_de_agua.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_faixa_consumo")
@Getter
@Setter
public class FaixaConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Consumidor consumidor;

    @Column(name = "consumo_inicial", nullable = false)
    private Integer consumoInicial;

    @Column(name = "consumo_final", nullable = false)
    private Integer consumoFinal;

    @Column(name = "valor_unitario", nullable = false)
    private float valorUnitario;

    @ManyToOne
    @JoinColumn(name = "tabela_tarifaria_id", nullable = false)
    @JsonBackReference
    private TabelaTarifaria tabelaTarifaria;
}
