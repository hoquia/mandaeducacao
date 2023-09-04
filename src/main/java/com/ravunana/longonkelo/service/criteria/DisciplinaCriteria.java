package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Disciplina} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.DisciplinaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /disciplinas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DisciplinaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter nome;

    private LongFilter responsaveisId;

    private LongFilter disciplinaCurricularId;

    private Boolean distinct;

    public DisciplinaCriteria() {}

    public DisciplinaCriteria(DisciplinaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.responsaveisId = other.responsaveisId == null ? null : other.responsaveisId.copy();
        this.disciplinaCurricularId = other.disciplinaCurricularId == null ? null : other.disciplinaCurricularId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DisciplinaCriteria copy() {
        return new DisciplinaCriteria(this);
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

    public StringFilter getCodigo() {
        return codigo;
    }

    public StringFilter codigo() {
        if (codigo == null) {
            codigo = new StringFilter();
        }
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public LongFilter getResponsaveisId() {
        return responsaveisId;
    }

    public LongFilter responsaveisId() {
        if (responsaveisId == null) {
            responsaveisId = new LongFilter();
        }
        return responsaveisId;
    }

    public void setResponsaveisId(LongFilter responsaveisId) {
        this.responsaveisId = responsaveisId;
    }

    public LongFilter getDisciplinaCurricularId() {
        return disciplinaCurricularId;
    }

    public LongFilter disciplinaCurricularId() {
        if (disciplinaCurricularId == null) {
            disciplinaCurricularId = new LongFilter();
        }
        return disciplinaCurricularId;
    }

    public void setDisciplinaCurricularId(LongFilter disciplinaCurricularId) {
        this.disciplinaCurricularId = disciplinaCurricularId;
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
        final DisciplinaCriteria that = (DisciplinaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(responsaveisId, that.responsaveisId) &&
            Objects.equals(disciplinaCurricularId, that.disciplinaCurricularId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome, responsaveisId, disciplinaCurricularId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisciplinaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (responsaveisId != null ? "responsaveisId=" + responsaveisId + ", " : "") +
            (disciplinaCurricularId != null ? "disciplinaCurricularId=" + disciplinaCurricularId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
