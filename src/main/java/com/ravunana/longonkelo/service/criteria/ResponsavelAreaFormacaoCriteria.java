package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.ResponsavelAreaFormacao} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.ResponsavelAreaFormacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /responsavel-area-formacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResponsavelAreaFormacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter de;

    private LocalDateFilter ate;

    private ZonedDateTimeFilter timestamp;

    private LongFilter responsavelId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter areaFormacaoId;

    private Boolean distinct;

    public ResponsavelAreaFormacaoCriteria() {}

    public ResponsavelAreaFormacaoCriteria(ResponsavelAreaFormacaoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.de = other.de == null ? null : other.de.copy();
        this.ate = other.ate == null ? null : other.ate.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.responsavelId = other.responsavelId == null ? null : other.responsavelId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.areaFormacaoId = other.areaFormacaoId == null ? null : other.areaFormacaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ResponsavelAreaFormacaoCriteria copy() {
        return new ResponsavelAreaFormacaoCriteria(this);
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

    public LocalDateFilter getDe() {
        return de;
    }

    public LocalDateFilter de() {
        if (de == null) {
            de = new LocalDateFilter();
        }
        return de;
    }

    public void setDe(LocalDateFilter de) {
        this.de = de;
    }

    public LocalDateFilter getAte() {
        return ate;
    }

    public LocalDateFilter ate() {
        if (ate == null) {
            ate = new LocalDateFilter();
        }
        return ate;
    }

    public void setAte(LocalDateFilter ate) {
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

    public LongFilter getResponsavelId() {
        return responsavelId;
    }

    public LongFilter responsavelId() {
        if (responsavelId == null) {
            responsavelId = new LongFilter();
        }
        return responsavelId;
    }

    public void setResponsavelId(LongFilter responsavelId) {
        this.responsavelId = responsavelId;
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
        final ResponsavelAreaFormacaoCriteria that = (ResponsavelAreaFormacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(de, that.de) &&
            Objects.equals(ate, that.ate) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(responsavelId, that.responsavelId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(areaFormacaoId, that.areaFormacaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, de, ate, timestamp, responsavelId, anoLectivoId, utilizadorId, areaFormacaoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelAreaFormacaoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (de != null ? "de=" + de + ", " : "") +
            (ate != null ? "ate=" + ate + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (responsavelId != null ? "responsavelId=" + responsavelId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (areaFormacaoId != null ? "areaFormacaoId=" + areaFormacaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
