package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.PeriodoHorario} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.PeriodoHorarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /periodo-horarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoHorarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private IntegerFilter tempo;

    private StringFilter inicio;

    private StringFilter fim;

    private LongFilter horarioId;

    private LongFilter turnoId;

    private Boolean distinct;

    public PeriodoHorarioCriteria() {}

    public PeriodoHorarioCriteria(PeriodoHorarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.tempo = other.tempo == null ? null : other.tempo.copy();
        this.inicio = other.inicio == null ? null : other.inicio.copy();
        this.fim = other.fim == null ? null : other.fim.copy();
        this.horarioId = other.horarioId == null ? null : other.horarioId.copy();
        this.turnoId = other.turnoId == null ? null : other.turnoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PeriodoHorarioCriteria copy() {
        return new PeriodoHorarioCriteria(this);
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

    public IntegerFilter getTempo() {
        return tempo;
    }

    public IntegerFilter tempo() {
        if (tempo == null) {
            tempo = new IntegerFilter();
        }
        return tempo;
    }

    public void setTempo(IntegerFilter tempo) {
        this.tempo = tempo;
    }

    public StringFilter getInicio() {
        return inicio;
    }

    public StringFilter inicio() {
        if (inicio == null) {
            inicio = new StringFilter();
        }
        return inicio;
    }

    public void setInicio(StringFilter inicio) {
        this.inicio = inicio;
    }

    public StringFilter getFim() {
        return fim;
    }

    public StringFilter fim() {
        if (fim == null) {
            fim = new StringFilter();
        }
        return fim;
    }

    public void setFim(StringFilter fim) {
        this.fim = fim;
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
        final PeriodoHorarioCriteria that = (PeriodoHorarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(tempo, that.tempo) &&
            Objects.equals(inicio, that.inicio) &&
            Objects.equals(fim, that.fim) &&
            Objects.equals(horarioId, that.horarioId) &&
            Objects.equals(turnoId, that.turnoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, tempo, inicio, fim, horarioId, turnoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoHorarioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (tempo != null ? "tempo=" + tempo + ", " : "") +
            (inicio != null ? "inicio=" + inicio + ", " : "") +
            (fim != null ? "fim=" + fim + ", " : "") +
            (horarioId != null ? "horarioId=" + horarioId + ", " : "") +
            (turnoId != null ? "turnoId=" + turnoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
