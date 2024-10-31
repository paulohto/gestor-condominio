package com.gestorcondominio.msunidades.dto;

import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.entity.Veiculo;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VeiculoDTO {

    private Long id;

    @NotEmpty(message = "O tipo de veículo não pode ser nulo ou em branco")
    @Size(min = 2, max = 50, message = "O tipo de veículo deve ter entre 2 e 50 caracteres")
    private String tipo;

    @NotEmpty(message = "A marca do veículo não pode ser nula ou em branco")
    @Size(min = 2, max = 50, message = "A marca do veículo deve ter entre 2 e 50 caracteres")
    private String marca;

    @NotEmpty(message = "O modelo do veículo não pode ser nulo ou em branco")
    @Size(min = 2, max = 50, message = "O modelo do veículo deve ter entre 2 e 50 caracteres")
    private String modelo;

    @NotEmpty(message = "A cor do veículo não pode ser nula ou em branco")
    @Size(min = 2, max = 50, message = "A cor do veículo deve ter entre 2 e 50 caracteres")
    private String cor;

    @NotEmpty(message = "A placa do veículo não pode ser nula ou em branco")
    @Pattern(regexp = "^[A-Za-z0-9]{7}$", message = "A placa deve ter exatamente 7 caracteres alfanuméricos")
    private String placa;

    private String unidadeEndereco;
    @NotNull(message = "A unidade não pode ser nula")
    private Unidade unidadeInfo;

    public VeiculoDTO() {}

    public VeiculoDTO(
            Long id,
            String tipo, String marca, String modelo,
            String cor, String placa,
            String unidadeEndereco, Unidade unidade
    ) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.placa = placa;

        this.unidadeEndereco = unidadeEndereco;
        this.unidadeInfo = unidade;
    }

    public VeiculoDTO(Veiculo veiculo) {
        this(
                veiculo.getId(),
                veiculo.getTipo(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getPlaca(),

                veiculo.getUnidadeEndereco(),
                veiculo.getUnidadeInfo()
        );
    }

    public static VeiculoDTO fromEntity(Veiculo entity) {
        return new VeiculoDTO(
                entity.getId(),
                entity.getTipo(),
                entity.getMarca(),
                entity.getModelo(),
                entity.getCor(),
                entity.getPlaca(),

                entity.getUnidadeEndereco(),
                entity.getUnidadeInfo()
        );
    }

    public static Veiculo toEntity(VeiculoDTO dto) {
        return new Veiculo(
                dto.getId(),
                dto.getTipo(),
                dto.getMarca(),
                dto.getModelo(),
                dto.getCor(),
                dto.getPlaca(),

                dto.getUnidadeEndereco(),
                dto.getUnidadeInfo()
        );
    }

    public static Veiculo mapperDTOtoEntity(VeiculoDTO dto, Veiculo entity) {
        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        entity.setMarca(dto.getMarca());
        entity.setModelo(dto.getModelo());
        entity.setCor(dto.getCor());
        entity.setPlaca(dto.getPlaca());

        entity.setUnidadeEndereco(dto.getUnidadeEndereco());
        entity.setUnidadeInfo(dto.getUnidadeInfo());
        return entity;
    }
}
