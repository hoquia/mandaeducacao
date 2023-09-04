package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.HistoricoSaude} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.HistoricoSaudeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /historico-saudes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistoricoSaudeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private ZonedDateTimeFilter inicio;

    private ZonedDateTimeFilter fim;

    private StringFilter situacaoPrescricao;

    private ZonedDateTimeFilter timestamp;

    private StringFilter hash;

    private LongFilter utilizadorId;

    private LongFilter discenteId;

    private Boolean distinct;

    public HistoricoSaudeCriteria() {}

    public HistoricoSaudeCriteria(HistoricoSaudeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.inicio = other.inicio == null ? null : other.inicio.copy();
        this.fim = other.fim == null ? null : other.fim.copy();
        this.situacaoPrescricao = other.situacaoPrescricao == null ? null : other.situacaoPrescricao.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.discenteId = other.discenteId == null ? null : other.discenteId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public HistoricoSaudeCriteria copy() {
        return new HistoricoSaudeCriteria(this);
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

    public ZonedDateTimeFilter getInicio() {
        return inicio;
    }

    public ZonedDateTimeFilter inicio() {
        if (inicio == null) {
            inicio = new ZonedDateTimeFilter();
        }
        return inicio;
    }

    public void setInicio(ZonedDateTimeFilter inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTimeFilter getFim() {
        return fim;
    }

    public ZonedDateTimeFilter fim() {
        if (fim == null) {
            fim = new ZonedDateTimeFilter();
        }
        return fim;
    }

    public void setFim(ZonedDateTimeFilter fim) {
        this.fim = fim;
    }

    public StringFilter getSituacaoPrescricao() {
        return situacaoPrescricao;
    }

    public StringFilter situacaoPrescricao() {
        if (situacaoPrescricao == null) {
            situacaoPrescricao = new StringFilter();
        }
        return situacaoPrescricao;
    }

    public void setSituacaoPrescricao(StringFilter situacaoPrescricao) {
        this.situacaoPrescricao = situacaoPrescricao;
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
        final HistoricoSaudeCriteria that = (HistoricoSaudeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(inicio, that.inicio) &&
            Objects.equals(fim, that.fim) &&
            Objects.equals(situacaoPrescricao, that.situacaoPrescricao) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(discenteId, that.discenteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, inicio, fim, situacaoPrescricao, timestamp, hash, utilizadorId, discenteId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoricoSaudeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (inicio != null ? "inicio=" + inicio + ", " : "") +
            (fim != null ? "fim=" + fim + ", " : "") +
            (situacaoPrescricao != null ? "situacaoPrescricao=" + situacaoPrescricao + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (discenteId != null ? "discenteId=" + discenteId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
