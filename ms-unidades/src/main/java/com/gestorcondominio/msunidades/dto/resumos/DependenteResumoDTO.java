package com.gestorcondominio.msunidades.dto.resumos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gestorcondominio.msunidades.dto.DependenteDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  // Ignorar campos nulos
public class DependenteResumoDTO {
    private Long id;
    private String nome;
    private String img;
    private String vinculo;
    private LocalDate nascimento;
    private String telefone;
    private String email;
    private String unidadeEndereco;
    private UnidadeResumoDTO unidadeInfo;

    public DependenteResumoDTO() {
    }

    public DependenteResumoDTO(
            Long id,
            String nome, String img, String vinculo, LocalDate nascimento, String telefone, String email,
            String unidadeEndereco, UnidadeResumoDTO unidadeInfo
    ) {
        this.id = id;
        this.nome = nome;
        this.img = img;
        this.vinculo = vinculo;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.email = email;
        this.unidadeEndereco = unidadeEndereco;
        this.unidadeInfo = unidadeInfo;
    }

    /*** Resumo Simples para Dependentes ***/

    public static DependenteResumoDTO toResumoDTO(Dependente dependente) {
        return new DependenteResumoDTO(
                dependente.getId(),
                dependente.getNome(),
                //dependente.getImg(),
                null,
                dependente.getVinculo(),
                dependente.getNascimento(),
                dependente.getTelefone(),
                dependente.getEmail(),
                dependente.getUnidadeEndereco(),
                null
        );
    }

    /*** Resumo Completo (com informações de unidade)  ***/

    public static DependenteResumoDTO toResumoDTOWithUnidade(Dependente dependente) {
        DependenteResumoDTO dto = toResumoDTO(dependente); // Reaproveita o método simples

        // Verificar se a unidade não é nula e preencher apenas os campos desejados
        if (dependente.getUnidadeInfo() != null) {
            UnidadeResumoDTO unidadeResumoDTO = new UnidadeResumoDTO();
            unidadeResumoDTO.setUnidadeEndereco(dependente.getUnidadeInfo().getUnidadeEndereco()); // MUDAR UnidadeNumero
            unidadeResumoDTO.setBloco(dependente.getUnidadeInfo().getBloco());
            unidadeResumoDTO.setNomeResidente(dependente.getUnidadeInfo().getNomeResidente());
            unidadeResumoDTO.setTelefone(dependente.getUnidadeInfo().getTelefone());

            // Definir manualmente a unidade com os campos limitados
            dto.setUnidadeInfo(unidadeResumoDTO);
        }

        return dto;
    }

    public static Dependente toEntity(DependenteDTO dto, Dependente existingEntity) {
        if (existingEntity == null) {
            throw new IllegalArgumentException("Entidade existente não pode ser nula.");
        }
        // Cria uma nova instância de Dependente com os dados do DTO
        Dependente dependente = new Dependente(
                existingEntity.getId(), // Mantém o ID existente
                dto.getNome() != null ? dto.getNome() : existingEntity.getNome(),
                dto.getImg() != null ? dto.getImg() : existingEntity.getImg(),
                dto.getVinculo() != null ? dto.getVinculo() : existingEntity.getVinculo(),
                dto.getNascimento() != null ? dto.getNascimento() : existingEntity.getNascimento(),
                dto.getTelefone() != null ? dto.getTelefone() : existingEntity.getTelefone(),
                dto.getEmail() != null ? dto.getEmail() : existingEntity.getEmail(),
                dto.getUnidadeEndereco() != null ? dto.getUnidadeEndereco() : existingEntity.getUnidadeEndereco(),
                existingEntity.getUnidadeInfo() // Preserva a unidade associada
        );

        return dependente;
    }

//    public static Dependente mapperDTOtoEntity(DependenteResumoDTO dto, Dependente entity) {
//        entity.setNome(dto.getNome());
//        entity.setVinculo(dto.getVinculo());
//        entity.setNascimento(dto.getNascimento());
//        entity.setTelefone(dto.getTelefone());
//        entity.setEmail(dto.getEmail());
//        entity.setUnidadeEndereco(dto.getUnidadeEndereco()); //add
//        return entity;
//    }

    public Dependente mapperDTOtoEntity2(Dependente dependente, Unidade novaUnidade) {
        if (novaUnidade != null) {
            dependente.atualizarUnidade(novaUnidade);  // Usa o método encapsulado para atualizar a unidade
            novaUnidade.getDependentes().add(dependente);  // Associação à nova unidade
        }

        // Atualiza outros campos do dependente usando métodos encapsulados
        dependente.atualizarNome(this.nome);
        dependente.atualizarImg(this.img);
        dependente.atualizarVinculo(this.vinculo);
        dependente.atualizarNascimento(this.nascimento);
        dependente.atualizarTelefone(this.telefone);
        dependente.atualizarEmail(this.email);
        dependente.atualizarUnidadeEndereco(this.unidadeEndereco);

        return dependente;
    }


}
