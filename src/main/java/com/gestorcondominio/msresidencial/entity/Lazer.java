package com.gestorcondominio.msresidencial.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_lazer")
public class Lazer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @ManyToMany(mappedBy = "lazeres", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Residencial> residenciais = new HashSet<>();

//    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    private Instant dataDeCriacao;

    public Lazer() {}

    public Lazer(Long id, String descricao /*, Instant dataDeCriacao*/) {
        this.id = id;
        this.descricao = descricao;
        //this.dataDeCriacao = dataDeCriacao;
    }

    public Set<Residencial> getResidenciais(){
        return residenciais;
    }
    public void setResidenciais(Set<Residencial> residenciais) { this.residenciais = residenciais; }

//    @PrePersist
//    public void prePersist() {
//        dataDeCriacao = Instant.now();
//    }

    // Apesar de ter a @Data foi necessário incluir para evitar recursão
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lazer lazer = (Lazer) o;
        return id != null && id.equals(lazer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
