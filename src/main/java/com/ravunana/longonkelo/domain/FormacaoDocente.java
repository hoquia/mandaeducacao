package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormacaoDocente.
 */
@Entity
@Table(name = "formacao_docente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormacaoDocente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "instituicao_ensino", nullable = false)
    private String instituicaoEnsino;

    @NotNull
    @Column(name = "area_formacao", nullable = false)
    private String areaFormacao;

    @Column(name = "curso")
    private String curso;

    @Column(name = "especialidade")
    private String especialidade;

    @NotNull
    @Column(name = "grau", nullable = false)
    private String grau;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private LocalDate inicio;

    @Column(name = "fim")
    private LocalDate fim;

    @Lob
    @Column(name = "anexo")
    private byte[] anexo;

    @Column(name = "anexo_content_type")
    private String anexoContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lookup" }, allowSetters = true)
    private LookupItem grauAcademico;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "ocorrencias",
            "horarios",
            "planoAulas",
            "notasPeriodicaDisciplinas",
            "notasGeralDisciplinas",
            "dissertacaoFinalCursos",
            "categoriaOcorrencias",
            "formacoes",
            "nacionalidade",
            "naturalidade",
            "tipoDocumento",
            "grauAcademico",
            "categoriaProfissional",
            "unidadeOrganica",
            "estadoCivil",
            "responsavelTurno",
            "responsavelAreaFormacao",
            "responsavelCurso",
            "responsavelDisciplina",
            "responsavelTurma",
        },
        allowSetters = true
    )
    private Docente docente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormacaoDocente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstituicaoEnsino() {
        return this.instituicaoEnsino;
    }

    public FormacaoDocente instituicaoEnsino(String instituicaoEnsino) {
        this.setInstituicaoEnsino(instituicaoEnsino);
        return this;
    }

    public void setInstituicaoEnsino(String instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public String getAreaFormacao() {
        return this.areaFormacao;
    }

    public FormacaoDocente areaFormacao(String areaFormacao) {
        this.setAreaFormacao(areaFormacao);
        return this;
    }

    public void setAreaFormacao(String areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public String getCurso() {
        return this.curso;
    }

    public FormacaoDocente curso(String curso) {
        this.setCurso(curso);
        return this;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEspecialidade() {
        return this.especialidade;
    }

    public FormacaoDocente especialidade(String especialidade) {
        this.setEspecialidade(especialidade);
        return this;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getGrau() {
        return this.grau;
    }

    public FormacaoDocente grau(String grau) {
        this.setGrau(grau);
        return this;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public LocalDate getInicio() {
        return this.inicio;
    }

    public FormacaoDocente inicio(LocalDate inicio) {
        this.setInicio(inicio);
        return this;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return this.fim;
    }

    public FormacaoDocente fim(LocalDate fim) {
        this.setFim(fim);
        return this;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public byte[] getAnexo() {
        return this.anexo;
    }

    public FormacaoDocente anexo(byte[] anexo) {
        this.setAnexo(anexo);
        return this;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getAnexoContentType() {
        return this.anexoContentType;
    }

    public FormacaoDocente anexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
        return this;
    }

    public void setAnexoContentType(String anexoContentType) {
        this.anexoContentType = anexoContentType;
    }

    public LookupItem getGrauAcademico() {
        return this.grauAcademico;
    }

    public void setGrauAcademico(LookupItem lookupItem) {
        this.grauAcademico = lookupItem;
    }

    public FormacaoDocente grauAcademico(LookupItem lookupItem) {
        this.setGrauAcademico(lookupItem);
        return this;
    }

    public Docente getDocente() {
        return this.docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public FormacaoDocente docente(Docente docente) {
        this.setDocente(docente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormacaoDocente)) {
            return false;
        }
        return id != null && id.equals(((FormacaoDocente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormacaoDocente{" +
            "id=" + getId() +
            ", instituicaoEnsino='" + getInstituicaoEnsino() + "'" +
            ", areaFormacao='" + getAreaFormacao() + "'" +
            ", curso='" + getCurso() + "'" +
            ", especialidade='" + getEspecialidade() + "'" +
            ", grau='" + getGrau() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", fim='" + getFim() + "'" +
            ", anexo='" + getAnexo() + "'" +
            ", anexoContentType='" + getAnexoContentType() + "'" +
            "}";
    }
}
