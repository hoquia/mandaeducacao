package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.DetalhePlanoAula} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.DetalhePlanoAulaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /detalhe-plano-aulas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetalhePlanoAulaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter tempoActividade;

    private StringFilter tituloActividade;

    private LongFilter planoAulaId;

    private Boolean distinct;

    public DetalhePlanoAulaCriteria() {}

    public DetalhePlanoAulaCriteria(DetalhePlanoAulaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tempoActividade = other.tempoActividade == null ? null : other.tempoActividade.copy();
        this.tituloActividade = other.tituloActividade == null ? null : other.tituloActividade.copy();
        this.planoAulaId = other.planoAulaId == null ? null : other.planoAulaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DetalhePlanoAulaCriteria copy() {
        return new DetalhePlanoAulaCriteria(this);
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

    public DoubleFilter getTempoActividade() {
        return tempoActividade;
    }

    public DoubleFilter tempoActividade() {
        if (tempoActividade == null) {
            tempoActividade = new DoubleFilter();
        }
        return tempoActividade;
    }

    public void setTempoActividade(DoubleFilter tempoActividade) {
        this.tempoActividade = tempoActividade;
    }

    public StringFilter getTituloActividade() {
        return tituloActividade;
    }

    public StringFilter tituloActividade() {
        if (tituloActividade == null) {
            tituloActividade = new StringFilter();
        }
        return tituloActividade;
    }

    public void setTituloActividade(StringFilter tituloActividade) {
        this.tituloActividade = tituloActividade;
    }

    public LongFilter getPlanoAulaId() {
        return planoAulaId;
    }

    public LongFilter planoAulaId() {
        if (planoAulaId == null) {
            planoAulaId = new LongFilter();
        }
        return planoAulaId;
    }

    public void setPlanoAulaId(LongFilter planoAulaId) {
        this.planoAulaId = planoAulaId;
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
        final DetalhePlanoAulaCriteria that = (DetalhePlanoAulaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tempoActividade, that.tempoActividade) &&
            Objects.equals(tituloActividade, that.tituloActividade) &&
            Objects.equals(planoAulaId, that.planoAulaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tempoActividade, tituloActividade, planoAulaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetalhePlanoAulaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tempoActividade != null ? "tempoActividade=" + tempoActividade + ", " : "") +
            (tituloActividade != null ? "tituloActividade=" + tituloActividade + ", " : "") +
            (planoAulaId != null ? "planoAulaId=" + planoAulaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
