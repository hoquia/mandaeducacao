package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.DisciplinaCurricular} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.DisciplinaCurricularResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /disciplina-curriculars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DisciplinaCurricularCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uniqueDisciplinaCurricular;

    private StringFilter descricao;

    private DoubleFilter cargaSemanal;

    private BooleanFilter isTerminal;

    private DoubleFilter mediaParaExame;

    private DoubleFilter mediaParaRecurso;

    private DoubleFilter mediaParaExameEspecial;

    private DoubleFilter mediaParaDespensar;

    private LongFilter disciplinaCurricularId;

    private LongFilter horarioId;

    private LongFilter planoAulaId;

    private LongFilter notasGeralDisciplinaId;

    private LongFilter notasPeriodicaDisciplinaId;

    private LongFilter componenteId;

    private LongFilter regimeId;

    private LongFilter planosCurricularId;

    private LongFilter disciplinaId;

    private LongFilter referenciaId;

    private LongFilter estadosId;

    private Boolean distinct;

    public DisciplinaCurricularCriteria() {}

    public DisciplinaCurricularCriteria(DisciplinaCurricularCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uniqueDisciplinaCurricular = other.uniqueDisciplinaCurricular == null ? null : other.uniqueDisciplinaCurricular.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.cargaSemanal = other.cargaSemanal == null ? null : other.cargaSemanal.copy();
        this.isTerminal = other.isTerminal == null ? null : other.isTerminal.copy();
        this.mediaParaExame = other.mediaParaExame == null ? null : other.mediaParaExame.copy();
        this.mediaParaRecurso = other.mediaParaRecurso == null ? null : other.mediaParaRecurso.copy();
        this.mediaParaExameEspecial = other.mediaParaExameEspecial == null ? null : other.mediaParaExameEspecial.copy();
        this.mediaParaDespensar = other.mediaParaDespensar == null ? null : other.mediaParaDespensar.copy();
        this.disciplinaCurricularId = other.disciplinaCurricularId == null ? null : other.disciplinaCurricularId.copy();
        this.horarioId = other.horarioId == null ? null : other.horarioId.copy();
        this.planoAulaId = other.planoAulaId == null ? null : other.planoAulaId.copy();
        this.notasGeralDisciplinaId = other.notasGeralDisciplinaId == null ? null : other.notasGeralDisciplinaId.copy();
        this.notasPeriodicaDisciplinaId = other.notasPeriodicaDisciplinaId == null ? null : other.notasPeriodicaDisciplinaId.copy();
        this.componenteId = other.componenteId == null ? null : other.componenteId.copy();
        this.regimeId = other.regimeId == null ? null : other.regimeId.copy();
        this.planosCurricularId = other.planosCurricularId == null ? null : other.planosCurricularId.copy();
        this.disciplinaId = other.disciplinaId == null ? null : other.disciplinaId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.estadosId = other.estadosId == null ? null : other.estadosId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DisciplinaCurricularCriteria copy() {
        return new DisciplinaCurricularCriteria(this);
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

    public StringFilter getUniqueDisciplinaCurricular() {
        return uniqueDisciplinaCurricular;
    }

    public StringFilter uniqueDisciplinaCurricular() {
        if (uniqueDisciplinaCurricular == null) {
            uniqueDisciplinaCurricular = new StringFilter();
        }
        return uniqueDisciplinaCurricular;
    }

    public void setUniqueDisciplinaCurricular(StringFilter uniqueDisciplinaCurricular) {
        this.uniqueDisciplinaCurricular = uniqueDisciplinaCurricular;
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

    public DoubleFilter getCargaSemanal() {
        return cargaSemanal;
    }

    public DoubleFilter cargaSemanal() {
        if (cargaSemanal == null) {
            cargaSemanal = new DoubleFilter();
        }
        return cargaSemanal;
    }

    public void setCargaSemanal(DoubleFilter cargaSemanal) {
        this.cargaSemanal = cargaSemanal;
    }

    public BooleanFilter getIsTerminal() {
        return isTerminal;
    }

    public BooleanFilter isTerminal() {
        if (isTerminal == null) {
            isTerminal = new BooleanFilter();
        }
        return isTerminal;
    }

    public void setIsTerminal(BooleanFilter isTerminal) {
        this.isTerminal = isTerminal;
    }

    public DoubleFilter getMediaParaExame() {
        return mediaParaExame;
    }

    public DoubleFilter mediaParaExame() {
        if (mediaParaExame == null) {
            mediaParaExame = new DoubleFilter();
        }
        return mediaParaExame;
    }

    public void setMediaParaExame(DoubleFilter mediaParaExame) {
        this.mediaParaExame = mediaParaExame;
    }

    public DoubleFilter getMediaParaRecurso() {
        return mediaParaRecurso;
    }

    public DoubleFilter mediaParaRecurso() {
        if (mediaParaRecurso == null) {
            mediaParaRecurso = new DoubleFilter();
        }
        return mediaParaRecurso;
    }

    public void setMediaParaRecurso(DoubleFilter mediaParaRecurso) {
        this.mediaParaRecurso = mediaParaRecurso;
    }

    public DoubleFilter getMediaParaExameEspecial() {
        return mediaParaExameEspecial;
    }

    public DoubleFilter mediaParaExameEspecial() {
        if (mediaParaExameEspecial == null) {
            mediaParaExameEspecial = new DoubleFilter();
        }
        return mediaParaExameEspecial;
    }

    public void setMediaParaExameEspecial(DoubleFilter mediaParaExameEspecial) {
        this.mediaParaExameEspecial = mediaParaExameEspecial;
    }

    public DoubleFilter getMediaParaDespensar() {
        return mediaParaDespensar;
    }

    public DoubleFilter mediaParaDespensar() {
        if (mediaParaDespensar == null) {
            mediaParaDespensar = new DoubleFilter();
        }
        return mediaParaDespensar;
    }

    public void setMediaParaDespensar(DoubleFilter mediaParaDespensar) {
        this.mediaParaDespensar = mediaParaDespensar;
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

    public LongFilter getHorarioId() {
        return horarioId;
    }

    public LongFilter horarioId() {
        if (horarioId == null) {
            horarioId = new LongFilter();
        }
        return horarioId;
    }

    public void setHorarioId(LongFilter horarioId) {
        this.horarioId = horarioId;
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

    public LongFilter getNotasGeralDisciplinaId() {
        return notasGeralDisciplinaId;
    }

    public LongFilter notasGeralDisciplinaId() {
        if (notasGeralDisciplinaId == null) {
            notasGeralDisciplinaId = new LongFilter();
        }
        return notasGeralDisciplinaId;
    }

    public void setNotasGeralDisciplinaId(LongFilter notasGeralDisciplinaId) {
        this.notasGeralDisciplinaId = notasGeralDisciplinaId;
    }

    public LongFilter getNotasPeriodicaDisciplinaId() {
        return notasPeriodicaDisciplinaId;
    }

    public LongFilter notasPeriodicaDisciplinaId() {
        if (notasPeriodicaDisciplinaId == null) {
            notasPeriodicaDisciplinaId = new LongFilter();
        }
        return notasPeriodicaDisciplinaId;
    }

    public void setNotasPeriodicaDisciplinaId(LongFilter notasPeriodicaDisciplinaId) {
        this.notasPeriodicaDisciplinaId = notasPeriodicaDisciplinaId;
    }

    public LongFilter getComponenteId() {
        return componenteId;
    }

    public LongFilter componenteId() {
        if (componenteId == null) {
            componenteId = new LongFilter();
        }
        return componenteId;
    }

    public void setComponenteId(LongFilter componenteId) {
        this.componenteId = componenteId;
    }

    public LongFilter getRegimeId() {
        return regimeId;
    }

    public LongFilter regimeId() {
        if (regimeId == null) {
            regimeId = new LongFilter();
        }
        return regimeId;
    }

    public void setRegimeId(LongFilter regimeId) {
        this.regimeId = regimeId;
    }

    public LongFilter getPlanosCurricularId() {
        return planosCurricularId;
    }

    public LongFilter planosCurricularId() {
        if (planosCurricularId == null) {
            planosCurricularId = new LongFilter();
        }
        return planosCurricularId;
    }

    public void setPlanosCurricularId(LongFilter planosCurricularId) {
        this.planosCurricularId = planosCurricularId;
    }

    public LongFilter getDisciplinaId() {
        return disciplinaId;
    }

    public LongFilter disciplinaId() {
        if (disciplinaId == null) {
            disciplinaId = new LongFilter();
        }
        return disciplinaId;
    }

    public void setDisciplinaId(LongFilter disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public LongFilter getReferenciaId() {
        return referenciaId;
    }

    public LongFilter referenciaId() {
        if (referenciaId == null) {
            referenciaId = new LongFilter();
        }
        return referenciaId;
    }

    public void setReferenciaId(LongFilter referenciaId) {
        this.referenciaId = referenciaId;
    }

    public LongFilter getEstadosId() {
        return estadosId;
    }

    public LongFilter estadosId() {
        if (estadosId == null) {
            estadosId = new LongFilter();
        }
        return estadosId;
    }

    public void setEstadosId(LongFilter estadosId) {
        this.estadosId = estadosId;
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
        final DisciplinaCurricularCriteria that = (DisciplinaCurricularCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uniqueDisciplinaCurricular, that.uniqueDisciplinaCurricular) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(cargaSemanal, that.cargaSemanal) &&
            Objects.equals(isTerminal, that.isTerminal) &&
            Objects.equals(mediaParaExame, that.mediaParaExame) &&
            Objects.equals(mediaParaRecurso, that.mediaParaRecurso) &&
            Objects.equals(mediaParaExameEspecial, that.mediaParaExameEspecial) &&
            Objects.equals(mediaParaDespensar, that.mediaParaDespensar) &&
            Objects.equals(disciplinaCurricularId, that.disciplinaCurricularId) &&
            Objects.equals(horarioId, that.horarioId) &&
            Objects.equals(planoAulaId, that.planoAulaId) &&
            Objects.equals(notasGeralDisciplinaId, that.notasGeralDisciplinaId) &&
            Objects.equals(notasPeriodicaDisciplinaId, that.notasPeriodicaDisciplinaId) &&
            Objects.equals(componenteId, that.componenteId) &&
            Objects.equals(regimeId, that.regimeId) &&
            Objects.equals(planosCurricularId, that.planosCurricularId) &&
            Objects.equals(disciplinaId, that.disciplinaId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(estadosId, that.estadosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            uniqueDisciplinaCurricular,
            descricao,
            cargaSemanal,
            isTerminal,
            mediaParaExame,
            mediaParaRecurso,
            mediaParaExameEspecial,
            mediaParaDespensar,
            disciplinaCurricularId,
            horarioId,
            planoAulaId,
            notasGeralDisciplinaId,
            notasPeriodicaDisciplinaId,
            componenteId,
            regimeId,
            planosCurricularId,
            disciplinaId,
            referenciaId,
            estadosId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisciplinaCurricularCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uniqueDisciplinaCurricular != null ? "uniqueDisciplinaCurricular=" + uniqueDisciplinaCurricular + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (cargaSemanal != null ? "cargaSemanal=" + cargaSemanal + ", " : "") +
            (isTerminal != null ? "isTerminal=" + isTerminal + ", " : "") +
            (mediaParaExame != null ? "mediaParaExame=" + mediaParaExame + ", " : "") +
            (mediaParaRecurso != null ? "mediaParaRecurso=" + mediaParaRecurso + ", " : "") +
            (mediaParaExameEspecial != null ? "mediaParaExameEspecial=" + mediaParaExameEspecial + ", " : "") +
            (mediaParaDespensar != null ? "mediaParaDespensar=" + mediaParaDespensar + ", " : "") +
            (disciplinaCurricularId != null ? "disciplinaCurricularId=" + disciplinaCurricularId + ", " : "") +
            (horarioId != null ? "horarioId=" + horarioId + ", " : "") +
            (planoAulaId != null ? "planoAulaId=" + planoAulaId + ", " : "") +
            (notasGeralDisciplinaId != null ? "notasGeralDisciplinaId=" + notasGeralDisciplinaId + ", " : "") +
            (notasPeriodicaDisciplinaId != null ? "notasPeriodicaDisciplinaId=" + notasPeriodicaDisciplinaId + ", " : "") +
            (componenteId != null ? "componenteId=" + componenteId + ", " : "") +
            (regimeId != null ? "regimeId=" + regimeId + ", " : "") +
            (planosCurricularId != null ? "planosCurricularId=" + planosCurricularId + ", " : "") +
            (disciplinaId != null ? "disciplinaId=" + disciplinaId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (estadosId != null ? "estadosId=" + estadosId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
