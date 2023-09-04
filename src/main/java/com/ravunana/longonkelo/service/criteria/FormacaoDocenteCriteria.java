package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.FormacaoDocente} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.FormacaoDocenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /formacao-docentes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormacaoDocenteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter instituicaoEnsino;

    private StringFilter areaFormacao;

    private StringFilter curso;

    private StringFilter especialidade;

    private StringFilter grau;

    private LocalDateFilter inicio;

    private LocalDateFilter fim;

    private LongFilter grauAcademicoId;

    private LongFilter docenteId;

    private Boolean distinct;

    public FormacaoDocenteCriteria() {}

    public FormacaoDocenteCriteria(FormacaoDocenteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.instituicaoEnsino = other.instituicaoEnsino == null ? null : other.instituicaoEnsino.copy();
        this.areaFormacao = other.areaFormacao == null ? null : other.areaFormacao.copy();
        this.curso = other.curso == null ? null : other.curso.copy();
        this.especialidade = other.especialidade == null ? null : other.especialidade.copy();
        this.grau = other.grau == null ? null : other.grau.copy();
        this.inicio = other.inicio == null ? null : other.inicio.copy();
        this.fim = other.fim == null ? null : other.fim.copy();
        this.grauAcademicoId = other.grauAcademicoId == null ? null : other.grauAcademicoId.copy();
        this.docenteId = other.docenteId == null ? null : other.docenteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FormacaoDocenteCriteria copy() {
        return new FormacaoDocenteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public StringFilter instituicaoEnsino() {
        if (instituicaoEnsino == null) {
            instituicaoEnsino = new StringFilter();
        }
        return instituicaoEnsino;
    }

    public void setInstituicaoEnsino(StringFilter instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public StringFilter getAreaFormacao() {
        return areaFormacao;
    }

    public StringFilter areaFormacao() {
        if (areaFormacao == null) {
            areaFormacao = new StringFilter();
        }
        return areaFormacao;
    }

    public void setAreaFormacao(StringFilter areaFormacao) {
        this.areaFormacao = areaFormacao;
    }

    public StringFilter getCurso() {
        return curso;
    }

    public StringFilter curso() {
        if (curso == null) {
            curso = new StringFilter();
        }
        return curso;
    }

    public void setCurso(StringFilter curso) {
        this.curso = curso;
    }

    public StringFilter getEspecialidade() {
        return especialidade;
    }

    public StringFilter especialidade() {
        if (especialidade == null) {
            especialidade = new StringFilter();
        }
        return especialidade;
    }

    public void setEspecialidade(StringFilter especialidade) {
        this.especialidade = especialidade;
    }

    public StringFilter getGrau() {
        return grau;
    }

    public StringFilter grau() {
        if (grau == null) {
            grau = new StringFilter();
        }
        return grau;
    }

    public void setGrau(StringFilter grau) {
        this.grau = grau;
    }

    public LocalDateFilter getInicio() {
        return inicio;
    }

    public LocalDateFilter inicio() {
        if (inicio == null) {
            inicio = new LocalDateFilter();
        }
        return inicio;
    }

    public void setInicio(LocalDateFilter inicio) {
        this.inicio = inicio;
    }

    public LocalDateFilter getFim() {
        return fim;
    }

    public LocalDateFilter fim() {
        if (fim == null) {
            fim = new LocalDateFilter();
        }
        return fim;
    }

    public void setFim(LocalDateFilter fim) {
        this.fim = fim;
    }

    public LongFilter getGrauAcademicoId() {
        return grauAcademicoId;
    }

    public LongFilter grauAcademicoId() {
        if (grauAcademicoId == null) {
            grauAcademicoId = new LongFilter();
        }
        return grauAcademicoId;
    }

    public void setGrauAcademicoId(LongFilter grauAcademicoId) {
        this.grauAcademicoId = grauAcademicoId;
    }

    public LongFilter getDocenteId() {
        return docenteId;
    }

    public LongFilter docenteId() {
        if (docenteId == null) {
            docenteId = new LongFilter();
        }
        return docenteId;
    }

    public void setDocenteId(LongFilter docenteId) {
        this.docenteId = docenteId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FormacaoDocenteCriteria that = (FormacaoDocenteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(instituicaoEnsino, that.instituicaoEnsino) &&
            Objects.equals(areaFormacao, that.areaFormacao) &&
            Objects.equals(curso, that.curso) &&
            Objects.equals(especialidade, that.especialidade) &&
            Objects.equals(grau, that.grau) &&
            Objects.equals(inicio, that.inicio) &&
            Objects.equals(fim, that.fim) &&
            Objects.equals(grauAcademicoId, that.grauAcademicoId) &&
            Objects.equals(docenteId, that.docenteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            instituicaoEnsino,
            areaFormacao,
            curso,
            especialidade,
            grau,
            inicio,
            fim,
            grauAcademicoId,
            docenteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormacaoDocenteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (instituicaoEnsino != null ? "instituicaoEnsino=" + instituicaoEnsino + ", " : "") +
            (areaFormacao != null ? "areaFormacao=" + areaFormacao + ", " : "") +
            (curso != null ? "curso=" + curso + ", " : "") +
            (especialidade != null ? "especialidade=" + especialidade + ", " : "") +
            (grau != null ? "grau=" + grau + ", " : "") +
            (inicio != null ? "inicio=" + inicio + ", " : "") +
            (fim != null ? "fim=" + fim + ", " : "") +
            (grauAcademicoId != null ? "grauAcademicoId=" + grauAcademicoId + ", " : "") +
            (docenteId != null ? "docenteId=" + docenteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
