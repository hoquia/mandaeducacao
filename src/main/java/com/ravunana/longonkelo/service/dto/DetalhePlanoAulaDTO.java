package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.DetalhePlanoAula} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhePlanoAulaDTO implements Serializable {

    private Long id;

    @Lob
    private String estrategiaAula;

    @NotNull
    @DecimalMin(value = "0")
    private Double tempoActividade;

    @Lob
    private String recursosEnsino;

    @NotNull
    private String tituloActividade;

    @Lob
    private String actividadesDocente;

    @Lob
    private String actividadesDiscentes;

    @Lob
    private String avaliacao;

    @Lob
    private String bibliografia;

    @Lob
    private String observacao;

    @Lob
    private byte[] pdf;

    private String pdfContentType;

    @Lob
    private byte[] video;

    private String videoContentType;

    @Lob
    private byte[] audio;

    private String audioContentType;
    private PlanoAulaDTO planoAula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstrategiaAula() {
        return estrategiaAula;
    }

    public void setEstrategiaAula(String estrategiaAula) {
        this.estrategiaAula = estrategiaAula;
    }

    public Double getTempoActividade() {
        return tempoActividade;
    }

    public void setTempoActividade(Double tempoActividade) {
        this.tempoActividade = tempoActividade;
    }

    public String getRecursosEnsino() {
        return recursosEnsino;
    }

    public void setRecursosEnsino(String recursosEnsino) {
        this.recursosEnsino = recursosEnsino;
    }

    public String getTituloActividade() {
        return tituloActividade;
    }

    public void setTituloActividade(String tituloActividade) {
        this.tituloActividade = tituloActividade;
    }

    public String getActividadesDocente() {
        return actividadesDocente;
    }

    public void setActividadesDocente(String actividadesDocente) {
        this.actividadesDocente = actividadesDocente;
    }

    public String getActividadesDiscentes() {
        return actividadesDiscentes;
    }

    public void setActividadesDiscentes(String actividadesDiscentes) {
        this.actividadesDiscentes = actividadesDiscentes;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public String getPdfContentType() {
        return pdfContentType;
    }

    public void setPdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return videoContentType;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getAudioContentType() {
        return audioContentType;
    }

    public void setAudioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
    }

    public PlanoAulaDTO getPlanoAula() {
        return planoAula;
    }

    public void setPlanoAula(PlanoAulaDTO planoAula) {
        this.planoAula = planoAula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalhePlanoAulaDTO)) {
            return false;
        }

        DetalhePlanoAulaDTO detalhePlanoAulaDTO = (DetalhePlanoAulaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detalhePlanoAulaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhePlanoAulaDTO{" +
            "id=" + getId() +
            ", estrategiaAula='" + getEstrategiaAula() + "'" +
            ", tempoActividade=" + getTempoActividade() +
            ", recursosEnsino='" + getRecursosEnsino() + "'" +
            ", tituloActividade='" + getTituloActividade() + "'" +
            ", actividadesDocente='" + getActividadesDocente() + "'" +
            ", actividadesDiscentes='" + getActividadesDiscentes() + "'" +
            ", avaliacao='" + getAvaliacao() + "'" +
            ", bibliografia='" + getBibliografia() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", pdf='" + getPdf() + "'" +
            ", video='" + getVideo() + "'" +
            ", audio='" + getAudio() + "'" +
            ", planoAula=" + getPlanoAula() +
            "}";
    }
}
