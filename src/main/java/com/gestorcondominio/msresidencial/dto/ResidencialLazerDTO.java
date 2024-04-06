package com.gestorcondominio.msresidencial.dto;

import com.gestorcondominio.msresidencial.entity.Residencial;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record ResidencialLazerDTO(
        Long idResidencial,
        List<Long> idsLazeres
) {

    public ResidencialLazerDTO {
        Objects.requireNonNull(idResidencial, "ID do residencial não pode ser nulo");
    }

    public Residencial toEntity() {
        Residencial residencial = new Residencial();
        residencial.setId(idResidencial);
        // A lista de lazeres será tratada no serviço ao carregar os lazeres com base nos IDs
        return residencial;
    }

    public static ResidencialLazerDTO fromEntity(Residencial residencial) {
        Objects.requireNonNull(residencial, "Residencial não pode ser nulo");
        // Neste caso, como não temos a informação dos IDs dos lazeres, vamos passar uma lista vazia
        return new ResidencialLazerDTO(residencial.getId(), Collections.emptyList());
    }
}
