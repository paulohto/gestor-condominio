package com.gestorcondominio.msresidencial.dto;

import com.gestorcondominio.msresidencial.entity.Lazer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LazerDTO {
    private Long id;
    @NotEmpty(message = "Descrição não pode ser nulo ou em branco")
    private String descricao;

    public LazerDTO() {}

    public LazerDTO(Long id, String descricao) {
        //super(); // dica dev super
        this.id = id;
        this.descricao = descricao;
    }

    public LazerDTO(Lazer entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
    }


//    public static Lazer createFromId(Long id) {
//        return new Lazer(id);
//    }

//    public CLazerDTO(Lazer lazer) {
//        this(lazer.getId(), lazer.getDescricao());
//    }

    public static LazerDTO fromEntity(Lazer entity) {
        return new LazerDTO(
                entity.getId(),
                entity.getDescricao()
        );
    }

    public static Lazer toEntity(LazerDTO dto){
        return new Lazer(
                dto.getId(),
                dto.getDescricao()
        );
    }

    public Lazer mapperDTOtoEntity(LazerDTO dto, Lazer entity) {
        entity.setId(dto.getId());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

}
