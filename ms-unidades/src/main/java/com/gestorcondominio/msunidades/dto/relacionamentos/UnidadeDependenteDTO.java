package com.gestorcondominio.msunidades.dto.relacionamentos;

import com.gestorcondominio.msunidades.dto.PetDTO;
import com.gestorcondominio.msunidades.dto.VeiculoDTO;
import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Pet;
import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.entity.Veiculo;
import com.gestorcondominio.msunidades.entity.enums.AceitaPets;
import com.gestorcondominio.msunidades.entity.enums.CondicaoDoResidente;
import com.gestorcondominio.msunidades.entity.enums.StatusOcupado;
import com.gestorcondominio.msunidades.exception.ValidDate;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UnidadeDependenteDTO {

    private Long id;

    @NotNull(message = "O número da unidade não pode ser nulo")
    private String unidadeEndereco;

    @NotEmpty(message = "Bloco não pode ser nulo ou em branco")
    private String bloco;

  //  @NotEmpty(message = "Status da unidade não pode ser nulo ou em branco")
    private StatusOcupado statusOcupado;
   // @NotEmpty(message = "Política de Pets não pode ser nulo ou em branco")
    private AceitaPets aceitaPets;

    @NotNull(message = "A quantidade de vagas não pode ser nula")
    @Min(value = 0, message = "A quantidade de vagas não pode ser negativa")
    private Integer quantidadeVagas;
    private String vagas;

    @NotEmpty(message = "Nome do responsável não pode ser nulo ou em branco")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras e espaços")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nomeResidente;

    //
    private String img;
    private CondicaoDoResidente condicaoDoResidente;
    //

    @NotEmpty(message = "Telefone de contato não pode ser nulo ou em branco")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "O telefone deve estar no formato (DD) 00000-0000")
    private String telefone;

    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "O whatsapp deve estar no formato (DD) 00000-0000")
    private String whatsapp;

    @NotEmpty(message = "Email não pode ser nulo ou em branco")
    @Email(message = "Email inválido")
    private String email;

    @ValidDate(message = "A data não pode estar em branco. A data deve estar no formato YYYY-MM-DD")
    private LocalDate nascimento;

    @ValidDate(message = "A data de morador desde não pode estar em branco. A data deve estar no formato YYYY-MM-DD")
    private LocalDate moradorDesde;

    @NotNull(message = "A quantidade de moradores não pode ser nula")
    @Min(value = 0, message = "A quantidade de moradores não pode ser negativa")
    private Integer quantidadeMoradores;
    private Set<DependenteResumoDTO> dependentes;

    @NotNull(message = "A quantidade de veículos não pode ser nula")
    @Min(value = 0, message = "A quantidade de veículos não pode ser negativa")
    private Integer quantidadeVeiculos;
    private Set<VeiculoDTO> veiculos;

    @NotNull(message = "A quantidade de pets não pode ser nula")
    @Min(value = 0, message = "A quantidade de pets não pode ser negativa")
    private Integer quantidadePets;
    private Set<PetDTO> pets;

    // Construtor padrão
    public UnidadeDependenteDTO() {}

    // Construtor com parâmetros
