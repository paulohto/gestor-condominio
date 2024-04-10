package com.gestorcondominio.msresidencial.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "residencial_lazer",
            joinColumns = @JoinColumn(name = "residencial_id"),
            inverseJoinColumns = @JoinColumn(name = "lazer_id")
    )
    @JsonIgnoreProperties("residenciais")
    private List<Lazer> lazeres; // precisa ser List<Lazer>
    //private List<Long> lazeres;
    //private List<Lazer> lazeres = new ArrayList<>();
    //private List<Long> lazeres = new ArrayList<>();

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

    // Construtor padrão sem argumentos
    public Residencial() {}

    // Construtor com argumentos
    public Residencial(
            Long id,
            String nome,
            String endereco,
            String cep,
            String bairro,
            String cidade,
            String uf,

            //List<String> lazer,
            //String lazer,
            //List<Long> lazer,
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
            int quantidadeUnidadesComVeiculo
    ){
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;

        //this.lazer = lazer;
        //this.lazeres = lazer;

       // this.lazeres = new ArrayList<>();
//        Lazer lazer1 = new Lazer();
//        this.lazeres.add(lazer1);

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
    }

    public Residencial(Long id, String nome, String endereco, String cep, String bairro, String cidade, String uf, List<Lazer> lazeres, BigDecimal valorCondominio, Boolean elevador, String empresaPortaria, String empresaZeladoria, String empresaVigilancia, String empresaBoletos, int quantidadeUnidades, int quantidadePublico, int quantidadeUnidadesUtilizamApp, int quantidadeUnidadesComPet, int quantidadeUnidadesComVeiculo) {
    }

}
