package com.gestorcondominio.msresidencial.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import jakarta.validation.constraints.NotEmpty;

public record LazerDTO(

        Long id,

        @NotEmpty(message = "Descrição não pode ser nulo ou em branco")
        String descricao
) {

    public LazerDTO {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do lazer não pode ser nula ou vazia");
        }
    }

    public static Lazer createFromId(Long id) {
        return new Lazer(id);
    }

    public LazerDTO (Lazer lazer) {
        this(
                lazer.getId(),
                lazer.getDescricao()
        );
    }

    public static LazerDTO fromEntity(Lazer entity) {
        return new LazerDTO(
                entity.getId(),
                entity.getDescricao()
        );
    }

    public static Lazer toEntity(LazerDTO dto){
        return new Lazer(
                dto.id,
                dto.descricao
        );
    }

    public static Lazer mapperDTOtoEntity(LazerDTO dto, Lazer entity) {
        entity.setId(dto.id);
        entity.setDescricao(dto.descricao);
        return entity;
    }


}
