package br.com.projeto.bap.model;

import java.time.LocalDate;

public class Pessoa {

    private Long id;
    private String nomeCompleto;
    private String biografia;
    private LocalDate dataNascimento;
    private String fotoUrl;

    public Pessoa() {}
    
    public Pessoa(String nome, String bio, LocalDate data) {
        this.nomeCompleto = nome;
        this.biografia = bio;
        this.dataNascimento = data;
    }


    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    // NOVOS
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }    

}
