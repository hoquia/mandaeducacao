package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.ProcessoSelectivoMatriculaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processo-selectivo-matriculas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcessoSelectivoMatriculaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter localTeste;

    private ZonedDateTimeFilter dataTeste;

    private DoubleFilter notaTeste;

    private BooleanFilter isAdmitido;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter turmaId;

    private LongFilter discenteId;

    private Boolean distinct;

    public ProcessoSelectivoMatriculaCriteria() {}

    public ProcessoSelectivoMatriculaCriteria(ProcessoSelectivoMatriculaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.localTeste = other.localTeste == null ? null : other.localTeste.copy();
        this.dataTeste = other.dataTeste == null ? null : other.dataTeste.copy();
        this.notaTeste = other.notaTeste == null ? null : other.notaTeste.copy();
        this.isAdmitido = other.isAdmitido == null ? null : other.isAdmitido.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.discenteId = other.discenteId == null ? null : other.discenteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProcessoSelectivoMatriculaCriteria copy() {
        return new ProcessoSelectivoMatriculaCriteria(this);
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

    public StringFilter getLocalTeste() {
        return localTeste;
    }

    public StringFilter localTeste() {
        if (localTeste == null) {
            localTeste = new StringFilter();
        }
        return localTeste;
    }

    public void setLocalTeste(StringFilter localTeste) {
        this.localTeste = localTeste;
    }

    public ZonedDateTimeFilter getDataTeste() {
        return dataTeste;
    }

    public ZonedDateTimeFilter dataTeste() {
        if (dataTeste == null) {
            dataTeste = new ZonedDateTimeFilter();
        }
        return dataTeste;
    }

    public void setDataTeste(ZonedDateTimeFilter dataTeste) {
        this.dataTeste = dataTeste;
    }

    public DoubleFilter getNotaTeste() {
        return notaTeste;
    }

    public DoubleFilter notaTeste() {
        if (notaTeste == null) {
            notaTeste = new DoubleFilter();
        }
        return notaTeste;
    }

    public void setNotaTeste(DoubleFilter notaTeste) {
        this.notaTeste = notaTeste;
    }

    public BooleanFilter getIsAdmitido() {
        return isAdmitido;
    }

    public BooleanFilter isAdmitido() {
        if (isAdmitido == null) {
            isAdmitido = new BooleanFilter();
        }
        return isAdmitido;
    }

    public void setIsAdmitido(BooleanFilter isAdmitido) {
        this.isAdmitido = isAdmitido;
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

    public LongFilter getDiscenteId() {
        return discenteId;
    }

    public LongFilter discenteId() {
        if (discenteId == null) {
            discenteId = new LongFilter();
        }
        return discenteId;
    }

    public void setDiscenteId(LongFilter discenteId) {
        this.discenteId = discenteId;
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
        final ProcessoSelectivoMatriculaCriteria that = (ProcessoSelectivoMatriculaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(localTeste, that.localTeste) &&
            Objects.equals(dataTeste, that.dataTeste) &&
            Objects.equals(notaTeste, that.notaTeste) &&
            Objects.equals(isAdmitido, that.isAdmitido) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(discenteId, that.discenteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localTeste, dataTeste, notaTeste, isAdmitido, anoLectivoId, utilizadorId, turmaId, discenteId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoSelectivoMatriculaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (localTeste != null ? "localTeste=" + localTeste + ", " : "") +
            (dataTeste != null ? "dataTeste=" + dataTeste + ", " : "") +
            (notaTeste != null ? "notaTeste=" + notaTeste + ", " : "") +
            (isAdmitido != null ? "isAdmitido=" + isAdmitido + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (discenteId != null ? "discenteId=" + discenteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
