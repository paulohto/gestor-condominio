package com.gestorcondominio.msunidades.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gestorcondominio.msunidades.entity.enums.CondicaoDoResidente;
import com.gestorcondominio.msunidades.entity.enums.AceitaPets;
import com.gestorcondominio.msunidades.entity.enums.StatusOcupado;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_unidades")
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unidadeEndereco;
    private String bloco;

    private StatusOcupado statusOcupado;
    private AceitaPets aceitaPets;
    private int quantidadeVagas;
    private String vagas;

    /** INICIO RESIDENTE **/
    private String nomeResidente;

    //novos atributos RESIDENTE
    private String img;
    private CondicaoDoResidente condicaoDoResidente;
    //

    private String telefone;
    private String whatsapp;
    private String email;
    private LocalDate nascimento;
    private LocalDate moradorDesde;

    //DEPENDENTES
    private int quantidadeMoradores;
    @OneToMany(mappedBy = "unidadeInfo", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Dependente> dependentes = new HashSet<>();

    //VEICULOS
    private int quantidadeVeiculos;
    @OneToMany(mappedBy = "unidadeInfo", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Veiculo> veiculos = new HashSet<>();

    //PETS
    private int quantidadePets;
    @OneToMany(mappedBy = "unidadeInfo",fetch = FetchType.EAGER /*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    @JsonIgnore
    private Set<Pet> pets = new HashSet<>();

    /** FIM RESIDENTE **/

    // APLICAÇÃO FUTURA
//    private Set<String> notificacao = new HashSet<>();
//    private Set<Funcionario> funcionario = new HashSet<>();
//    private Residencial residencial;
//    private Sindico sindico;

    public Unidade(){}

    public Unidade(
            Long id, String unidadeEndereco, String bloco,
            StatusOcupado statusOcupado, AceitaPets aceitaPets,
            int quantidadeVagas, String vagas,
            String nomeResidente, String img, CondicaoDoResidente condicaoDoResidente,
            String telefone, String whatsapp, String email,
            LocalDate nascimento, LocalDate moradorDesde,
            int quantidadeMoradores, Set<Dependente> dependentes,
            int quantidadeVeiculos, Set<Veiculo> veiculos,
            int quantidadePets, Set<Pet> pets
    ) {
        this.id = id;
        this.unidadeEndereco = unidadeEndereco;
        this.bloco = bloco;
        this.nomeResidente = nomeResidente;
        this.statusOcupado = statusOcupado;
        this.aceitaPets = aceitaPets;
        this.quantidadeVagas = quantidadeVagas;
        this.vagas = vagas;

        this.condicaoDoResidente = condicaoDoResidente;
        this.img = img;

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

    public int getQuantidadeVagas() {
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

    public int getQuantidadeMoradores() {
        return quantidadeMoradores;
    }

    public Set<Dependente> getDependentes() {
        return dependentes;
    }

    public int getQuantidadeVeiculos() {
        return quantidadeVeiculos;
    }

    public Set<Veiculo> getVeiculos() {
        return veiculos;
    }

    public int getQuantidadePets() {
        return quantidadePets;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    //SETTER
    public void setUnidadeEndereco(String unidadeEndereco) {
        this.unidadeEndereco = unidadeEndereco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public void setStatusOcupado(StatusOcupado statusOcupado) {
        this.statusOcupado = statusOcupado;
    }

    public void setAceitaPets(AceitaPets aceitaPets) {
        this.aceitaPets = aceitaPets;
    }

    public void setQuantidadeVagas(int quantidadeVagas) {
        this.quantidadeVagas = quantidadeVagas;
    }

    public void setVagas(String vagas) {
        this.vagas = vagas;
    }

    public void setNomeResidente(String nomeResidente) {
        this.nomeResidente = nomeResidente;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setCondicaoDoResidente(CondicaoDoResidente condicaoDoResidente) {
        this.condicaoDoResidente = condicaoDoResidente;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public void setMoradorDesde(LocalDate moradorDesde) {
        this.moradorDesde = moradorDesde;
    }

    public void setQuantidadeMoradores(int quantidadeMoradores) {
        this.quantidadeMoradores = quantidadeMoradores;
    }

    public void setDependentes(Set<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

    public void setQuantidadeVeiculos(int quantidadeVeiculos) {
        this.quantidadeVeiculos = quantidadeVeiculos;
    }

    public void setVeiculos(Set<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public void setQuantidadePets(int quantidadePets) {
        this.quantidadePets = quantidadePets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unidade unidade = (Unidade) o;
        return id != null && id.equals(unidade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
