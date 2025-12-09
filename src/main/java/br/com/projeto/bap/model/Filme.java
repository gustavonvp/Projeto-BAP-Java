package br.com.projeto.bap.model;

public class Filme extends Midia {
    
    private Integer duracaoMinutos;
    private String sinopse;
    private String trailerUrl; // Link do YouTube

    @Override
    public String getTipoFormatado() {
        return "Filme (" + duracaoMinutos + " min)";
    }

    // Getters e Setters Específicos
    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }
}