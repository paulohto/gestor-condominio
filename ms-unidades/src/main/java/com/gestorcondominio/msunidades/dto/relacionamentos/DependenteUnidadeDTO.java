package com.gestorcondominio.msunidades.dto.relacionamentos;

import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.dto.resumos.UnidadeResumoDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DependenteUnidadeDTO {

    private Long id;

   // @NotEmpty(message = "O nome não pode ser nulo ou em branco")
    private String nome;

    private String img;

   // @NotEmpty(message = "O parentesco não pode ser nulo ou em branco")
    private String vinculo;

    //@NotNull(message = "A data de nascimento não pode ser nula")
    private LocalDate nascimento;
    private String telefone;
    private String email;

    private String unidadeEndereco; // Novo campo para o Numero da unidade
    //private UnidadeDTO unidade1;
    private UnidadeResumoDTO unidadeInfo;
    //private UnidadeResumoDTO2 unidade;
    //private UnidadeResumoDTO2 unidadeInfo;

//    private Long unidadeId;

//    private int unidadeNumero;
//    private String bloco;
//    private String nomeResponsavel;

    public DependenteUnidadeDTO() {}

    public DependenteUnidadeDTO(
            Long id,
            String nome,
            String img,
            String vinculo,
            LocalDate nascimento,
            String telefone,
            String email,
            String unidadeEndereco, // Novo campo para o Numero da unidade
            UnidadeResumoDTO unidadeInfo
    ) {
        this.id = id;
        this.nome = nome;
        this.img = img;
        this.vinculo = vinculo;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.email = email;
        this.unidadeEndereco = unidadeEndereco; //add
        this.unidadeInfo = unidadeInfo;
    }


    // Método para converter DTO em Entidade
    public static Dependente toEntity(DependenteUnidadeDTO dto, Unidade unidade){
        return new Dependente(
                //dto, unidade //ANTES
                dto.getId(),           // Long id
                dto.getNome(),        // String nome
                dto.getImg(),
                dto.getVinculo(),     // String vinculo
                dto.getNascimento(),  // LocalDate nascimento
                dto.getTelefone(),     // String telefone
                dto.getEmail(),       // String email
                dto.getUnidadeEndereco(), // String unidadeEndereco
                unidade               // Unidade unidade
        );
    }

    // Método para converter Entidade em DTO
    public static DependenteUnidadeDTO fromEntity(Dependente dependente) {
        return new DependenteUnidadeDTO(
                dependente.getId(),
                dependente.getNome(),
                dependente.getImg(),
                dependente.getVinculo(),
                dependente.getNascimento(),
                dependente.getTelefone(),
                dependente.getEmail(),

                dependente.getUnidadeEndereco(),
                //
                //dependente.getUnidadeInfo() != null ? UnidadeResumoDTO2.fromEntity(dependente.getUnidadeInfo()) : null
                dependente.getUnidadeInfo() != null ? UnidadeResumoDTO.fromEntity(dependente.getUnidadeInfo()) : null

        );
    }

    // # SEM USO - TESTE
//    // Método para atualizar uma entidade existente com os dados do DTO
//    public static Dependente mapperDtoToEntity(DependenteUnidadeDTO dto, Dependente entity, Unidade unidade) {
//        entity.setNome(dto.getNome());
//        entity.setVinculo(dto.getVinculo());
//        entity.setNascimento(dto.getNascimento());
//        entity.setTelefone(dto.getTelefone());
//        entity.setEmail(dto.getEmail());
//
//        //entity.setUnidade(dto.getUnidade());
//        // Converter DTO para entidade
//       // entity.setUnidade(unidade);
//        entity.setUnidadeInfo(unidade);
//        return entity;
//    }



}
