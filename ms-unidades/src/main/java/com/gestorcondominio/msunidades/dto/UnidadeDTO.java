package com.gestorcondominio.msunidades.dto;

import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Pet;
import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.entity.Veiculo;
import com.gestorcondominio.msunidades.entity.enums.CondicaoDoResidente;
import com.gestorcondominio.msunidades.entity.enums.AceitaPets;
import com.gestorcondominio.msunidades.entity.enums.StatusOcupado;
import com.gestorcondominio.msunidades.exception.ValidDate;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UnidadeDTO {

    private Long id;

    @NotNull(message = "O número da unidade não pode ser nulo")
    private String unidadeEndereco;

    @NotEmpty(message = "Bloco não pode ser nulo ou em branco")
    private String bloco;

   // @NotEmpty(message = "Status da unidade não pode ser nulo ou em branco")
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

    //private Set<Dependente> dependentes;
    private List<DependenteResumoDTO> dependentes;

    @NotNull(message = "A quantidade de veículos não pode ser nula")
    @Min(value = 0, message = "A quantidade de veículos não pode ser negativa")
    private Integer quantidadeVeiculos;
    private Set<Veiculo> veiculos;

    @NotNull(message = "A quantidade de pets não pode ser nula")
    @Min(value = 0, message = "A quantidade de pets não pode ser negativa")
    private Integer quantidadePets;
    private Set<Pet> pets;

    public UnidadeDTO() {}

    public UnidadeDTO(
            Long id,
            String unidadeEndereco, String bloco,
            StatusOcupado statusOcupado, AceitaPets aceitaPets,
            int quantidadeVagas, String vagas,

            String nomeResidente,
            String img,
            CondicaoDoResidente condicaoDoResidente,

            String telefone, String whatsapp,String email,
            LocalDate nascimento,
            LocalDate moradorDesde,

            int quantidadeMoradores, List<DependenteResumoDTO> dependentes,
            int quantidadeVeiculos, Set<Veiculo> veiculos,

            int quantidadePets, Set<Pet> pets
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

    //GETTER
    public Long getId() {
        return id;
    }

    public String getUnidadeEndereco() {
        return unidadeEndereco;
    }

    public String getBloco() {
        return bloco;
    }

    public StatusOcupado getStatusOcupado() {
        return statusOcupado;
    }

    public AceitaPets getAceitaPets() {
        return aceitaPets;
    }

    public Integer getQuantidadeVagas() {
        return quantidadeVagas;
    }

    public String getVagas() {
        return vagas;
    }

    public String getNomeResidente() {
        return nomeResidente;
    }

    public String getImg() {
        return img;
    }

    public CondicaoDoResidente getCondicaoDoResidente() {
        return condicaoDoResidente;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public LocalDate getMoradorDesde() {
        return moradorDesde;
    }

    public Integer getQuantidadeMoradores() {
        return quantidadeMoradores;
    }

    public List<DependenteResumoDTO> getDependentes() {
        return dependentes;
    }

    public Integer getQuantidadeVeiculos() {
        return quantidadeVeiculos;
    }

    public Set<Veiculo> getVeiculos() {
        return veiculos;
    }

    public Integer getQuantidadePets() {
        return quantidadePets;
    }

    //SETTER
    public void setDependentes(List<DependenteResumoDTO> dependentes) {
        this.dependentes = dependentes;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public UnidadeDTO(Unidade unidade) {
        this(
                unidade.getId(),
                unidade.getUnidadeEndereco(),
                unidade.getBloco(),
                unidade.getStatusOcupado(),
                unidade.getAceitaPets(),
                unidade.getQuantidadeVagas(),
                unidade.getVagas(),

                unidade.getNomeResidente(),
                unidade.getImg(),
                unidade.getCondicaoDoResidente(),
                unidade.getTelefone(),
                unidade.getWhatsapp(),
                unidade.getEmail(),
                unidade.getNascimento(),
                unidade.getMoradorDesde(),
                unidade.getQuantidadeMoradores(),

                //unidade.getDependentes(),
                unidade.getDependentes().stream()
                        .map(DependenteResumoDTO::toResumoDTO)
                        .collect(Collectors.toList()),  // Converte Set<Dependente> para List<DependenteResumoDTO>

                unidade.getQuantidadeVeiculos(),
                unidade.getVeiculos(),

                unidade.getQuantidadePets(),
                unidade.getPets()
        );
    }

    public static UnidadeDTO fromEntity(Unidade entity) {
        return new UnidadeDTO(
                entity.getId(),
                entity.getUnidadeEndereco(),
                entity.getBloco(),
                entity.getStatusOcupado(),
                entity.getAceitaPets(),
                entity.getQuantidadeVagas(),
                entity.getVagas(),

                entity.getNomeResidente(),
                //
                entity.getImg(),
                entity.getCondicaoDoResidente(),
                //
                entity.getTelefone(),
                entity.getWhatsapp(),
                entity.getEmail(),
                entity.getNascimento(),
                entity.getMoradorDesde(),
                entity.getQuantidadeMoradores(),

                //entity.getDependentes(),
                entity.getDependentes().stream()
                        .map(DependenteResumoDTO::toResumoDTO)
                        .collect(Collectors.toList()),  // Converte Set<Dependente> para List<DependenteResumoDTO>

                entity.getQuantidadeVeiculos(),
                entity.getVeiculos(),

                entity.getQuantidadePets(),
                entity.getPets()
        );
    }

    public static Unidade toEntity(UnidadeDTO dto) {
        Unidade unidade = new Unidade();
        //unidade.setId(dto.getId());
        unidade.setUnidadeEndereco(dto.getUnidadeEndereco());
        unidade.setBloco(dto.getBloco());
        unidade.setStatusOcupado(dto.getStatusOcupado());
        unidade.setAceitaPets(dto.getAceitaPets());
        unidade.setQuantidadeVagas(dto.getQuantidadeVagas());
        unidade.setVagas(dto.getVagas());

        unidade.setNomeResidente(dto.getNomeResidente());
        //
        unidade.setImg(dto.getImg());
        unidade.setCondicaoDoResidente(dto.getCondicaoDoResidente());
        //
        unidade.setTelefone(dto.getTelefone());
        unidade.setWhatsapp(dto.getWhatsapp());
        unidade.setEmail(dto.getEmail());
        unidade.setNascimento(dto.getNascimento());
        unidade.setMoradorDesde(dto.getMoradorDesde());
        unidade.setQuantidadeMoradores(dto.getQuantidadeMoradores());

        // Converte List<DependenteResumoDTO> para Set<Dependente>
        unidade.setDependentes(dto.getDependentes().stream()
                .map(depDTO -> {
                    Dependente dependente = new Dependente();
                    //dependente.setNome(depDTO.getNome());
                    // Mapeie outros campos conforme necessário
                    return dependente;
                })
                .collect(Collectors.toSet()));

        unidade.setQuantidadeVeiculos(dto.getQuantidadeVeiculos());
        unidade.setVeiculos(dto.getVeiculos());

        unidade.setQuantidadePets(dto.getQuantidadePets());
        unidade.setPets(dto.getPets());

        return unidade;
    }

    public static Unidade mapperDTOtoEntity(UnidadeDTO dto, Unidade entity) {
       // entity.setId(dto.getId());
        entity.setUnidadeEndereco(dto.getUnidadeEndereco());
        entity.setBloco(dto.getBloco());
        entity.setAceitaPets(dto.getAceitaPets());
        entity.setStatusOcupado(dto.getStatusOcupado());
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

        // Atualizar dependentes
        entity.getDependentes().clear();
        dto.getDependentes().forEach(depDTO -> {
            Dependente dependente = new Dependente();
            //dependente.setNome(depDTO.getNome());
            // Adicionar à coleção da entidade
            entity.getDependentes().add(dependente);
        });

        entity.setQuantidadeVeiculos(dto.getQuantidadeVeiculos());
        entity.setVeiculos(dto.getVeiculos());

        entity.setQuantidadePets(dto.getQuantidadePets());
        entity.setPets(dto.getPets());

        return entity;
    }


}