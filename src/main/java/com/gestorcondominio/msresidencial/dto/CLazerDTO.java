package com.gestorcondominio.msresidencial.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestorcondominio.msresidencial.entity.Lazer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CLazerDTO {
    private Long id;
    @NotEmpty(message = "Descrição não pode ser nulo ou em branco")
    private String descricao;

    public CLazerDTO() {}

    public CLazerDTO(Long id, String descricao) {
        super(); // dica dev super
        this.id = id;
        this.descricao = descricao;
    }

    public CLazerDTO(Lazer entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
    }


//    public static Lazer createFromId(Long id) {
//        return new Lazer(id);
//    }

//    public CLazerDTO(Lazer lazer) {
//        this(lazer.getId(), lazer.getDescricao());
//    }

    public static CLazerDTO fromEntity(Lazer entity) {
        return new CLazerDTO(
                entity.getId(),
                entity.getDescricao()
        );
    }

    public static Lazer toEntity(CLazerDTO dto){
        return new Lazer(
                dto.getId(),
                dto.getDescricao()
        );
    }

//    public Lazer mapperDTOtoEntity(CLazerDTO dto, Lazer entity) {
//        entity.setId(dto.getId());
//        entity.setDescricao(dto.getDescricao());
//        return entity;
//    }
}
