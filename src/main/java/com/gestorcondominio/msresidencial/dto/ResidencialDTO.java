package com.gestorcondominio.msresidencial.dto;

import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ResidencialDTO {

    private Long id;
    @NotEmpty(message = "Nome não pode ser nulo ou em branco")
    @Pattern(regexp = "^.{1,40}$", message = "Nome não pode ter mais de 40 caracteres.")
    private String nome;
    @NotEmpty(message = "Endereço não pode ser nulo ou em branco")
    private String endereco;
    @NotEmpty(message = "Cep não pode ser nulo ou em branco")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve ertar no formato 12345-678")
    private String cep;
    @NotEmpty(message = "Bairro não pode ser nulo ou em branco")
    private String bairro;
    @NotEmpty(message = "Cidade não pode ser nulo ou em branco")
    private String cidade;
    @NotEmpty(message = "UF não pode ser nulo ou em branco")
    @Size(min = 2, max =2 , message = "O estado deve ter exatamente 2 caracteres")
    private String uf;

    //Sindico sindico;
    private List<LazerDTO> lazeres = new ArrayList<>();
    private BigDecimal valorCondominio;
    private Boolean elevador;
    private String empresaPortaria;
    private String empresaZeladoria;
    private String empresaVigilancia;
    private String empresaBoletos;
    @NotNull(message = "Quantidade de Unidades não pode ser nulo")
    @Positive(message = "A quantidade de Unidades deve ser um número positivo")
    private int quantidadeUnidades;
    private int quantidadePublico;
    private int quantidadeUnidadesUtilizamApp;
    private int quantidadeUnidadesComPet;
    private int quantidadeUnidadesComVeiculo;

    public ResidencialDTO(){}

//    public ResidencialDTO(Long id, String nome, List<LazerDTO> lazereres) {
//    }

    public ResidencialDTO(
            Long id, String nome, String endereco, String cep, String bairro, String cidade, String uf,
            List<LazerDTO> lazeres,
            BigDecimal valorCondominio, Boolean elevador, String empresaPortaria, String empresaZeladoria,
            String empresaVigilancia, String empresaBoletos, int quantidadeUnidades, int quantidadePublico,
            int quantidadeUnidadesUtilizamApp, int quantidadeUnidadesComPet, int quantidadeUnidadesComVeiculo
    ) {
        //super(); // DICA DEV SUPER
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;

        this.lazeres = lazeres;

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

    public ResidencialDTO(Residencial entity){
                this.id = entity.getId();
                this.nome = entity.getNome();
                this.endereco = entity.getEndereco();
                this.cep = entity.getCep();
                this.bairro = entity.getBairro();
                this.cidade = entity.getCidade();
                this.uf = entity.getUf();
                this.valorCondominio = entity.getValorCondominio();
                this.elevador = entity.getElevador();
                this.empresaPortaria = entity.getEmpresaPortaria();
                this.empresaZeladoria = entity.getEmpresaZeladoria();
                this.empresaVigilancia = entity.getEmpresaVigilancia();
                this.empresaBoletos = entity.getEmpresaBoletos();
                this.quantidadeUnidades = entity.getQuantidadeUnidades();
                this.quantidadePublico = entity.getQuantidadePublico();
                this.quantidadeUnidadesUtilizamApp = entity.getQuantidadeUnidadesUtilizamApp();
                this.quantidadeUnidadesComPet = entity.getQuantidadeUnidadesComPet();
                this.quantidadeUnidadesComVeiculo = entity.getQuantidadeUnidadesComVeiculo();

                //DICA DEV SUPER
                //lazeres = entity.getLazeres().stream().map(x -> new CLazerDTO(x)).collect(Collectors.toList());
    }

    public ResidencialDTO(Residencial residencial, Set<Lazer> lazeres) {
        //this(residencial); // GUILHERME FIAP
        //lazeres.forEach(lazer -> this.lazeres.add(new LazerDTO(lazer))); // GUILHERME FIAP

        this.id = residencial.getId();
        this.nome = residencial.getNome();
        this.endereco = residencial.getEndereco();
        this.cep = residencial.getCep();
        this.bairro = residencial.getBairro();
        this.cidade = residencial.getCidade();
        this.uf = residencial.getUf();
        this.valorCondominio = residencial.getValorCondominio();
        this.elevador = residencial.getElevador();
        this.empresaPortaria = residencial.getEmpresaPortaria();
        this.empresaZeladoria = residencial.getEmpresaZeladoria();
        this.empresaVigilancia = residencial.getEmpresaVigilancia();
        this.empresaBoletos = residencial.getEmpresaBoletos();
        this.quantidadeUnidades = residencial.getQuantidadeUnidades();
        this.quantidadePublico = residencial.getQuantidadePublico();
        this.quantidadeUnidadesUtilizamApp = residencial.getQuantidadeUnidadesUtilizamApp();
        this.quantidadeUnidadesComPet = residencial.getQuantidadeUnidadesComPet();
        this.quantidadeUnidadesComVeiculo = residencial.getQuantidadeUnidadesComVeiculo();

        this.lazeres = lazeres.stream().map(LazerDTO::new).collect(Collectors.toList());
    }

    public List<LazerDTO> getLazeres() {
        return lazeres;
    }
    public void setLazeres(List<LazerDTO> lazeres) {

        this.lazeres = lazeres != null ? new ArrayList<>(lazeres) : new ArrayList<>();
        //this.lazeres = lazeres;
    }


}