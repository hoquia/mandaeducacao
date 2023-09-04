package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.NotasGeralDisciplina} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.NotasGeralDisciplinaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notas-geral-disciplinas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotasGeralDisciplinaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chaveComposta;

    private IntegerFilter periodoLancamento;

    private DoubleFilter media1;

    private DoubleFilter media2;

    private DoubleFilter media3;

    private DoubleFilter exame;

    private DoubleFilter recurso;

    private DoubleFilter exameEspecial;

    private DoubleFilter notaConselho;

    private DoubleFilter mediaFinalDisciplina;

    private ZonedDateTimeFilter timestamp;

    private StringFilter hash;

    private IntegerFilter faltaJusticada;

    private IntegerFilter faltaInjustificada;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter docenteId;

    private LongFilter disciplinaCurricularId;

    private LongFilter matriculaId;

    private LongFilter estadoId;

    private Boolean distinct;

    public NotasGeralDisciplinaCriteria() {}

    public NotasGeralDisciplinaCriteria(NotasGeralDisciplinaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chaveComposta = other.chaveComposta == null ? null : other.chaveComposta.copy();
        this.periodoLancamento = other.periodoLancamento == null ? null : other.periodoLancamento.copy();
        this.media1 = other.media1 == null ? null : other.media1.copy();
        this.media2 = other.media2 == null ? null : other.media2.copy();
        this.media3 = other.media3 == null ? null : other.media3.copy();
        this.exame = other.exame == null ? null : other.exame.copy();
        this.recurso = other.recurso == null ? null : other.recurso.copy();
        this.exameEspecial = other.exameEspecial == null ? null : other.exameEspecial.copy();
        this.notaConselho = other.notaConselho == null ? null : other.notaConselho.copy();
        this.mediaFinalDisciplina = other.mediaFinalDisciplina == null ? null : other.mediaFinalDisciplina.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.faltaJusticada = other.faltaJusticada == null ? null : other.faltaJusticada.copy();
        this.faltaInjustificada = other.faltaInjustificada == null ? null : other.faltaInjustificada.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.docenteId = other.docenteId == null ? null : other.docenteId.copy();
        this.disciplinaCurricularId = other.disciplinaCurricularId == null ? null : other.disciplinaCurricularId.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotasGeralDisciplinaCriteria copy() {
        return new NotasGeralDisciplinaCriteria(this);
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

    public StringFilter getChaveComposta() {
        return chaveComposta;
    }

    public StringFilter chaveComposta() {
        if (chaveComposta == null) {
            chaveComposta = new StringFilter();
        }
        return chaveComposta;
    }

    public void setChaveComposta(StringFilter chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public IntegerFilter getPeriodoLancamento() {
        return periodoLancamento;
    }

    public IntegerFilter periodoLancamento() {
        if (periodoLancamento == null) {
            periodoLancamento = new IntegerFilter();
        }
        return periodoLancamento;
    }

    public void setPeriodoLancamento(IntegerFilter periodoLancamento) {
        this.periodoLancamento = periodoLancamento;
    }

    public DoubleFilter getMedia1() {
        return media1;
    }

    public DoubleFilter media1() {
        if (media1 == null) {
            media1 = new DoubleFilter();
        }
        return media1;
    }

    public void setMedia1(DoubleFilter media1) {
        this.media1 = media1;
    }

    public DoubleFilter getMedia2() {
        return media2;
    }

    public DoubleFilter media2() {
        if (media2 == null) {
            media2 = new DoubleFilter();
        }
        return media2;
    }

    public void setMedia2(DoubleFilter media2) {
        this.media2 = media2;
    }

    public DoubleFilter getMedia3() {
        return media3;
    }

    public DoubleFilter media3() {
        if (media3 == null) {
            media3 = new DoubleFilter();
        }
        return media3;
    }

    public void setMedia3(DoubleFilter media3) {
        this.media3 = media3;
    }

    public DoubleFilter getExame() {
        return exame;
    }

    public DoubleFilter exame() {
        if (exame == null) {
            exame = new DoubleFilter();
        }
        return exame;
    }

    public void setExame(DoubleFilter exame) {
        this.exame = exame;
    }

    public DoubleFilter getRecurso() {
        return recurso;
    }

    public DoubleFilter recurso() {
        if (recurso == null) {
            recurso = new DoubleFilter();
        }
        return recurso;
    }

    public void setRecurso(DoubleFilter recurso) {
        this.recurso = recurso;
    }

    public DoubleFilter getExameEspecial() {
        return exameEspecial;
    }

    public DoubleFilter exameEspecial() {
        if (exameEspecial == null) {
            exameEspecial = new DoubleFilter();
        }
        return exameEspecial;
    }

    public void setExameEspecial(DoubleFilter exameEspecial) {
        this.exameEspecial = exameEspecial;
    }

    public DoubleFilter getNotaConselho() {
        return notaConselho;
    }

    public DoubleFilter notaConselho() {
        if (notaConselho == null) {
            notaConselho = new DoubleFilter();
        }
        return notaConselho;
    }

    public void setNotaConselho(DoubleFilter notaConselho) {
        this.notaConselho = notaConselho;
    }

    public DoubleFilter getMediaFinalDisciplina() {
        return mediaFinalDisciplina;
    }

    public DoubleFilter mediaFinalDisciplina() {
        if (mediaFinalDisciplina == null) {
            mediaFinalDisciplina = new DoubleFilter();
        }
        return mediaFinalDisciplina;
    }

    public void setMediaFinalDisciplina(DoubleFilter mediaFinalDisciplina) {
        this.mediaFinalDisciplina = mediaFinalDisciplina;
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

    public IntegerFilter getFaltaJusticada() {
        return faltaJusticada;
    }

    public IntegerFilter faltaJusticada() {
        if (faltaJusticada == null) {
            faltaJusticada = new IntegerFilter();
        }
        return faltaJusticada;
    }

    public void setFaltaJusticada(IntegerFilter faltaJusticada) {
        this.faltaJusticada = faltaJusticada;
    }

    public IntegerFilter getFaltaInjustificada() {
        return faltaInjustificada;
    }

    public IntegerFilter faltaInjustificada() {
        if (faltaInjustificada == null) {
            faltaInjustificada = new IntegerFilter();
        }
        return faltaInjustificada;
    }

    public void setFaltaInjustificada(IntegerFilter faltaInjustificada) {
        this.faltaInjustificada = faltaInjustificada;
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
        final NotasGeralDisciplinaCriteria that = (NotasGeralDisciplinaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(chaveComposta, that.chaveComposta) &&
            Objects.equals(periodoLancamento, that.periodoLancamento) &&
            Objects.equals(media1, that.media1) &&
            Objects.equals(media2, that.media2) &&
            Objects.equals(media3, that.media3) &&
            Objects.equals(exame, that.exame) &&
            Objects.equals(recurso, that.recurso) &&
            Objects.equals(exameEspecial, that.exameEspecial) &&
            Objects.equals(notaConselho, that.notaConselho) &&
            Objects.equals(mediaFinalDisciplina, that.mediaFinalDisciplina) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(faltaJusticada, that.faltaJusticada) &&
            Objects.equals(faltaInjustificada, that.faltaInjustificada) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(docenteId, that.docenteId) &&
            Objects.equals(disciplinaCurricularId, that.disciplinaCurricularId) &&
            Objects.equals(matriculaId, that.matriculaId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            chaveComposta,
            periodoLancamento,
            media1,
            media2,
            media3,
            exame,
            recurso,
            exameEspecial,
            notaConselho,
            mediaFinalDisciplina,
            timestamp,
            hash,
            faltaJusticada,
            faltaInjustificada,
            anoLectivoId,
            utilizadorId,
            docenteId,
            disciplinaCurricularId,
            matriculaId,
            estadoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotasGeralDisciplinaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (chaveComposta != null ? "chaveComposta=" + chaveComposta + ", " : "") +
            (periodoLancamento != null ? "periodoLancamento=" + periodoLancamento + ", " : "") +
            (media1 != null ? "media1=" + media1 + ", " : "") +
            (media2 != null ? "media2=" + media2 + ", " : "") +
            (media3 != null ? "media3=" + media3 + ", " : "") +
            (exame != null ? "exame=" + exame + ", " : "") +
            (recurso != null ? "recurso=" + recurso + ", " : "") +
            (exameEspecial != null ? "exameEspecial=" + exameEspecial + ", " : "") +
            (notaConselho != null ? "notaConselho=" + notaConselho + ", " : "") +
            (mediaFinalDisciplina != null ? "mediaFinalDisciplina=" + mediaFinalDisciplina + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (faltaJusticada != null ? "faltaJusticada=" + faltaJusticada + ", " : "") +
            (faltaInjustificada != null ? "faltaInjustificada=" + faltaInjustificada + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (docenteId != null ? "docenteId=" + docenteId + ", " : "") +
            (disciplinaCurricularId != null ? "disciplinaCurricularId=" + disciplinaCurricularId + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
