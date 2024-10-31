package com.gestorcondominio.msunidades.dto;

import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.dto.resumos.UnidadeResumoDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

//@Data
public class DependenteDTO {

    private Long id;

    @NotEmpty(message = "O nome não pode ser nulo ou em branco")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    private String img;

    @NotEmpty(message = "O parentesco não pode ser nulo ou em branco")
    private String vinculo;

    @NotNull(message = "A data de nascimento não pode ser nula")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate nascimento;

    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "O telefone deve estar no formato (DD) 00000-0000")
    private String telefone;

    @Email(message = "Email inválido")
    private String email;

    private String unidadeEndereco;

//    @NotNull(message = "A unidade não pode ser nula")
//    private UnidadeResumoDTO2 unidade;
    //private UnidadeResumoDTO2 unidadeInfo;
    private UnidadeResumoDTO unidadeInfo;

    public DependenteDTO() {}

    public DependenteDTO(
            Long id,
            String nome,
            String img,
            String vinculo,
            LocalDate nascimento,
            String telefone,
            String email,

            String unidadeEndereco,
            UnidadeResumoDTO unidadeInfo

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

    //GETTER
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getImg() {
        return img;
    }

    public String getVinculo() {
        return vinculo;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getUnidadeEndereco() {
        return unidadeEndereco;
    }

    public UnidadeResumoDTO getUnidadeInfo() {
        return unidadeInfo;
    }

    //SETTER
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUnidadeEndereco(String unidadeEndereco) {
        this.unidadeEndereco = unidadeEndereco;
    }

    public void setUnidadeInfo(UnidadeResumoDTO unidadeInfo) {
        this.unidadeInfo = unidadeInfo;
    }

    // Método estático para criar uma nova instância a partir da entidade
    public static DependenteDTO fromEntity(Dependente dependente) {
        UnidadeResumoDTO unidadeResumoDTO = UnidadeResumoDTO.fromEntity(dependente.getUnidadeInfo());
        return new DependenteDTO(
                dependente.getId(),
                dependente.getNome(),
                dependente.getImg(),
                dependente.getVinculo(),
                dependente.getNascimento(),
                dependente.getTelefone(),
                dependente.getEmail(),
                dependente.getUnidadeEndereco(),
                unidadeResumoDTO
        );
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

    public void dependente(DependenteDTO dependenteDTO){}

}
