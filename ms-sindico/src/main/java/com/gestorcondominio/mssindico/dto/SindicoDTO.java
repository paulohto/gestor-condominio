package com.gestorcondominio.mssindico.dto;

import com.gestorcondominio.mssindico.entity.Sindico;
import com.gestorcondominio.mssindico.exception.ValidDate;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;


import java.time.LocalDate;

public record SindicoDTO(

        Long id,
//        @NotEmpty(message = "Nome não pode ser nulo ou em branco")
//        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras e espaços")
//        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotEmpty(message = "CPF não pode ser nulo ou em branco")
        @CPF(message = "CPF inválido")
        String cpf,

        @Pattern(regexp = "\\d{14}", message = "CNPJ inválido. Digite apenas números sem espaço")
        String cnpj,

        @ValidDate(message = "A data não pode estar em branco. A data deve estar no formato YYYY-MM-DD")
        LocalDate nascimento,

        @NotEmpty(message = "Telefone de contato não pode ser nulo ou em branco")
        @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "O telefone deve estar no formato (DD) 00000-0000")
        String telefone,

        @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "O telefone deve estar no formato (DD) 00000-0000")
        String whatsapp,

        @NotEmpty(message = "Email não pode ser nulo ou em branco")
        @Email(regexp = ".+@.+\\..+", message = "e-mail inválido")
        String email,

        @NotEmpty(message = "Cep não pode ser nulo ou em branco")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve ertar no formato 12345-678")
        String cep,

        @NotEmpty(message = "Endereço não pode ser nulo ou em branco")
        String endereco,

        // RELAÇÃO COM MS-RESIDENCIAL
        Long residencialId,
        String residencialNome

        //private Papel papel
        //private Residencial residencial;
) {

    public SindicoDTO(Sindico sindico){
        this(
                sindico.getId(),
                sindico.getNome(),
                sindico.getCpf(),
                sindico.getCnpj(),
                sindico.getNascimento(),
                sindico.getTelefone(),
                sindico.getWhatsapp(),
                sindico.getEmail(),
                sindico.getCep(),
                sindico.getEndereco(),

                sindico.getResidencialId(),
                sindico.getResidencialNome()
        );
    }

    public static SindicoDTO fromEntity(Sindico entity){
        return new SindicoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getCnpj(),
                entity.getNascimento(),
                entity.getTelefone(),
                entity.getWhatsapp(),
                entity.getEmail(),
                entity.getCep(),
                entity.getEndereco(),

                entity.getResidencialId(),
                entity.getResidencialNome()
        );
    }

    public static Sindico toEntity(SindicoDTO dto){
        return new Sindico(
                dto.id(),
                dto.nome(),
                dto.cpf(),
                dto.cnpj(),
                dto.nascimento(),
                dto.telefone(),
                dto.whatsapp(),
                dto.email(),
                dto.cep(),
                dto.endereco(),

                dto.residencialId(),
                dto.residencialNome()
        );
    }

    public static Sindico mapperDTOtoEntity(SindicoDTO dto, Sindico entity){
        entity.setId(dto.id());
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setCnpj(dto.cnpj());
        entity.setNascimento(dto.nascimento());
        entity.setTelefone(dto.telefone());
        entity.setWhatsapp(dto.whatsapp());
        entity.setEmail(dto.email());
        entity.setCep(dto.cep());
        entity.setEndereco(dto.endereco());

        entity.setResidencialId(dto.residencialId());
        entity.setResidencialNome(dto.residencialNome());

                return entity;
    }

    /// TESTE
//    public Long getResidencialId() {
//        return residencialId;
//    }
//    public String getResidencialNome() {
//        return residencialNome;
//    }
//    public void setResidencial(ResidencialDTO residencial) {
//        residencial.getId();
//        residencial.getNome();
//    }
}
