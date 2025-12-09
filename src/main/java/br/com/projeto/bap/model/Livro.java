package br.com.projeto.bap.model;

import java.util.ArrayList;
import java.util.List;

public class Livro extends Midia{
   // private Long id;
   // private String titulo;
    private String editora;
    private String isbn;
    //private Integer ano;
   // private String genero;
    private String sinopse;
   // private String capaUrl;
    
    // Relacionamento: Um livro tem uma lista de Autores (Pessoas)
    private List<Pessoa> autores = new ArrayList<>();

    public Livro() {}

    @Override
    public String getTipoFormatado() {
        return "Livro (ISBN: " + isbn + ")";
    }

    // // Getters e Setters
    // public Long getId() { return id; }
    // public void setId(Long id) { this.id = id; }
    
    // public String getTitulo() { return titulo; }
    // public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    // public Integer getAno() { return ano; }
    // public void setAno(Integer ano) { this.ano = ano; }

    // public String getGenero() { return genero; }
    // public void setGenero(String genero) { this.genero = genero; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public List<Pessoa> getAutores() { return autores; }
    public void setAutores(List<Pessoa> autores) { this.autores = autores; }


    // public String getCapaUrl() { return capaUrl; }
    // public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }

    // @Override
    // public String getTipoFormatado() {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }
}