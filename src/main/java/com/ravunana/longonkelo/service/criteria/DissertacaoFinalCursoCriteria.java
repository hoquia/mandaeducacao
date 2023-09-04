package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.DissertacaoFinalCurso} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.DissertacaoFinalCursoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dissertacao-final-cursos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DissertacaoFinalCursoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numero;

    private ZonedDateTimeFilter timestamp;

    private LocalDateFilter data;

    private StringFilter tema;

    private StringFilter objectivoGeral;

    private StringFilter hash;

    private BooleanFilter isAceiteTermosCompromisso;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter turmaId;

    private LongFilter orientadorId;

    private LongFilter especialidadeId;

    private LongFilter discenteId;

    private LongFilter estadoId;

    private LongFilter naturezaId;

    private Boolean distinct;

    public DissertacaoFinalCursoCriteria() {}

    public DissertacaoFinalCursoCriteria(DissertacaoFinalCursoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.tema = other.tema == null ? null : other.tema.copy();
        this.objectivoGeral = other.objectivoGeral == null ? null : other.objectivoGeral.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.isAceiteTermosCompromisso = other.isAceiteTermosCompromisso == null ? null : other.isAceiteTermosCompromisso.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.orientadorId = other.orientadorId == null ? null : other.orientadorId.copy();
        this.especialidadeId = other.especialidadeId == null ? null : other.especialidadeId.copy();
        this.discenteId = other.discenteId == null ? null : other.discenteId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.naturezaId = other.naturezaId == null ? null : other.naturezaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DissertacaoFinalCursoCriteria copy() {
        return new DissertacaoFinalCursoCriteria(this);
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

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
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

    public StringFilter getTema() {
        return tema;
    }

    public StringFilter tema() {
        if (tema == null) {
            tema = new StringFilter();
        }
        return tema;
    }

    public void setTema(StringFilter tema) {
        this.tema = tema;
    }

    public StringFilter getObjectivoGeral() {
        return objectivoGeral;
    }

    public StringFilter objectivoGeral() {
        if (objectivoGeral == null) {
            objectivoGeral = new StringFilter();
        }
        return objectivoGeral;
    }

    public void setObjectivoGeral(StringFilter objectivoGeral) {
        this.objectivoGeral = objectivoGeral;
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

    public BooleanFilter getIsAceiteTermosCompromisso() {
        return isAceiteTermosCompromisso;
    }

    public BooleanFilter isAceiteTermosCompromisso() {
        if (isAceiteTermosCompromisso == null) {
            isAceiteTermosCompromisso = new BooleanFilter();
        }
        return isAceiteTermosCompromisso;
    }

    public void setIsAceiteTermosCompromisso(BooleanFilter isAceiteTermosCompromisso) {
        this.isAceiteTermosCompromisso = isAceiteTermosCompromisso;
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

    public LongFilter getOrientadorId() {
        return orientadorId;
    }

    public LongFilter orientadorId() {
        if (orientadorId == null) {
            orientadorId = new LongFilter();
        }
        return orientadorId;
    }

    public void setOrientadorId(LongFilter orientadorId) {
        this.orientadorId = orientadorId;
    }

    public LongFilter getEspecialidadeId() {
        return especialidadeId;
    }

    public LongFilter especialidadeId() {
        if (especialidadeId == null) {
            especialidadeId = new LongFilter();
        }
        return especialidadeId;
    }

    public void setEspecialidadeId(LongFilter especialidadeId) {
        this.especialidadeId = especialidadeId;
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

    public LongFilter getNaturezaId() {
        return naturezaId;
    }

    public LongFilter naturezaId() {
        if (naturezaId == null) {
            naturezaId = new LongFilter();
        }
        return naturezaId;
    }

    public void setNaturezaId(LongFilter naturezaId) {
        this.naturezaId = naturezaId;
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
        final DissertacaoFinalCursoCriteria that = (DissertacaoFinalCursoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(data, that.data) &&
            Objects.equals(tema, that.tema) &&
            Objects.equals(objectivoGeral, that.objectivoGeral) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(isAceiteTermosCompromisso, that.isAceiteTermosCompromisso) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(orientadorId, that.orientadorId) &&
            Objects.equals(especialidadeId, that.especialidadeId) &&
            Objects.equals(discenteId, that.discenteId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(naturezaId, that.naturezaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            numero,
            timestamp,
            data,
            tema,
            objectivoGeral,
            hash,
            isAceiteTermosCompromisso,
            anoLectivoId,
            utilizadorId,
            turmaId,
            orientadorId,
            especialidadeId,
            discenteId,
            estadoId,
            naturezaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DissertacaoFinalCursoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (tema != null ? "tema=" + tema + ", " : "") +
            (objectivoGeral != null ? "objectivoGeral=" + objectivoGeral + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (isAceiteTermosCompromisso != null ? "isAceiteTermosCompromisso=" + isAceiteTermosCompromisso + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (orientadorId != null ? "orientadorId=" + orientadorId + ", " : "") +
            (especialidadeId != null ? "especialidadeId=" + especialidadeId + ", " : "") +
            (discenteId != null ? "discenteId=" + discenteId + ", " : "") +
            (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            (naturezaId != null ? "naturezaId=" + naturezaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
