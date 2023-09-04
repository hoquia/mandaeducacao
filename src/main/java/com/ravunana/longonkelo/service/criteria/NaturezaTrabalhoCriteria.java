package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.NaturezaTrabalho} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.NaturezaTrabalhoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /natureza-trabalhos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NaturezaTrabalhoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter isActivo;

    private LongFilter dissertacaoFinalCursoId;

    private Boolean distinct;

    public NaturezaTrabalhoCriteria() {}

    public NaturezaTrabalhoCriteria(NaturezaTrabalhoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.isActivo = other.isActivo == null ? null : other.isActivo.copy();
        this.dissertacaoFinalCursoId = other.dissertacaoFinalCursoId == null ? null : other.dissertacaoFinalCursoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NaturezaTrabalhoCriteria copy() {
        return new NaturezaTrabalhoCriteria(this);
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

    public BooleanFilter getIsActivo() {
        return isActivo;
    }

    public BooleanFilter isActivo() {
        if (isActivo == null) {
            isActivo = new BooleanFilter();
        }
        return isActivo;
    }

    public void setIsActivo(BooleanFilter isActivo) {
        this.isActivo = isActivo;
    }

    public LongFilter getDissertacaoFinalCursoId() {
        return dissertacaoFinalCursoId;
    }

    public LongFilter dissertacaoFinalCursoId() {
        if (dissertacaoFinalCursoId == null) {
            dissertacaoFinalCursoId = new LongFilter();
        }
        return dissertacaoFinalCursoId;
    }

    public void setDissertacaoFinalCursoId(LongFilter dissertacaoFinalCursoId) {
        this.dissertacaoFinalCursoId = dissertacaoFinalCursoId;
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
        final NaturezaTrabalhoCriteria that = (NaturezaTrabalhoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(isActivo, that.isActivo) &&
            Objects.equals(dissertacaoFinalCursoId, that.dissertacaoFinalCursoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, isActivo, dissertacaoFinalCursoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NaturezaTrabalhoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (isActivo != null ? "isActivo=" + isActivo + ", " : "") +
            (dissertacaoFinalCursoId != null ? "dissertacaoFinalCursoId=" + dissertacaoFinalCursoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
