package br.com.projeto.bap.model;

import java.util.ArrayList;
import java.util.List;

// ABSTRACT: Não podemos instanciar "uma mídia" genérica, tem que ser Livro ou Filme.
public abstract class Midia {
    
    protected Long id;
    protected String titulo;
    protected Integer ano;
    protected String genero;
    protected String capaUrl;
    
    // Polimorfismo: Toda mídia tem uma lista de pessoas envolvidas
    // No Livro são Autores. No Filme são Diretores/Atores.
    protected List<Pessoa> equipe = new ArrayList<>();

    // Getters e Setters (Comuns a todos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public String getCapaUrl() { return capaUrl; }
    public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }

    public List<Pessoa> getEquipe() { return equipe; }
    public void setEquipe(List<Pessoa> equipe) { this.equipe = equipe; }
    
    // Método abstrato: Obriga as filhas a implementarem uma descrição textual
    public abstract String getTipoFormatado();
}