package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.EstadoDissertacao} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.EstadoDissertacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estado-dissertacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoDissertacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter nome;

    private IntegerFilter etapa;

    private LongFilter dissertacaoFinalCursoId;

    private Boolean distinct;

    public EstadoDissertacaoCriteria() {}

    public EstadoDissertacaoCriteria(EstadoDissertacaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.etapa = other.etapa == null ? null : other.etapa.copy();
        this.dissertacaoFinalCursoId = other.dissertacaoFinalCursoId == null ? null : other.dissertacaoFinalCursoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EstadoDissertacaoCriteria copy() {
        return new EstadoDissertacaoCriteria(this);
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

    public IntegerFilter getEtapa() {
        return etapa;
    }

    public IntegerFilter etapa() {
        if (etapa == null) {
            etapa = new IntegerFilter();
        }
        return etapa;
    }

    public void setEtapa(IntegerFilter etapa) {
        this.etapa = etapa;
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
        final EstadoDissertacaoCriteria that = (EstadoDissertacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(etapa, that.etapa) &&
            Objects.equals(dissertacaoFinalCursoId, that.dissertacaoFinalCursoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome, etapa, dissertacaoFinalCursoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDissertacaoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (etapa != null ? "etapa=" + etapa + ", " : "") +
            (dissertacaoFinalCursoId != null ? "dissertacaoFinalCursoId=" + dissertacaoFinalCursoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
