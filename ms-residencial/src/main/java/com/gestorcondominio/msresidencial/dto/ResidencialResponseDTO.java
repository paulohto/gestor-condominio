package com.gestorcondominio.msresidencial.dto;

public class ResidencialResponseDTO {

    // CLASS COMPLEMENTAR PARA REDUZIR AS INFORMAÇÕES DO RESIDENCIAL NA CONSULTA
    private Long id;
    private String nome;
    private String bairro;
    private String cidade;
    private String uf;
    private int quantidadeUnidades;

    public ResidencialResponseDTO(Long id, String nome, String bairro, String cidade, String uf, int quantidadeUnidades) {
        this.id = id;
        this.nome = nome;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.quantidadeUnidades = quantidadeUnidades;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public int getQuantidadeUnidades() { return quantidadeUnidades; }
    public void setQuantidadeUnidades(int quantidadeUnidades) { this.quantidadeUnidades = quantidadeUnidades; }

}
