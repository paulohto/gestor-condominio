package com.gestorcondominio.mssindico.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResidencialDTO {

    @NotEmpty(message = "Id não pode ser nulo ou em branco")
    private Long id;
    @NotEmpty(message = "Nome não pode ser nulo ou em branco")
    private String nome;
    // outros campos e getters/setters

    public ResidencialDTO() {
    }

    public ResidencialDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
