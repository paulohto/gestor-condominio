package com.gestorcondominio.msunidades.dto.resumos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.entity.enums.AceitaPets;
import com.gestorcondominio.msunidades.entity.enums.CondicaoDoResidente;
import com.gestorcondominio.msunidades.entity.enums.StatusOcupado;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  // Ignorar campos nulos
public class UnidadeResumoDTO {
    private Long id;
    private String unidadeEndereco;
    private String bloco;
    //novos atributos UNIDADE
    private StatusOcupado statusOcupado;
    private AceitaPets aceitaPets;
    private Integer quantidadeVagas;
    private String vagas;
    //
    private String nomeResidente;
    //
    private String img;
    private CondicaoDoResidente condicaoDoResidente;
    //
    private String telefone;
    private String whatsapp;
    private String email;
    private LocalDate nascimento;
    private LocalDate moradorDesde;

    private List<DependenteResumoDTO> dependentes; // TESTE
    private Integer quantidadeMoradores;
    private Integer quantidadeVeiculos;
    private Integer quantidadePets;

    public UnidadeResumoDTO() {
    }

    public UnidadeResumoDTO(
            Long id,
            String unidadeEndereco, String bloco,
            StatusOcupado statusOcupado, AceitaPets aceitaPets,
            Integer quantidadeVagas, String vagas,
            String nomeResidente, String img,
            CondicaoDoResidente condicaoDoResponsavel,
            String telefone, String whatsapp, String email,
            LocalDate nascimento, LocalDate moradorDesde,
            Integer quantidadeMoradores, Integer quantidadeVeiculos, Integer quantidadePets
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
        this.condicaoDoResidente = condicaoDoResponsavel;
        this.img = img;
        //
        this.telefone = telefone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.nascimento = nascimento;
        this.moradorDesde = moradorDesde;
        this.quantidadeMoradores = quantidadeMoradores;
        this.quantidadeVeiculos = quantidadeVeiculos;

        this.quantidadePets = quantidadePets;
    }

    // Método para converter uma entidade Unidade para UnidadeResumoDTO
    public static UnidadeResumoDTO fromEntity(Unidade unidade) {
        return new UnidadeResumoDTO(
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
                unidade.getQuantidadeVeiculos(),
                unidade.getQuantidadePets()
        );
    }

    ///
    public UnidadeResumoDTO(
            //Long id,
            String unidadeEndereco, String bloco,
            StatusOcupado statusOcupado, AceitaPets aceitaPets,
            Integer quantidadeVagas, String vagas,
            String nomeResidente, String img,
            CondicaoDoResidente condicaoDoResponsavel,
            String telefone, String whatsapp, String email,
            LocalDate nascimento, LocalDate moradorDesde,
            Integer quantidadeMoradores, Integer quantidadeVeiculos, Integer quantidadePets
    ) {
        //this.id = id;
        this.unidadeEndereco = unidadeEndereco;
        this.bloco = bloco;
        this.statusOcupado = statusOcupado;
        this.aceitaPets = aceitaPets;
        this.nomeResidente = nomeResidente;
        this.quantidadeVagas = quantidadeVagas;
        this.vagas = vagas;
        //
        this.condicaoDoResidente = condicaoDoResponsavel;
        this.img = img;
        //
        this.telefone = telefone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.nascimento = nascimento;
        this.moradorDesde = moradorDesde;
        this.quantidadeMoradores = quantidadeMoradores;
        this.quantidadeVeiculos = quantidadeVeiculos;

        this.quantidadePets = quantidadePets;
    }

    /*** Resumo Simples para unidade ***/

    public static UnidadeResumoDTO toResumoDTO(Unidade unidade) {
        return new UnidadeResumoDTO(
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
                unidade.getQuantidadeVagas(),
                unidade.getQuantidadePets()
        );
    }

    /*** Resumo Completo (com informações de dependente)  ***/

    public static UnidadeResumoDTO toResumoDTOWithDependentes(Unidade unidade) {
        UnidadeResumoDTO dto = toResumoDTO(unidade); // Reaproveita o método simples

        // Verificar se a unidade possui dependentes
        if (unidade.getDependentes() != null) {
            dto.setDependentes(
                    unidade.getDependentes().stream()
                            .map(dependente -> {
                                DependenteResumoDTO dependenteResumo = new DependenteResumoDTO();
                                dependenteResumo.setId(dependente.getId());
                                dependenteResumo.setNome(dependente.getNome());
                                dependenteResumo.setVinculo(dependente.getVinculo());
                                dependenteResumo.setTelefone(dependente.getTelefone());
                                dependenteResumo.setEmail(dependente.getEmail());
                                dependenteResumo.setUnidadeEndereco(dependente.getUnidadeInfo().getUnidadeEndereco());
                                // Adicione outros campos se necessário
                                return dependenteResumo;
                            })
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }


}
