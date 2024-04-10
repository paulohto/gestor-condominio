package com.gestorcondominio.msresidencial.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestorcondominio.msresidencial.dto.LazerDTO;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_lazer")
public class Lazer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @ManyToMany(mappedBy = "lazeres", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("lazeres")
    private List<Residencial> residenciais;
    //private List<Residencial> residenciais = new ArrayList<>();

    public Lazer() {
    }

    @JsonCreator
    public Lazer(@JsonProperty("id") Long id, @JsonProperty("descricao") String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

//    @JsonCreator
//    public Lazer(Long id, String descricao) {
//        super(); // da video-alua
//        this.id = id;
//        this.descricao = descricao;
//    }

    public Lazer(Long id) {
    }

//    @JsonCreator
    public static Lazer createFromId(Long id) {
        Lazer lazer = new Lazer();
        lazer.setId(id);
        return lazer;
    }


}
