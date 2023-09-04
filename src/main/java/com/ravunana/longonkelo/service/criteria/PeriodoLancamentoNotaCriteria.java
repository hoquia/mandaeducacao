package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.TipoAvaliacao;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.PeriodoLancamentoNota} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.PeriodoLancamentoNotaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /periodo-lancamento-notas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoLancamentoNotaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoAvaliacao
     */
    public static class TipoAvaliacaoFilter extends Filter<TipoAvaliacao> {

        public TipoAvaliacaoFilter() {}

        public TipoAvaliacaoFilter(TipoAvaliacaoFilter filter) {
            super(filter);
        }

        @Override
        public TipoAvaliacaoFilter copy() {
            return new TipoAvaliacaoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoAvaliacaoFilter tipoAvaliacao;

    private ZonedDateTimeFilter de;

    private ZonedDateTimeFilter ate;

    private ZonedDateTimeFilter timestamp;

    private LongFilter utilizadorId;

    private LongFilter classeId;

    private Boolean distinct;

    public PeriodoLancamentoNotaCriteria() {}

    public PeriodoLancamentoNotaCriteria(PeriodoLancamentoNotaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipoAvaliacao = other.tipoAvaliacao == null ? null : other.tipoAvaliacao.copy();
        this.de = other.de == null ? null : other.de.copy();
        this.ate = other.ate == null ? null : other.ate.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.classeId = other.classeId == null ? null : other.classeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PeriodoLancamentoNotaCriteria copy() {
        return new PeriodoLancamentoNotaCriteria(this);
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

    public TipoAvaliacaoFilter getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public TipoAvaliacaoFilter tipoAvaliacao() {
        if (tipoAvaliacao == null) {
            tipoAvaliacao = new TipoAvaliacaoFilter();
        }
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(TipoAvaliacaoFilter tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public ZonedDateTimeFilter getDe() {
        return de;
    }

    public ZonedDateTimeFilter de() {
        if (de == null) {
            de = new ZonedDateTimeFilter();
        }
        return de;
    }

    public void setDe(ZonedDateTimeFilter de) {
        this.de = de;
    }

    public ZonedDateTimeFilter getAte() {
        return ate;
    }

    public ZonedDateTimeFilter ate() {
        if (ate == null) {
            ate = new ZonedDateTimeFilter();
        }
        return ate;
    }

    public void setAte(ZonedDateTimeFilter ate) {
        this.ate = ate;
    }

    public ZonedDateTimeFilter getTimestamp() {
        return timestamp;
    }

    public ZonedDateTimeFilter timestamp() {
        if (timestamp == null) {
            timestamp = new ZonedDateTimeFilter();
        }
        return timestamp;
    }

    public void setTimestamp(ZonedDateTimeFilter timestamp) {
        this.timestamp = timestamp;
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
        final PeriodoLancamentoNotaCriteria that = (PeriodoLancamentoNotaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipoAvaliacao, that.tipoAvaliacao) &&
            Objects.equals(de, that.de) &&
            Objects.equals(ate, that.ate) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(classeId, that.classeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoAvaliacao, de, ate, timestamp, utilizadorId, classeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoLancamentoNotaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipoAvaliacao != null ? "tipoAvaliacao=" + tipoAvaliacao + ", " : "") +
            (de != null ? "de=" + de + ", " : "") +
            (ate != null ? "ate=" + ate + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (classeId != null ? "classeId=" + classeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
