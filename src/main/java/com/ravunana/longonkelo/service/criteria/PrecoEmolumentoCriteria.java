package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.PrecoEmolumento} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.PrecoEmolumentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /preco-emolumentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrecoEmolumentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter preco;

    private BooleanFilter isEspecificoCurso;

    private BooleanFilter isEspecificoAreaFormacao;

    private BooleanFilter isEspecificoClasse;

    private BooleanFilter isEspecificoTurno;

    private LongFilter utilizadorId;

    private LongFilter emolumentoId;

    private LongFilter areaFormacaoId;

    private LongFilter cursoId;

    private LongFilter classeId;

    private LongFilter turnoId;

    private LongFilter planoMultaId;

    private Boolean distinct;

    public PrecoEmolumentoCriteria() {}

    public PrecoEmolumentoCriteria(PrecoEmolumentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.preco = other.preco == null ? null : other.preco.copy();
        this.isEspecificoCurso = other.isEspecificoCurso == null ? null : other.isEspecificoCurso.copy();
        this.isEspecificoAreaFormacao = other.isEspecificoAreaFormacao == null ? null : other.isEspecificoAreaFormacao.copy();
        this.isEspecificoClasse = other.isEspecificoClasse == null ? null : other.isEspecificoClasse.copy();
        this.isEspecificoTurno = other.isEspecificoTurno == null ? null : other.isEspecificoTurno.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.emolumentoId = other.emolumentoId == null ? null : other.emolumentoId.copy();
        this.areaFormacaoId = other.areaFormacaoId == null ? null : other.areaFormacaoId.copy();
        this.cursoId = other.cursoId == null ? null : other.cursoId.copy();
        this.classeId = other.classeId == null ? null : other.classeId.copy();
        this.turnoId = other.turnoId == null ? null : other.turnoId.copy();
        this.planoMultaId = other.planoMultaId == null ? null : other.planoMultaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PrecoEmolumentoCriteria copy() {
        return new PrecoEmolumentoCriteria(this);
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

    public BigDecimalFilter getPreco() {
        return preco;
    }

    public BigDecimalFilter preco() {
        if (preco == null) {
            preco = new BigDecimalFilter();
        }
        return preco;
    }

    public void setPreco(BigDecimalFilter preco) {
        this.preco = preco;
    }

    public BooleanFilter getIsEspecificoCurso() {
        return isEspecificoCurso;
    }

    public BooleanFilter isEspecificoCurso() {
        if (isEspecificoCurso == null) {
            isEspecificoCurso = new BooleanFilter();
        }
        return isEspecificoCurso;
    }

    public void setIsEspecificoCurso(BooleanFilter isEspecificoCurso) {
        this.isEspecificoCurso = isEspecificoCurso;
    }

    public BooleanFilter getIsEspecificoAreaFormacao() {
        return isEspecificoAreaFormacao;
    }

    public BooleanFilter isEspecificoAreaFormacao() {
        if (isEspecificoAreaFormacao == null) {
            isEspecificoAreaFormacao = new BooleanFilter();
        }
        return isEspecificoAreaFormacao;
    }

    public void setIsEspecificoAreaFormacao(BooleanFilter isEspecificoAreaFormacao) {
        this.isEspecificoAreaFormacao = isEspecificoAreaFormacao;
    }

    public BooleanFilter getIsEspecificoClasse() {
        return isEspecificoClasse;
    }

    public BooleanFilter isEspecificoClasse() {
        if (isEspecificoClasse == null) {
            isEspecificoClasse = new BooleanFilter();
        }
        return isEspecificoClasse;
    }

    public void setIsEspecificoClasse(BooleanFilter isEspecificoClasse) {
        this.isEspecificoClasse = isEspecificoClasse;
    }

    public BooleanFilter getIsEspecificoTurno() {
        return isEspecificoTurno;
    }

    public BooleanFilter isEspecificoTurno() {
        if (isEspecificoTurno == null) {
            isEspecificoTurno = new BooleanFilter();
        }
        return isEspecificoTurno;
    }

    public void setIsEspecificoTurno(BooleanFilter isEspecificoTurno) {
        this.isEspecificoTurno = isEspecificoTurno;
    }

    public LongFilter getUtilizadorId() {
        return utilizadorId;
    }

    public LongFilter utilizadorId() {
        if (utilizadorId == null) {
            utilizadorId = new LongFilter();
        }
        return utilizadorId;
    }

    public void setUtilizadorId(LongFilter utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public LongFilter getEmolumentoId() {
        return emolumentoId;
    }

    public LongFilter emolumentoId() {
        if (emolumentoId == null) {
            emolumentoId = new LongFilter();
        }
        return emolumentoId;
    }

    public void setEmolumentoId(LongFilter emolumentoId) {
        this.emolumentoId = emolumentoId;
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

    public LongFilter getCursoId() {
        return cursoId;
    }

    public LongFilter cursoId() {
        if (cursoId == null) {
            cursoId = new LongFilter();
        }
        return cursoId;
    }

    public void setCursoId(LongFilter cursoId) {
        this.cursoId = cursoId;
    }

    public LongFilter getClasseId() {
        return classeId;
    }

    public LongFilter classeId() {
        if (classeId == null) {
            classeId = new LongFilter();
        }
        return classeId;
    }

    public void setClasseId(LongFilter classeId) {
        this.classeId = classeId;
    }

    public LongFilter getTurnoId() {
        return turnoId;
    }

    public LongFilter turnoId() {
        if (turnoId == null) {
            turnoId = new LongFilter();
        }
        return turnoId;
    }

    public void setTurnoId(LongFilter turnoId) {
        this.turnoId = turnoId;
    }

    public LongFilter getPlanoMultaId() {
        return planoMultaId;
    }

    public LongFilter planoMultaId() {
        if (planoMultaId == null) {
            planoMultaId = new LongFilter();
        }
        return planoMultaId;
    }

    public void setPlanoMultaId(LongFilter planoMultaId) {
        this.planoMultaId = planoMultaId;
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
        final PrecoEmolumentoCriteria that = (PrecoEmolumentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(preco, that.preco) &&
            Objects.equals(isEspecificoCurso, that.isEspecificoCurso) &&
            Objects.equals(isEspecificoAreaFormacao, that.isEspecificoAreaFormacao) &&
            Objects.equals(isEspecificoClasse, that.isEspecificoClasse) &&
            Objects.equals(isEspecificoTurno, that.isEspecificoTurno) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(emolumentoId, that.emolumentoId) &&
            Objects.equals(areaFormacaoId, that.areaFormacaoId) &&
            Objects.equals(cursoId, that.cursoId) &&
            Objects.equals(classeId, that.classeId) &&
            Objects.equals(turnoId, that.turnoId) &&
            Objects.equals(planoMultaId, that.planoMultaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            preco,
            isEspecificoCurso,
            isEspecificoAreaFormacao,
            isEspecificoClasse,
            isEspecificoTurno,
            utilizadorId,
            emolumentoId,
            areaFormacaoId,
            cursoId,
            classeId,
            turnoId,
            planoMultaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrecoEmolumentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (preco != null ? "preco=" + preco + ", " : "") +
            (isEspecificoCurso != null ? "isEspecificoCurso=" + isEspecificoCurso + ", " : "") +
            (isEspecificoAreaFormacao != null ? "isEspecificoAreaFormacao=" + isEspecificoAreaFormacao + ", " : "") +
            (isEspecificoClasse != null ? "isEspecificoClasse=" + isEspecificoClasse + ", " : "") +
            (isEspecificoTurno != null ? "isEspecificoTurno=" + isEspecificoTurno + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (emolumentoId != null ? "emolumentoId=" + emolumentoId + ", " : "") +
            (areaFormacaoId != null ? "areaFormacaoId=" + areaFormacaoId + ", " : "") +
            (cursoId != null ? "cursoId=" + cursoId + ", " : "") +
            (classeId != null ? "classeId=" + classeId + ", " : "") +
            (turnoId != null ? "turnoId=" + turnoId + ", " : "") +
            (planoMultaId != null ? "planoMultaId=" + planoMultaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
