package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A DetalhePlanoAula.
 */
@Entity
@Table(name = "detalhe_plano_aula")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhePlanoAula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "estrategia_aula")
    private String estrategiaAula;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "tempo_actividade", nullable = false)
    private Double tempoActividade;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "recursos_ensino")
    private String recursosEnsino;

    @NotNull
    @Column(name = "titulo_actividade", nullable = false)
    private String tituloActividade;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "actividades_docente", nullable = false)
    private String actividadesDocente;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "actividades_discentes", nullable = false)
    private String actividadesDiscentes;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "avaliacao", nullable = false)
    private String avaliacao;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "bibliografia", nullable = false)
    private String bibliografia;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacao")
    private String observacao;

    @Lob
    @Column(name = "pdf")
    private byte[] pdf;

    @Column(name = "pdf_content_type")
    private String pdfContentType;

    @Lob
    @Column(name = "video")
    private byte[] video;

    @Column(name = "video_content_type")
    private String videoContentType;

    @Lob
    @Column(name = "audio")
    private byte[] audio;

    @Column(name = "audio_content_type")
    private String audioContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "detalhes",
            "licaos",
            "anoLectivos",
            "utilizador",
            "unidadeTematica",
            "subUnidadeTematica",
            "turma",
            "docente",
            "disciplinaCurricular",
        },
        allowSetters = true
    )
    private PlanoAula planoAula;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetalhePlanoAula id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstrategiaAula() {
        return this.estrategiaAula;
    }

    public DetalhePlanoAula estrategiaAula(String estrategiaAula) {
        this.setEstrategiaAula(estrategiaAula);
        return this;
    }

    public void setEstrategiaAula(String estrategiaAula) {
        this.estrategiaAula = estrategiaAula;
    }

    public Double getTempoActividade() {
        return this.tempoActividade;
    }

    public DetalhePlanoAula tempoActividade(Double tempoActividade) {
        this.setTempoActividade(tempoActividade);
        return this;
    }

    public void setTempoActividade(Double tempoActividade) {
        this.tempoActividade = tempoActividade;
    }

    public String getRecursosEnsino() {
        return this.recursosEnsino;
    }

    public DetalhePlanoAula recursosEnsino(String recursosEnsino) {
        this.setRecursosEnsino(recursosEnsino);
        return this;
    }

    public void setRecursosEnsino(String recursosEnsino) {
        this.recursosEnsino = recursosEnsino;
    }

    public String getTituloActividade() {
        return this.tituloActividade;
    }

    public DetalhePlanoAula tituloActividade(String tituloActividade) {
        this.setTituloActividade(tituloActividade);
        return this;
    }

    public void setTituloActividade(String tituloActividade) {
        this.tituloActividade = tituloActividade;
    }

    public String getActividadesDocente() {
        return this.actividadesDocente;
    }

    public DetalhePlanoAula actividadesDocente(String actividadesDocente) {
        this.setActividadesDocente(actividadesDocente);
        return this;
    }

    public void setActividadesDocente(String actividadesDocente) {
        this.actividadesDocente = actividadesDocente;
    }

    public String getActividadesDiscentes() {
        return this.actividadesDiscentes;
    }

    public DetalhePlanoAula actividadesDiscentes(String actividadesDiscentes) {
        this.setActividadesDiscentes(actividadesDiscentes);
        return this;
    }

    public void setActividadesDiscentes(String actividadesDiscentes) {
        this.actividadesDiscentes = actividadesDiscentes;
    }

    public String getAvaliacao() {
        return this.avaliacao;
    }

    public DetalhePlanoAula avaliacao(String avaliacao) {
        this.setAvaliacao(avaliacao);
        return this;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getBibliografia() {
        return this.bibliografia;
    }

    public DetalhePlanoAula bibliografia(String bibliografia) {
        this.setBibliografia(bibliografia);
        return this;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public DetalhePlanoAula observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public byte[] getPdf() {
        return this.pdf;
    }

    public DetalhePlanoAula pdf(byte[] pdf) {
        this.setPdf(pdf);
        return this;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public String getPdfContentType() {
        return this.pdfContentType;
    }

    public DetalhePlanoAula pdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
        return this;
    }

    public void setPdfContentType(String pdfContentType) {
        this.pdfContentType = pdfContentType;
    }

    public byte[] getVideo() {
        return this.video;
    }

    public DetalhePlanoAula video(byte[] video) {
        this.setVideo(video);
        return this;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return this.videoContentType;
    }

    public DetalhePlanoAula videoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
        return this;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public byte[] getAudio() {
        return this.audio;
    }

    public DetalhePlanoAula audio(byte[] audio) {
        this.setAudio(audio);
        return this;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getAudioContentType() {
        return this.audioContentType;
    }

    public DetalhePlanoAula audioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
        return this;
    }

    public void setAudioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
    }

    public PlanoAula getPlanoAula() {
        return this.planoAula;
    }

    public void setPlanoAula(PlanoAula planoAula) {
        this.planoAula = planoAula;
    }

    public DetalhePlanoAula planoAula(PlanoAula planoAula) {
        this.setPlanoAula(planoAula);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalhePlanoAula)) {
            return false;
        }
        return id != null && id.equals(((DetalhePlanoAula) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhePlanoAula{" +
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
            ", pdfContentType='" + getPdfContentType() + "'" +
            ", video='" + getVideo() + "'" +
            ", videoContentType='" + getVideoContentType() + "'" +
            ", audio='" + getAudio() + "'" +
            ", audioContentType='" + getAudioContentType() + "'" +
            "}";
    }
}
