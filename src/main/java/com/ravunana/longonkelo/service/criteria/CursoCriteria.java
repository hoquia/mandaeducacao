package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Curso} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.CursoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cursos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CursoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter nome;

    private LongFilter planoCurricularId;

    private LongFilter responsaveisId;

    private LongFilter precoEmolumentoId;

    private LongFilter areaFormacaoId;

    private LongFilter camposActuacaoId;

    private Boolean distinct;

    public CursoCriteria() {}

    public CursoCriteria(CursoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.planoCurricularId = other.planoCurricularId == null ? null : other.planoCurricularId.copy();
        this.responsaveisId = other.responsaveisId == null ? null : other.responsaveisId.copy();
        this.precoEmolumentoId = other.precoEmolumentoId == null ? null : other.precoEmolumentoId.copy();
        this.areaFormacaoId = other.areaFormacaoId == null ? null : other.areaFormacaoId.copy();
        this.camposActuacaoId = other.camposActuacaoId == null ? null : other.camposActuacaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CursoCriteria copy() {
        return new CursoCriteria(this);
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

    public LongFilter getAreaFormacaoId() {
        return areaFormacaoId;
    }

    public LongFilter areaFormacaoId() {
        if (areaFormacaoId == null) {
            areaFormacaoId = new LongFilter();
        }
        return areaFormacaoId;
    }

    public void setAreaFormacaoId(LongFilter areaFormacaoId) {
        this.areaFormacaoId = areaFormacaoId;
    }

    public LongFilter getCamposActuacaoId() {
        return camposActuacaoId;
    }

    public LongFilter camposActuacaoId() {
        if (camposActuacaoId == null) {
            camposActuacaoId = new LongFilter();
        }
        return camposActuacaoId;
    }

    public void setCamposActuacaoId(LongFilter camposActuacaoId) {
        this.camposActuacaoId = camposActuacaoId;
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
        final CursoCriteria that = (CursoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(planoCurricularId, that.planoCurricularId) &&
            Objects.equals(responsaveisId, that.responsaveisId) &&
            Objects.equals(precoEmolumentoId, that.precoEmolumentoId) &&
            Objects.equals(areaFormacaoId, that.areaFormacaoId) &&
            Objects.equals(camposActuacaoId, that.camposActuacaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codigo,
            nome,
            planoCurricularId,
            responsaveisId,
            precoEmolumentoId,
            areaFormacaoId,
            camposActuacaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CursoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (planoCurricularId != null ? "planoCurricularId=" + planoCurricularId + ", " : "") +
            (responsaveisId != null ? "responsaveisId=" + responsaveisId + ", " : "") +
            (precoEmolumentoId != null ? "precoEmolumentoId=" + precoEmolumentoId + ", " : "") +
            (areaFormacaoId != null ? "areaFormacaoId=" + areaFormacaoId + ", " : "") +
            (camposActuacaoId != null ? "camposActuacaoId=" + camposActuacaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
