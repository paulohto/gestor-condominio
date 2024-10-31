package com.gestorcondominio.msresidencial.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    //RELAÇÃO COM ENTIDADE LAZER
    @ManyToMany (fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
            name = "tb_residencial_lazer",
            joinColumns = @JoinColumn(name = "residencial_id"),
            inverseJoinColumns = @JoinColumn(name = "lazer_id")
    )
    //@JsonIgnoreProperties("residenciais") // Outra opção para evitar a recursão
    @JsonManagedReference
    private Set<Lazer> lazeres = new HashSet<>();

    // RELAÇÃO COM MS-SINDICO
    private Long sindicoId;
    private String sindicoNome;
    //private String sindicoTelefone;
    //private String sindicoEmail;

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

            //Set<Lazer> lazeres,
            Long sindicoId,
            String sindicoNome,

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

        //this.lazeres = lazeres; //DELETAR
        this.sindicoId = sindicoId;
        this.sindicoNome = sindicoNome;

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
    public void setLazeres(Set<Lazer> lazeres) { this.lazeres = lazeres; }

    @PrePersist
    public void prePersist() {
        dataDeCriacao = Instant.now();
    }

    // Apesar de ter a @Data foi necessário incluir para evitar recursão
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Residencial that = (Residencial) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
