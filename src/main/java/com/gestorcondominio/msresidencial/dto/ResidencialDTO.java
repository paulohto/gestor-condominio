package com.gestorcondominio.msresidencial.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record ResidencialDTO(
        Long id,

        @NotEmpty(message = "Nome não pode ser nulo ou em branco")
        @Pattern(regexp = "^.{1,40}$", message = "Nome não pode ter mais de 40 caracteres.")
        String nome,

        @NotEmpty(message = "Endereço não pode ser nulo ou em branco")
        String endereco,

        @NotEmpty(message = "Cep não pode ser nulo ou em branco")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve ertar no formato 12345-678")
        String cep,

        @NotEmpty(message = "Bairro não pode ser nulo ou em branco")
        String bairro,

        @NotEmpty(message = "Cidade não pode ser nulo ou em branco")
        String cidade,

        @NotEmpty(message = "UF não pode ser nulo ou em branco")
        @Size(min = 2, max =2 , message = "O estado deve ter exatamente 2 caracteres")
        String uf,

        //Sindico sindico,

        //@JsonIgnore
        //List<String> lazer,

        List<Lazer> lazeres, // Lista de IDs dos tipos de lazer associados
        BigDecimal valorCondominio,

        Boolean elevador,
        String empresaPortaria,
        String empresaZeladoria,
        String empresaVigilancia,
        String empresaBoletos,

        @NotNull(message = "Quantidade de Unidades não pode ser nulo")
        @Positive(message = "A quantidade de Unidades deve ser um número positivo")
        int quantidadeUnidades,

        int quantidadePublico,

        int quantidadeUnidadesUtilizamApp,
        int quantidadeUnidadesComPet,
        int quantidadeUnidadesComVeiculo
) {

        public ResidencialDTO (Residencial residencial) {
            this(
                    residencial.getId(),
                    residencial.getNome(),
                    residencial.getEndereco(),
                    residencial.getCep(),
                    residencial.getBairro(),
                    residencial.getCidade(),
                    residencial.getUf(),

                    //residencial.getSindico()
                    residencial.getLazeres(),

//                    // Convertendo a lista de objetos Lazer em uma lista de IDs
//                    residencial.getLazeres().stream()
//                            .map(id -> new Lazer(id)) // Obtém o ID de cada objeto Lazer
//                            .collect(Collectors.toList()), // Coleta os IDs em uma lista

                    residencial.getValorCondominio(),

                    residencial.getElevador(),
                    residencial.getEmpresaPortaria(),
                    residencial.getEmpresaZeladoria(),
                    residencial.getEmpresaVigilancia(),
                    residencial.getEmpresaBoletos(),
                    residencial.getQuantidadeUnidades(),
                    residencial.getQuantidadePublico(),
                    residencial.getQuantidadeUnidadesUtilizamApp(),
                    residencial.getQuantidadeUnidadesComPet(),
                    residencial.getQuantidadeUnidadesComVeiculo()
            );
        }

        public static ResidencialDTO fromEntity(Residencial entity){
            return new ResidencialDTO(
                    entity.getId(),
                    entity.getNome(),
                    entity.getEndereco(),
                    entity.getCep(),
                    entity.getBairro(),
                    entity.getCidade(),
                    entity.getUf(),

                    //entity.getSindico()
                    //entity.getLazer(),
                    //entity.getLazeres(),
                    entity.getLazeres().stream()
                            .map(lazer -> new Lazer(lazer.getId(), ""))
                            .collect(Collectors.toList()),

                    entity.getValorCondominio(),

                    entity.getElevador(),
                    entity.getEmpresaPortaria(),
                    entity.getEmpresaZeladoria(),
                    entity.getEmpresaVigilancia(),
                    entity.getEmpresaBoletos(),
                    entity.getQuantidadeUnidades(),
                    entity.getQuantidadePublico(),
                    entity.getQuantidadeUnidadesUtilizamApp(),
                    entity.getQuantidadeUnidadesComPet(),
                    entity.getQuantidadeUnidadesComVeiculo()
            );
        }

        public static Residencial toEntity(ResidencialDTO dto){
            return new Residencial(
                    dto.id,
                    dto.nome,
                    dto.endereco,
                    dto.cep,
                    dto.bairro,
                    dto.cidade,
                    dto.uf,

                    //dto.sindico,
                    dto.lazeres,
                    dto.valorCondominio,

                    dto.elevador,
                    dto.empresaPortaria,
                    dto.empresaZeladoria,
                    dto.empresaVigilancia,
                    dto.empresaBoletos,
                    dto.quantidadeUnidades,
                    dto.quantidadePublico,
                    dto.quantidadeUnidadesUtilizamApp,
                    dto.quantidadeUnidadesComPet,
                    dto.quantidadeUnidadesComVeiculo
            );
        }

        public static Residencial mapperDTOtoEntity(ResidencialDTO dto, Residencial entity){
                    entity.setId(dto.id);
                    entity.setNome(dto.nome);
                    entity.setEndereco(dto.endereco);
                    entity.setCep(dto.cep);
                    entity.setBairro(dto.bairro);
                    entity.setCidade(dto.cidade);
                    entity.setUf(dto.uf);

                    //entity.setSindico(dto.sindico);
                    entity.setLazeres(dto.lazeres);

                    entity.setValorCondominio(dto.valorCondominio);
                    entity.setElevador(dto.elevador);
                    entity.setEmpresaPortaria(dto.empresaPortaria);
                    entity.setEmpresaZeladoria(dto.empresaZeladoria);
                    entity.setEmpresaVigilancia(dto.empresaVigilancia);
                    entity.setEmpresaBoletos(dto.empresaBoletos);
                    entity.setQuantidadeUnidades(dto.quantidadeUnidades);
                    entity.setQuantidadePublico(dto.quantidadePublico);
                    entity.setQuantidadeUnidadesUtilizamApp(dto.quantidadeUnidadesUtilizamApp);
                    entity.setQuantidadeUnidadesComPet(dto.quantidadeUnidadesComPet);
                    entity.setQuantidadeUnidadesComVeiculo(dto.quantidadeUnidadesComVeiculo);
                    return entity;
        }

    public void add(ResidencialDTO dto) {
    }


}
