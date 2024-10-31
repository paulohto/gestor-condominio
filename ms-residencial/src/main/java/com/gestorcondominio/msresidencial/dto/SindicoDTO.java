package com.gestorcondominio.msresidencial.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SindicoDTO {

    @NotEmpty(message = "Id não pode ser nulo ou em branco")
    private Long id;
    @NotEmpty(message = "Nome não pode ser nulo ou em branco")
    private String nome;
    // outros campos e getters/setters

    public SindicoDTO() {
    }

    public SindicoDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
