package com.gestorcondominio.mssindico.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Entity
@Table(name = "tb_sindico")
public class Sindico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String cnpj;
    private LocalDate nascimento;
    private String telefone;
    private String whatsapp;
    private String email;
    private String cep;
    private String endereco;

    // RELAÇÃO COM MS-RESIDENCIAL
    private Long residencialId;
    private String residencialNome;

    public Sindico (){}

    public Sindico(
            Long id, String nome, String cpf, String cnpj, LocalDate nascimento,
            String telefone, String whatsapp, String email,String cep, String endereco,
            Long residencialId,
            String residencialNome
    ) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.cep = cep;
        this.endereco = endereco;
        this.residencialId = residencialId;
        this.residencialNome = residencialNome;

        //this.residencial = residencial;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
