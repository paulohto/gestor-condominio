package com.gestorcondominio.msunidades.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gestorcondominio.msunidades.dto.DependenteDTO;
import com.gestorcondominio.msunidades.dto.relacionamentos.DependenteUnidadeDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_dependentes")
public class Dependente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String img;
    private String vinculo;
    private LocalDate nascimento;
    private String telefone;
    private String email;

//    @Transient // Marca este campo como não persistido no banco de dados
//    @JsonInclude(JsonInclude.Include.NON_NULL) // Inclui o campo no JSON apenas se ele não for nulo
    private String unidadeEndereco;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_id")
    @JsonBackReference
    private Unidade unidadeInfo;

    public Dependente(){}

    public Dependente(
            Long id,
            String nome, String img, String vinculo, LocalDate nascimento,
            String telefone, String email,
            String unidadeEndereco, Unidade unidade
    ) {
        this.id = id;
        this.nome = nome;
        this.img = img;
        this.vinculo = vinculo;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.email = email;

        this.unidadeEndereco = unidadeEndereco;
        this.unidadeInfo = unidade;
    }

    // getters
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

    public Unidade getUnidadeInfo() {
        return unidadeInfo;
    }

    /** INICIO: Métodos de atualização específicos **/
    public void atualizarNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        }
    }

    public void atualizarImg(String img) {
        if (img != null && !img.trim().isEmpty()) {
            this.img = img;
        }
    }

    public void atualizarVinculo(String vinculo) {
        if (vinculo != null && !vinculo.trim().isEmpty()) {
            this.vinculo = vinculo;
        }
    }

    public void atualizarNascimento(LocalDate nascimento) {
        if (nascimento != null) {
            this.nascimento = nascimento;
        }
    }

    public void atualizarTelefone(String telefone) {
        if (telefone != null && !telefone.trim().isEmpty()) {
            this.telefone = telefone;
        }
    }

    public void atualizarEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        }
    }

    public void atualizarUnidadeEndereco(String unidadeEndereco) {
        if (unidadeEndereco != null && !unidadeEndereco.trim().isEmpty()) {
            this.unidadeEndereco = unidadeEndereco;
        }
    }

    public void atualizarUnidade(Unidade novaUnidade) {
        if (novaUnidade != null) {
            this.unidadeInfo = novaUnidade;
        }
    }
    /** FIM: Métodos de atualização específicos **/

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dependente that = (Dependente) o;
        return Objects.equals(id, that.id);
        //return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
