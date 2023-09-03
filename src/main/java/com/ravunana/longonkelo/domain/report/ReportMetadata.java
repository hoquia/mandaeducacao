package com.ravunana.longonkelo.domain.report;

public class ReportMetadata {

    private String titulo;
    private String subTitulo;
    private String palavrasChave;
    private String criador;
    private String autor;

    public ReportMetadata(String titulo, String subTitulo, String palavrasChave, String criador, String autor) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.palavrasChave = palavrasChave;
        this.criador = criador;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public String getCriador() {
        return criador;
    }

    public String getAutor() {
        return autor;
    }
}
