package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.Comporamento;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.NotasPeriodicaDisciplinaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notas-periodica-disciplinas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotasPeriodicaDisciplinaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Comporamento
     */
    public static class ComporamentoFilter extends Filter<Comporamento> {

        public ComporamentoFilter() {}

        public ComporamentoFilter(ComporamentoFilter filter) {
            super(filter);
        }

        @Override
        public ComporamentoFilter copy() {
            return new ComporamentoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chaveComposta;

    private IntegerFilter periodoLancamento;

    private DoubleFilter nota1;

    private DoubleFilter nota2;

    private DoubleFilter nota3;

    private DoubleFilter media;

    private IntegerFilter faltaJusticada;

    private IntegerFilter faltaInjustificada;

    private ComporamentoFilter comportamento;

    private StringFilter hash;

    private ZonedDateTimeFilter timestamp;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter turmaId;

    private LongFilter docenteId;

    private LongFilter disciplinaCurricularId;

    private LongFilter matriculaId;

    private LongFilter estadoId;

    private Boolean distinct;

    public NotasPeriodicaDisciplinaCriteria() {}

    public NotasPeriodicaDisciplinaCriteria(NotasPeriodicaDisciplinaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chaveComposta = other.chaveComposta == null ? null : other.chaveComposta.copy();
        this.periodoLancamento = other.periodoLancamento == null ? null : other.periodoLancamento.copy();
        this.nota1 = other.nota1 == null ? null : other.nota1.copy();
        this.nota2 = other.nota2 == null ? null : other.nota2.copy();
        this.nota3 = other.nota3 == null ? null : other.nota3.copy();
        this.media = other.media == null ? null : other.media.copy();
        this.faltaJusticada = other.faltaJusticada == null ? null : other.faltaJusticada.copy();
        this.faltaInjustificada = other.faltaInjustificada == null ? null : other.faltaInjustificada.copy();
        this.comportamento = other.comportamento == null ? null : other.comportamento.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.docenteId = other.docenteId == null ? null : other.docenteId.copy();
        this.disciplinaCurricularId = other.disciplinaCurricularId == null ? null : other.disciplinaCurricularId.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.estadoId = other.estadoId == null ? null : other.estadoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotasPeriodicaDisciplinaCriteria copy() {
        return new NotasPeriodicaDisciplinaCriteria(this);
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

    public DoubleFilter getNota1() {
        return nota1;
    }

    public DoubleFilter nota1() {
        if (nota1 == null) {
            nota1 = new DoubleFilter();
        }
        return nota1;
    }

    public void setNota1(DoubleFilter nota1) {
        this.nota1 = nota1;
    }

    public DoubleFilter getNota2() {
        return nota2;
    }

    public DoubleFilter nota2() {
        if (nota2 == null) {
            nota2 = new DoubleFilter();
        }
        return nota2;
    }

    public void setNota2(DoubleFilter nota2) {
        this.nota2 = nota2;
    }

    public DoubleFilter getNota3() {
        return nota3;
    }

    public DoubleFilter nota3() {
        if (nota3 == null) {
            nota3 = new DoubleFilter();
        }
        return nota3;
    }

    public void setNota3(DoubleFilter nota3) {
        this.nota3 = nota3;
    }

    public DoubleFilter getMedia() {
        return media;
    }

    public DoubleFilter media() {
        if (media == null) {
            media = new DoubleFilter();
        }
        return media;
    }

    public void setMedia(DoubleFilter media) {
        this.media = media;
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

    public ComporamentoFilter getComportamento() {
        return comportamento;
    }

    public ComporamentoFilter comportamento() {
        if (comportamento == null) {
            comportamento = new ComporamentoFilter();
        }
        return comportamento;
    }

    public void setComportamento(ComporamentoFilter comportamento) {
        this.comportamento = comportamento;
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
        final NotasPeriodicaDisciplinaCriteria that = (NotasPeriodicaDisciplinaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(chaveComposta, that.chaveComposta) &&
            Objects.equals(periodoLancamento, that.periodoLancamento) &&
            Objects.equals(nota1, that.nota1) &&
            Objects.equals(nota2, that.nota2) &&
            Objects.equals(nota3, that.nota3) &&
            Objects.equals(media, that.media) &&
            Objects.equals(faltaJusticada, that.faltaJusticada) &&
            Objects.equals(faltaInjustificada, that.faltaInjustificada) &&
            Objects.equals(comportamento, that.comportamento) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(turmaId, that.turmaId) &&
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
            nota1,
            nota2,
            nota3,
            media,
            faltaJusticada,
            faltaInjustificada,
            comportamento,
            hash,
            timestamp,
            anoLectivoId,
            utilizadorId,
            turmaId,
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
        return "NotasPeriodicaDisciplinaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (chaveComposta != null ? "chaveComposta=" + chaveComposta + ", " : "") +
            (periodoLancamento != null ? "periodoLancamento=" + periodoLancamento + ", " : "") +
            (nota1 != null ? "nota1=" + nota1 + ", " : "") +
            (nota2 != null ? "nota2=" + nota2 + ", " : "") +
            (nota3 != null ? "nota3=" + nota3 + ", " : "") +
            (media != null ? "media=" + media + ", " : "") +
            (faltaJusticada != null ? "faltaJusticada=" + faltaJusticada + ", " : "") +
            (faltaInjustificada != null ? "faltaInjustificada=" + faltaInjustificada + ", " : "") +
            (comportamento != null ? "comportamento=" + comportamento + ", " : "") +
            (hash != null ? "hash=" + hash + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (docenteId != null ? "docenteId=" + docenteId + ", " : "") +
            (disciplinaCurricularId != null ? "disciplinaCurricularId=" + disciplinaCurricularId + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