//    public UnidadeDependenteDTO(Long unidadeId, Set<DependenteDTO> dependentes) {
//        this.unidadeId = unidadeId;
//        this.dependentes = dependentes;
//    }

    public UnidadeDependenteDTO(
            Long id,
            String unidadeEndereco, String bloco,
            StatusOcupado statusOcupado, AceitaPets aceitaPets,
            int quantidadeVagas, String vagas,

            String nomeResidente, String img, CondicaoDoResidente condicaoDoResidente,
            String telefone, String whatsapp, String email, LocalDate nascimento,
            LocalDate moradorDesde,

            int quantidadeMoradores, Set<DependenteResumoDTO> dependentes,
            int quantidadeVeiculos, Set<VeiculoDTO> veiculos,
            int quantidadePets, Set<PetDTO> pets
    ) {
        this.id = id;
        this.unidadeEndereco = unidadeEndereco;
        this.bloco = bloco;
        this.statusOcupado = statusOcupado;
        this.aceitaPets = aceitaPets;
        this.quantidadeVagas = quantidadeVagas;
        this.vagas = vagas;

        this.nomeResidente = nomeResidente;
        //
        this.condicaoDoResidente = condicaoDoResidente;
        this.img = img;
        //
        this.telefone = telefone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.nascimento = nascimento;
        this.moradorDesde = moradorDesde;
        this.quantidadeMoradores = quantidadeMoradores;

        this.dependentes = dependentes;
        this.quantidadeVeiculos = quantidadeVeiculos;
        this.veiculos = veiculos;

        this.quantidadePets = quantidadePets;
        this.pets = pets;
    }

    public static Unidade toEntity(
            UnidadeDependenteDTO dto, Set<Dependente> dependente,
            Set<Veiculo> veiculos, Set<Pet> pets
    ){
        return new Unidade(
                //dto, dependente
                dto.getId(),
                dto.getUnidadeEndereco(),
                dto.getBloco(),
                dto.getStatusOcupado(),
                dto.getAceitaPets(),
                dto.getQuantidadeVagas(),
                dto.getVagas(),

                dto.getNomeResidente(),
                dto.getImg(),
                dto.getCondicaoDoResidente(),
                dto.getTelefone(),
                dto.getWhatsapp(),
                dto.getEmail(),
                dto.getNascimento(),
                dto.getMoradorDesde(),
                dto.getQuantidadeMoradores(),

                dependente,
                dto.getQuantidadeVeiculos(),
                veiculos,

                dto.getQuantidadePets(),
                pets
        );
    }

    // Métodos para converter de Unidade para UnidadeDependenteDTO
    // Método para converter Entidade em DTO
    public static UnidadeDependenteDTO fromEntity(Unidade unidade) {
        return new UnidadeDependenteDTO(
                unidade.getId(),
                unidade.getUnidadeEndereco(),
                unidade.getBloco(),
                unidade.getStatusOcupado(),
                unidade.getAceitaPets(),
                unidade.getQuantidadeVagas(),
                unidade.getVagas(),

                unidade.getNomeResidente(),
                //
                unidade.getImg(),
                unidade.getCondicaoDoResidente(),
                //
                unidade.getTelefone(),
                unidade.getWhatsapp(),
                unidade.getEmail(),
                unidade.getNascimento(),
                unidade.getMoradorDesde(),
                unidade.getQuantidadeMoradores(),

                // Converter Set<Dependente> para Set<DependenteDTO>
                unidade.getDependentes() != null ? unidade.getDependentes().stream()
                        .map(DependenteResumoDTO::toResumoDTO)
                        .collect(Collectors.toSet()) : null,

                unidade.getQuantidadeVeiculos(),
                null,
                unidade.getQuantidadePets(),
                null

//                unidade.getDependentes() != null ? DependenteDTO.fromEntity(unidade.getDependentes()) : null
        );
    }

    // Método para atualizar uma entidade existente com os dados do DTO
    public static Unidade mapperDtoToEntity(UnidadeDependenteDTO dto, Unidade entity, Set<Dependente> dependente) {
        entity.setUnidadeEndereco(dto.getUnidadeEndereco());
        entity.setBloco(dto.getBloco());
        entity.setStatusOcupado(dto.getStatusOcupado());
        entity.setAceitaPets(dto.getAceitaPets());
        entity.setQuantidadeVagas(dto.getQuantidadeVagas());
        entity.setVagas(dto.getVagas());

        entity.setNomeResidente(dto.getNomeResidente());
        //
        entity.setImg(dto.getImg());
        entity.setCondicaoDoResidente(dto.getCondicaoDoResidente());
        //
        entity.setTelefone(dto.getTelefone());
        entity.setWhatsapp(dto.getWhatsapp());
        entity.setEmail(dto.getEmail());
        entity.setNascimento(dto.getNascimento());
        entity.setMoradorDesde(dto.getMoradorDesde());
        entity.setQuantidadeMoradores(dto.getQuantidadeMoradores());
        entity.setDependentes(dependente);
        return entity;
    }

}
