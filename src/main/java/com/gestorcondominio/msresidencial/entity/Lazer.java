package com.gestorcondominio.msresidencial.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestorcondominio.msresidencial.dto.LazerDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_lazer")
public class Lazer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @ManyToMany(mappedBy = "lazeres" /*, fetch = FetchType.EAGER*/)
    @JsonIgnore
    private Set<Residencial> residenciais = new HashSet<>();

//    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
//    private Instant dataDeCriacao;

    public Lazer() {}

    public Lazer(Long id, String descricao /*, Instant dataDeCriacao*/) {
        super(); // DEV SUP - DICA DE AULA
        this.id = id;
        this.descricao = descricao;
        //this.dataDeCriacao = dataDeCriacao;
    }

    public Set<Residencial> getResidenciais(){
        return residenciais;
    }

//    @PrePersist
//    public void prePersist() {
//        dataDeCriacao = Instant.now();
//    }


}
