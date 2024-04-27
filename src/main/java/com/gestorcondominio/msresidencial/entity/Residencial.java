package com.gestorcondominio.msresidencial.entity;

import com.gestorcondominio.msresidencial.dto.LazerDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Data
@Entity
@Table(name = "tb_residencial")
public class Residencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String cep;
    private String bairro;
    private String cidade;
    private String uf;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "residencial_lazer",
            joinColumns = @JoinColumn(name = "residencial_id"),
            inverseJoinColumns = @JoinColumn(name = "lazer_id")
    )
    Set<Lazer> lazeres = new HashSet<>();

    private BigDecimal valorCondominio;
    private Boolean elevador;

    private String empresaPortaria;
    private String empresaZeladoria;
    private String empresaVigilancia;
    private String empresaBoletos;

    private int quantidadeUnidades;
    private int quantidadePublico;

    private int quantidadeUnidadesUtilizamApp;
    private int quantidadeUnidadesComPet;
    private int quantidadeUnidadesComVeiculo;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant dataDeCriacao;

     public Residencial() {}

    public Residencial(
            Long id,
            String nome,
            String endereco,
            String cep,
            String bairro,
            String cidade,
            String uf,

            BigDecimal valorCondominio,

            boolean elevador,
            String empresaPortaria,
            String empresaZeladoria,
            String empresaVigilancia,
            String empresaBoletos,

            int quantidadeUnidades,
            int quantidadePublico,
            int quantidadeUnidadesUtilizamApp,
            int quantidadeUnidadesComPet,
            int quantidadeUnidadesComVeiculo,

            Instant dataDeCriacao
    ){
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.valorCondominio = valorCondominio;
        this.elevador = elevador;
        this.empresaPortaria = empresaPortaria;
        this.empresaZeladoria = empresaZeladoria;
        this.empresaVigilancia = empresaVigilancia;
        this.empresaBoletos = empresaBoletos;
        this.quantidadeUnidades = quantidadeUnidades;
        this.quantidadePublico = quantidadePublico;
        this.quantidadeUnidadesUtilizamApp = quantidadeUnidadesUtilizamApp;
        this.quantidadeUnidadesComPet = quantidadeUnidadesComPet;
        this.quantidadeUnidadesComVeiculo = quantidadeUnidadesComVeiculo;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Set<Lazer> getLazeres() {
        return lazeres;
    }

    @PrePersist
    public void prePersist() {
        dataDeCriacao = Instant.now();
    }

}
