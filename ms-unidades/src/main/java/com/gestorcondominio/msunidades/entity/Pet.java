package com.gestorcondominio.msunidades.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;
    private String raca;

    private String unidadeEndereco;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_id")
    @JsonBackReference
    private Unidade unidadeInfo;

    public Pet() {}

    public Pet(
            Long id,
            String nome, String tipo, String raca,
            String unidadeEndereco, Unidade unidade
    ) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.raca = raca;

        this.unidadeEndereco = unidadeEndereco;
        this.unidadeInfo = unidade;
    }
}
