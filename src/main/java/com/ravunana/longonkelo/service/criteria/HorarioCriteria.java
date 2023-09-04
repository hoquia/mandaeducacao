package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.DiaSemana;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Horario} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.HorarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /horarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HorarioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DiaSemana
     */
    public static class DiaSemanaFilter extends Filter<DiaSemana> {

        public DiaSemanaFilter() {}

        public DiaSemanaFilter(DiaSemanaFilter filter) {
            super(filter);
        }

        @Override
        public DiaSemanaFilter copy() {
            return new DiaSemanaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chaveComposta1;

    private StringFilter chaveComposta2;

    private DiaSemanaFilter diaSemana;

    private LongFilter horarioId;

    private LongFilter licaoId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter turmaId;

    private LongFilter referenciaId;

    private LongFilter periodoId;

    private LongFilter docenteId;

    private LongFilter disciplinaCurricularId;

    private Boolean distinct;

    public HorarioCriteria() {}

    public HorarioCriteria(HorarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chaveComposta1 = other.chaveComposta1 == null ? null : other.chaveComposta1.copy();
        this.chaveComposta2 = other.chaveComposta2 == null ? null : other.chaveComposta2.copy();
        this.diaSemana = other.diaSemana == null ? null : other.diaSemana.copy();
        this.horarioId = other.horarioId == null ? null : other.horarioId.copy();
        this.licaoId = other.licaoId == null ? null : other.licaoId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.periodoId = other.periodoId == null ? null : other.periodoId.copy();
        this.docenteId = other.docenteId == null ? null : other.docenteId.copy();
        this.disciplinaCurricularId = other.disciplinaCurricularId == null ? null : other.disciplinaCurricularId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public HorarioCriteria copy() {
        return new HorarioCriteria(this);
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

    public StringFilter getChaveComposta1() {
        return chaveComposta1;
    }

    public StringFilter chaveComposta1() {
        if (chaveComposta1 == null) {
            chaveComposta1 = new StringFilter();
        }
        return chaveComposta1;
    }

    public void setChaveComposta1(StringFilter chaveComposta1) {
        this.chaveComposta1 = chaveComposta1;
    }

    public StringFilter getChaveComposta2() {
        return chaveComposta2;
    }

    public StringFilter chaveComposta2() {
        if (chaveComposta2 == null) {
            chaveComposta2 = new StringFilter();
        }
        return chaveComposta2;
    }

    public void setChaveComposta2(StringFilter chaveComposta2) {
        this.chaveComposta2 = chaveComposta2;
    }

    public DiaSemanaFilter getDiaSemana() {
        return diaSemana;
    }

    public DiaSemanaFilter diaSemana() {
        if (diaSemana == null) {
            diaSemana = new DiaSemanaFilter();
        }
        return diaSemana;
    }

    public void setDiaSemana(DiaSemanaFilter diaSemana) {
        this.diaSemana = diaSemana;
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

    public LongFilter getLicaoId() {
        return licaoId;
    }

    public LongFilter licaoId() {
        if (licaoId == null) {
            licaoId = new LongFilter();
        }
        return licaoId;
    }

    public void setLicaoId(LongFilter licaoId) {
        this.licaoId = licaoId;
    }

    public LongFilter getAnoLectivoId() {
        return anoLectivoId;
    }

    public LongFilter anoLectivoId() {
        if (anoLectivoId == null) {
            anoLectivoId = new LongFilter();
        }
        return anoLectivoId;
    }

    public void setAnoLectivoId(LongFilter anoLectivoId) {
        this.anoLectivoId = anoLectivoId;
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

    public LongFilter getTurmaId() {
        return turmaId;
    }

    public LongFilter turmaId() {
        if (turmaId == null) {
            turmaId = new LongFilter();
        }
        return turmaId;
    }

    public void setTurmaId(LongFilter turmaId) {
        this.turmaId = turmaId;
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

    public LongFilter getPeriodoId() {
        return periodoId;
    }

    public LongFilter periodoId() {
        if (periodoId == null) {
            periodoId = new LongFilter();
        }
        return periodoId;
    }

    public void setPeriodoId(LongFilter periodoId) {
        this.periodoId = periodoId;
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
        final HorarioCriteria that = (HorarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(chaveComposta1, that.chaveComposta1) &&
            Objects.equals(chaveComposta2, that.chaveComposta2) &&
            Objects.equals(diaSemana, that.diaSemana) &&
            Objects.equals(horarioId, that.horarioId) &&
            Objects.equals(licaoId, that.licaoId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(periodoId, that.periodoId) &&
            Objects.equals(docenteId, that.docenteId) &&
            Objects.equals(disciplinaCurricularId, that.disciplinaCurricularId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            chaveComposta1,
            chaveComposta2,
            diaSemana,
            horarioId,
            licaoId,
            anoLectivoId,
            utilizadorId,
            turmaId,
            referenciaId,
            periodoId,
            docenteId,
            disciplinaCurricularId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (chaveComposta1 != null ? "chaveComposta1=" + chaveComposta1 + ", " : "") +
            (chaveComposta2 != null ? "chaveComposta2=" + chaveComposta2 + ", " : "") +
            (diaSemana != null ? "diaSemana=" + diaSemana + ", " : "") +
            (horarioId != null ? "horarioId=" + horarioId + ", " : "") +
            (licaoId != null ? "licaoId=" + licaoId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (periodoId != null ? "periodoId=" + periodoId + ", " : "") +
            (docenteId != null ? "docenteId=" + docenteId + ", " : "") +
            (disciplinaCurricularId != null ? "disciplinaCurricularId=" + disciplinaCurricularId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
