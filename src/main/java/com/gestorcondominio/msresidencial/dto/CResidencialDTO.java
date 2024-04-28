package com.gestorcondominio.msresidencial.dto;

import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import com.gestorcondominio.msresidencial.repository.ILazerRepository;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CResidencialDTO {

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
    private List<CLazerDTO> lazeres = new ArrayList<>();
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

    public CResidencialDTO(){}

    public CResidencialDTO(
            Long id, String nome, String endereco, String cep, String bairro, String cidade, String uf,
            List<CLazerDTO> lazeres,
            BigDecimal valorCondominio, Boolean elevador, String empresaPortaria, String empresaZeladoria,
            String empresaVigilancia, String empresaBoletos, int quantidadeUnidades, int quantidadePublico,
            int quantidadeUnidadesUtilizamApp, int quantidadeUnidadesComPet, int quantidadeUnidadesComVeiculo
    ) {
        super(); // DICA DEV SUPER
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

    public CResidencialDTO (Residencial entity){
                this.id = entity.getId();
                this.nome = entity.getNome();
                this.endereco = entity.getEndereco();
                this.cep = entity.getCep();
                this.bairro = entity.getBairro();
                this.cidade = entity.getCidade();
                this.uf = entity.getUf();

//               this.lazeres = entity.getLazeres();

//                this.lazeres = entity.getLazeres().stream()
//                        .map(CLazerDTO::fromEntity)
//                        .collect(Collectors.toList());

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

    public CResidencialDTO(Residencial residencial, Set<Lazer> lazeres) {
        this(residencial);
        lazeres.forEach(lazer -> this.lazeres.add(new CLazerDTO(lazer)));
    }

    public List<CLazerDTO> getLazeres() {
        return lazeres;
    }


//    public static CResidencialDTO fromEntity(Residencial entity){
//        return new CResidencialDTO(
//                entity.getId(),
//                entity.getNome(),
//                entity.getEndereco(),
//                entity.getCep(),
//                entity.getBairro(),
//                entity.getCidade(),
//                entity.getUf(),
//                entity.getLazeres(),
//
////                entity.getLazeres().stream()
////                        .map(CLazerDTO::fromEntity)
////                        .collect(Collectors.toList()),
//
//                entity.getValorCondominio(),
//
//                entity.getElevador(),
//                entity.getEmpresaPortaria(),
//                entity.getEmpresaZeladoria(),
//                entity.getEmpresaVigilancia(),
//                entity.getEmpresaBoletos(),
//                entity.getQuantidadeUnidades(),
//                entity.getQuantidadePublico(),
//                entity.getQuantidadeUnidadesUtilizamApp(),
//                entity.getQuantidadeUnidadesComPet(),
//                entity.getQuantidadeUnidadesComVeiculo()
//        );
//    }

//    public static Residencial toEntity(CResidencialDTO dto){
//        return new Residencial(
//                dto.id,
//                dto.nome,
//                dto.endereco,
//                dto.cep,
//                dto.bairro,
//                dto.cidade,
//                dto.uf,
//
//                dto.lazeresId.stream()
//                        .map(LazerDTO::toEntity)
//                        .collect(Collectors.toList()),
//
//                dto.valorCondominio,
//                dto.elevador,
//                dto.empresaPortaria,
//                dto.empresaZeladoria,
//                dto.empresaVigilancia,
//                dto.empresaBoletos,
//                dto.quantidadeUnidades,
//                dto.quantidadePublico,
//                dto.quantidadeUnidadesUtilizamApp,
//                dto.quantidadeUnidadesComPet,
//                dto.quantidadeUnidadesComVeiculo
//        );
//    }


}
