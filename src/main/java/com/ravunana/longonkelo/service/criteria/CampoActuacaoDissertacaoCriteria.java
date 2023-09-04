package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.CampoActuacaoDissertacao} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.CampoActuacaoDissertacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /campo-actuacao-dissertacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CampoActuacaoDissertacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter isActivo;

    private LongFilter cursosId;

    private Boolean distinct;

    public CampoActuacaoDissertacaoCriteria() {}

    public CampoActuacaoDissertacaoCriteria(CampoActuacaoDissertacaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.isActivo = other.isActivo == null ? null : other.isActivo.copy();
        this.cursosId = other.cursosId == null ? null : other.cursosId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CampoActuacaoDissertacaoCriteria copy() {
        return new CampoActuacaoDissertacaoCriteria(this);
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

    public LongFilter getCursosId() {
        return cursosId;
    }

    public LongFilter cursosId() {
        if (cursosId == null) {
            cursosId = new LongFilter();
        }
        return cursosId;
    }

    public void setCursosId(LongFilter cursosId) {
        this.cursosId = cursosId;
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
        final CampoActuacaoDissertacaoCriteria that = (CampoActuacaoDissertacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(isActivo, that.isActivo) &&
            Objects.equals(cursosId, that.cursosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, isActivo, cursosId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampoActuacaoDissertacaoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (isActivo != null ? "isActivo=" + isActivo + ", " : "") +
            (cursosId != null ? "cursosId=" + cursosId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
