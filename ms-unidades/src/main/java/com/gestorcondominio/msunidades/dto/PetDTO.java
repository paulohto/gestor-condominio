package com.gestorcondominio.msunidades.dto;

import com.gestorcondominio.msunidades.entity.Pet;
import com.gestorcondominio.msunidades.entity.Unidade;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PetDTO {

    private Long id;

    @NotEmpty(message = "O nome do pet não pode ser nulo ou em branco")
    @Size(min = 2, max = 50, message = "O nome do pet deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotEmpty(message = "O tipo do pet não pode ser nulo ou em branco")
    @Size(min = 2, max = 50, message = "O tipo do pet deve ter entre 2 e 50 caracteres")
    private String tipo;

    @NotEmpty(message = "A raça do pet não pode ser nula ou em branco")
    @Size(min = 2, max = 50, message = "A raça do pet deve ter entre 2 e 50 caracteres")
    private String raca;

    private String unidadeEndereco;

    @NotNull(message = "A unidade não pode ser nula")
    private Unidade unidadeInfo;

    public PetDTO() {}

    public PetDTO(Long id, String nome, String tipo, String raca, String unidadeEndereco, Unidade unidade) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.raca = raca;

        this.unidadeEndereco = unidadeEndereco;
        this.unidadeInfo = unidade;
    }

    public PetDTO(Pet pet) {
        this(
                pet.getId(),
                pet.getNome(),
                pet.getTipo(),
                pet.getRaca(),

                pet.getUnidadeEndereco(),
                pet.getUnidadeInfo()
        );
    }

    public static PetDTO fromEntity(Pet entity) {
        return new PetDTO(
                entity.getId(),
                entity.getNome(),
                entity.getTipo(),
                entity.getRaca(),

                entity.getUnidadeEndereco(),
                entity.getUnidadeInfo()
        );
    }

    public static Pet toEntity(PetDTO dto) {
        return new Pet(
                dto.getId(),
                dto.getNome(),
                dto.getTipo(),
                dto.getRaca(),

                dto.getUnidadeEndereco(),
                dto.getUnidadeInfo()
        );
    }

    public static Pet mapperDTOtoEntity(PetDTO dto, Pet entity) {
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setTipo(dto.getTipo());
        entity.setRaca(dto.getRaca());

        entity.setUnidadeEndereco(dto.getUnidadeEndereco());
        entity.setUnidadeInfo(dto.getUnidadeInfo());
        return entity;
    }
}
