package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Ocorrencia} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.OcorrenciaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ocorrencias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OcorrenciaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uniqueOcorrencia;

    private StringFilter hash;

    private ZonedDateTimeFilter timestamp;

    private LongFilter ocorrenciaId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter referenciaId;

    private LongFilter docenteId;

    private LongFilter matriculaId;

    private LongFilter estadoId;

    private LongFilter licaoId;

    private Boolean distinct;

    public OcorrenciaCriteria() {}

    public OcorrenciaCriteria(OcorrenciaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uniqueOcorrencia = other.uniqueOcorrencia == null ? null : other.uniqueOcorrencia.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.ocorrenciaId = other.ocorrenciaId == null ? null : other.ocorrenciaId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.docenteId = other.docenteId == null ? null : other.docenteId.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.licaoId = other.licaoId == null ? null : other.licaoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OcorrenciaCriteria copy() {
        return new OcorrenciaCriteria(this);
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

    public StringFilter getUniqueOcorrencia() {
        return uniqueOcorrencia;
    }

    public StringFilter uniqueOcorrencia() {
        if (uniqueOcorrencia == null) {
            uniqueOcorrencia = new StringFilter();
        }
        return uniqueOcorrencia;
    }

    public void setUniqueOcorrencia(StringFilter uniqueOcorrencia) {
        this.uniqueOcorrencia = uniqueOcorrencia;
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

    public LongFilter getOcorrenciaId() {
        return ocorrenciaId;
    }

    public LongFilter ocorrenciaId() {
        if (ocorrenciaId == null) {
            ocorrenciaId = new LongFilter();
        }
        return ocorrenciaId;
    }

    public void setOcorrenciaId(LongFilter ocorrenciaId) {
        this.ocorrenciaId = ocorrenciaId;
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

    public LongFilter getMatriculaId() {
        return matriculaId;
    }

    public LongFilter matriculaId() {
        if (matriculaId == null) {
            matriculaId = new LongFilter();
        }
        return matriculaId;
    }

    public void setMatriculaId(LongFilter matriculaId) {
        this.matriculaId = matriculaId;
    }

    public LongFilter getEstadoId() {
        return estadoId;
    }

    public LongFilter estadoId() {
        if (estadoId == null) {
            estadoId = new LongFilter();
        }
        return estadoId;
    }

    public void setEstadoId(LongFilter estadoId) {
        this.estadoId = estadoId;
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
        final OcorrenciaCriteria that = (OcorrenciaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uniqueOcorrencia, that.uniqueOcorrencia) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(ocorrenciaId, that.ocorrenciaId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(docenteId, that.docenteId) &&
            Objects.equals(matriculaId, that.matriculaId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(licaoId, that.licaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            uniqueOcorrencia,
            hash,
            timestamp,
            ocorrenciaId,
            anoLectivoId,
            utilizadorId,
            referenciaId,
            docenteId,
            matriculaId,
            estadoId,
            licaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcorrenciaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uniqueOcorrencia != null ? "uniqueOcorrencia=" + uniqueOcorrencia + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (ocorrenciaId != null ? "ocorrenciaId=" + ocorrenciaId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (docenteId != null ? "docenteId=" + docenteId + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            (licaoId != null ? "licaoId=" + licaoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
