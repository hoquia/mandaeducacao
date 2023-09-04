package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.SequenciaDocumento} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.SequenciaDocumentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequencia-documentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SequenciaDocumentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter sequencia;

    private LocalDateFilter data;

    private StringFilter hash;

    private ZonedDateTimeFilter timestamp;

    private LongFilter serieId;

    private Boolean distinct;

    public SequenciaDocumentoCriteria() {}

    public SequenciaDocumentoCriteria(SequenciaDocumentoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.sequencia = other.sequencia == null ? null : other.sequencia.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.serieId = other.serieId == null ? null : other.serieId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SequenciaDocumentoCriteria copy() {
        return new SequenciaDocumentoCriteria(this);
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

    public LongFilter getSequencia() {
        return sequencia;
    }

    public LongFilter sequencia() {
        if (sequencia == null) {
            sequencia = new LongFilter();
        }
        return sequencia;
    }

    public void setSequencia(LongFilter sequencia) {
        this.sequencia = sequencia;
    }

    public LocalDateFilter getData() {
        return data;
    }

    public LocalDateFilter data() {
        if (data == null) {
            data = new LocalDateFilter();
        }
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
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

    public LongFilter getSerieId() {
        return serieId;
    }

    public LongFilter serieId() {
        if (serieId == null) {
            serieId = new LongFilter();
        }
        return serieId;
    }

    public void setSerieId(LongFilter serieId) {
        this.serieId = serieId;
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
        final SequenciaDocumentoCriteria that = (SequenciaDocumentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(sequencia, that.sequencia) &&
            Objects.equals(data, that.data) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(serieId, that.serieId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequencia, data, hash, timestamp, serieId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenciaDocumentoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (sequencia != null ? "sequencia=" + sequencia + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (serieId != null ? "serieId=" + serieId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
