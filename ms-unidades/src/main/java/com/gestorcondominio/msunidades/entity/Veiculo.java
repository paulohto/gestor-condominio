package com.gestorcondominio.msunidades.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_veiculos")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;

    private String unidadeEndereco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_id")
    @JsonBackReference
    private Unidade unidadeInfo;

    public Veiculo() {}

    public Veiculo(
            Long id,
            String tipo, String marca, String modelo,
            String placa, String cor,
            String unidadeEndereco, Unidade unidade
    ) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.cor = cor;

        this.unidadeEndereco = unidadeEndereco;
        this.unidadeInfo = unidade;
    }
}
