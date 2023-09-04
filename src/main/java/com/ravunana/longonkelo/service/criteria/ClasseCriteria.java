package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Classe} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.ClasseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /classes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClasseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private LongFilter planoCurricularId;

    private LongFilter precoEmolumentoId;

    private LongFilter nivesEnsinoId;

    private LongFilter periodosLancamentoNotaId;

    private Boolean distinct;

    public ClasseCriteria() {}

    public ClasseCriteria(ClasseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.planoCurricularId = other.planoCurricularId == null ? null : other.planoCurricularId.copy();
        this.precoEmolumentoId = other.precoEmolumentoId == null ? null : other.precoEmolumentoId.copy();
        this.nivesEnsinoId = other.nivesEnsinoId == null ? null : other.nivesEnsinoId.copy();
        this.periodosLancamentoNotaId = other.periodosLancamentoNotaId == null ? null : other.periodosLancamentoNotaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClasseCriteria copy() {
        return new ClasseCriteria(this);
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

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public LongFilter getPlanoCurricularId() {
        return planoCurricularId;
    }

    public LongFilter planoCurricularId() {
        if (planoCurricularId == null) {
            planoCurricularId = new LongFilter();
        }
        return planoCurricularId;
    }

    public void setPlanoCurricularId(LongFilter planoCurricularId) {
        this.planoCurricularId = planoCurricularId;
    }

    public LongFilter getPrecoEmolumentoId() {
        return precoEmolumentoId;
    }

    public LongFilter precoEmolumentoId() {
        if (precoEmolumentoId == null) {
            precoEmolumentoId = new LongFilter();
        }
        return precoEmolumentoId;
    }

    public void setPrecoEmolumentoId(LongFilter precoEmolumentoId) {
        this.precoEmolumentoId = precoEmolumentoId;
    }

    public LongFilter getNivesEnsinoId() {
        return nivesEnsinoId;
    }

    public LongFilter nivesEnsinoId() {
        if (nivesEnsinoId == null) {
            nivesEnsinoId = new LongFilter();
        }
        return nivesEnsinoId;
    }

    public void setNivesEnsinoId(LongFilter nivesEnsinoId) {
        this.nivesEnsinoId = nivesEnsinoId;
    }

    public LongFilter getPeriodosLancamentoNotaId() {
        return periodosLancamentoNotaId;
    }

    public LongFilter periodosLancamentoNotaId() {
        if (periodosLancamentoNotaId == null) {
            periodosLancamentoNotaId = new LongFilter();
        }
        return periodosLancamentoNotaId;
    }

    public void setPeriodosLancamentoNotaId(LongFilter periodosLancamentoNotaId) {
        this.periodosLancamentoNotaId = periodosLancamentoNotaId;
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
        final ClasseCriteria that = (ClasseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(planoCurricularId, that.planoCurricularId) &&
            Objects.equals(precoEmolumentoId, that.precoEmolumentoId) &&
            Objects.equals(nivesEnsinoId, that.nivesEnsinoId) &&
            Objects.equals(periodosLancamentoNotaId, that.periodosLancamentoNotaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, planoCurricularId, precoEmolumentoId, nivesEnsinoId, periodosLancamentoNotaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (planoCurricularId != null ? "planoCurricularId=" + planoCurricularId + ", " : "") +
            (precoEmolumentoId != null ? "precoEmolumentoId=" + precoEmolumentoId + ", " : "") +
            (nivesEnsinoId != null ? "nivesEnsinoId=" + nivesEnsinoId + ", " : "") +
            (periodosLancamentoNotaId != null ? "periodosLancamentoNotaId=" + periodosLancamentoNotaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
