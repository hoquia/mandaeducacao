package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.LongonkeloHistorico} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.LongonkeloHistoricoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /longonkelo-historicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LongonkeloHistoricoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter operacao;

    private StringFilter entidadeNome;

    private StringFilter entidadeCodigo;

    private StringFilter host;

    private StringFilter hash;

    private ZonedDateTimeFilter timestamp;

    private LongFilter utilizadorId;

    private Boolean distinct;

    public LongonkeloHistoricoCriteria() {}

    public LongonkeloHistoricoCriteria(LongonkeloHistoricoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.operacao = other.operacao == null ? null : other.operacao.copy();
        this.entidadeNome = other.entidadeNome == null ? null : other.entidadeNome.copy();
        this.entidadeCodigo = other.entidadeCodigo == null ? null : other.entidadeCodigo.copy();
        this.host = other.host == null ? null : other.host.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LongonkeloHistoricoCriteria copy() {
        return new LongonkeloHistoricoCriteria(this);
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

    public StringFilter getOperacao() {
        return operacao;
    }

    public StringFilter operacao() {
        if (operacao == null) {
            operacao = new StringFilter();
        }
        return operacao;
    }

    public void setOperacao(StringFilter operacao) {
        this.operacao = operacao;
    }

    public StringFilter getEntidadeNome() {
        return entidadeNome;
    }

    public StringFilter entidadeNome() {
        if (entidadeNome == null) {
            entidadeNome = new StringFilter();
        }
        return entidadeNome;
    }

    public void setEntidadeNome(StringFilter entidadeNome) {
        this.entidadeNome = entidadeNome;
    }

    public StringFilter getEntidadeCodigo() {
        return entidadeCodigo;
    }

    public StringFilter entidadeCodigo() {
        if (entidadeCodigo == null) {
            entidadeCodigo = new StringFilter();
        }
        return entidadeCodigo;
    }

    public void setEntidadeCodigo(StringFilter entidadeCodigo) {
        this.entidadeCodigo = entidadeCodigo;
    }

    public StringFilter getHost() {
        return host;
    }

    public StringFilter host() {
        if (host == null) {
            host = new StringFilter();
        }
        return host;
    }

    public void setHost(StringFilter host) {
        this.host = host;
    }

    public StringFilter getHash() {
        return hash;
    }

    public StringFilter hash() {
        if (hash == null) {
            hash = new StringFilter();
        }
        return hash;
    }

    public void setHash(StringFilter hash) {
        this.hash = hash;
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
        final LongonkeloHistoricoCriteria that = (LongonkeloHistoricoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(operacao, that.operacao) &&
            Objects.equals(entidadeNome, that.entidadeNome) &&
            Objects.equals(entidadeCodigo, that.entidadeCodigo) &&
            Objects.equals(host, that.host) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operacao, entidadeNome, entidadeCodigo, host, hash, timestamp, utilizadorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LongonkeloHistoricoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (operacao != null ? "operacao=" + operacao + ", " : "") +
            (entidadeNome != null ? "entidadeNome=" + entidadeNome + ", " : "") +
            (entidadeCodigo != null ? "entidadeCodigo=" + entidadeCodigo + ", " : "") +
            (host != null ? "host=" + host + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
