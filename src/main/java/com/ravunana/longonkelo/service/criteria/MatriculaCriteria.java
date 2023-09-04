package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.EstadoAcademico;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Matricula} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.MatriculaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /matriculas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatriculaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstadoAcademico
     */
    public static class EstadoAcademicoFilter extends Filter<EstadoAcademico> {

        public EstadoAcademicoFilter() {}

        public EstadoAcademicoFilter(EstadoAcademicoFilter filter) {
            super(filter);
        }

        @Override
        public EstadoAcademicoFilter copy() {
            return new EstadoAcademicoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chaveComposta1;

    private StringFilter chaveComposta2;

    private StringFilter numeroMatricula;

    private IntegerFilter numeroChamada;

    private EstadoAcademicoFilter estado;

    private ZonedDateTimeFilter timestamp;

    private BooleanFilter isAceiteTermosCompromisso;

    private LongFilter matriculaId;

    private LongFilter facturasId;

    private LongFilter transacoesId;

    private LongFilter recibosId;

    private LongFilter notasPeriodicaDisciplinaId;

    private LongFilter notasGeralDisciplinaId;

    private LongFilter transferenciaTurmaId;

    private LongFilter ocorrenciaId;

    private LongFilter utilizadorId;

    private LongFilter categoriasMatriculasId;

    private LongFilter turmaId;

    private LongFilter responsavelFinanceiroId;

    private LongFilter discenteId;

    private LongFilter referenciaId;

    private Boolean distinct;

    public MatriculaCriteria() {}

    public MatriculaCriteria(MatriculaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chaveComposta1 = other.chaveComposta1 == null ? null : other.chaveComposta1.copy();
        this.chaveComposta2 = other.chaveComposta2 == null ? null : other.chaveComposta2.copy();
        this.numeroMatricula = other.numeroMatricula == null ? null : other.numeroMatricula.copy();
        this.numeroChamada = other.numeroChamada == null ? null : other.numeroChamada.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.timestamp = other.timestamp == null ? null : other.timestamp.copy();
        this.isAceiteTermosCompromisso = other.isAceiteTermosCompromisso == null ? null : other.isAceiteTermosCompromisso.copy();
        this.matriculaId = other.matriculaId == null ? null : other.matriculaId.copy();
        this.facturasId = other.facturasId == null ? null : other.facturasId.copy();
        this.transacoesId = other.transacoesId == null ? null : other.transacoesId.copy();
        this.recibosId = other.recibosId == null ? null : other.recibosId.copy();
        this.notasPeriodicaDisciplinaId = other.notasPeriodicaDisciplinaId == null ? null : other.notasPeriodicaDisciplinaId.copy();
        this.notasGeralDisciplinaId = other.notasGeralDisciplinaId == null ? null : other.notasGeralDisciplinaId.copy();
        this.transferenciaTurmaId = other.transferenciaTurmaId == null ? null : other.transferenciaTurmaId.copy();
        this.ocorrenciaId = other.ocorrenciaId == null ? null : other.ocorrenciaId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.categoriasMatriculasId = other.categoriasMatriculasId == null ? null : other.categoriasMatriculasId.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.responsavelFinanceiroId = other.responsavelFinanceiroId == null ? null : other.responsavelFinanceiroId.copy();
        this.discenteId = other.discenteId == null ? null : other.discenteId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MatriculaCriteria copy() {
        return new MatriculaCriteria(this);
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

    public StringFilter getChaveComposta1() {
        return chaveComposta1;
    }

    public StringFilter chaveComposta1() {
        if (chaveComposta1 == null) {
            chaveComposta1 = new StringFilter();
        }
        return chaveComposta1;
    }

    public void setChaveComposta1(StringFilter chaveComposta1) {
        this.chaveComposta1 = chaveComposta1;
    }

    public StringFilter getChaveComposta2() {
        return chaveComposta2;
    }

    public StringFilter chaveComposta2() {
        if (chaveComposta2 == null) {
            chaveComposta2 = new StringFilter();
        }
        return chaveComposta2;
    }

    public void setChaveComposta2(StringFilter chaveComposta2) {
        this.chaveComposta2 = chaveComposta2;
    }

    public StringFilter getNumeroMatricula() {
        return numeroMatricula;
    }

    public StringFilter numeroMatricula() {
        if (numeroMatricula == null) {
            numeroMatricula = new StringFilter();
        }
        return numeroMatricula;
    }

    public void setNumeroMatricula(StringFilter numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public IntegerFilter getNumeroChamada() {
        return numeroChamada;
    }

    public IntegerFilter numeroChamada() {
        if (numeroChamada == null) {
            numeroChamada = new IntegerFilter();
        }
        return numeroChamada;
    }

    public void setNumeroChamada(IntegerFilter numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public EstadoAcademicoFilter getEstado() {
        return estado;
    }

    public EstadoAcademicoFilter estado() {
        if (estado == null) {
            estado = new EstadoAcademicoFilter();
        }
        return estado;
    }

    public void setEstado(EstadoAcademicoFilter estado) {
        this.estado = estado;
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

    public LongFilter getFacturasId() {
        return facturasId;
    }

    public LongFilter facturasId() {
        if (facturasId == null) {
            facturasId = new LongFilter();
        }
        return facturasId;
    }

    public void setFacturasId(LongFilter facturasId) {
        this.facturasId = facturasId;
    }

    public LongFilter getTransacoesId() {
        return transacoesId;
    }

    public LongFilter transacoesId() {
        if (transacoesId == null) {
            transacoesId = new LongFilter();
        }
        return transacoesId;
    }

    public void setTransacoesId(LongFilter transacoesId) {
        this.transacoesId = transacoesId;
    }

    public LongFilter getRecibosId() {
        return recibosId;
    }

    public LongFilter recibosId() {
        if (recibosId == null) {
            recibosId = new LongFilter();
        }
        return recibosId;
    }

    public void setRecibosId(LongFilter recibosId) {
        this.recibosId = recibosId;
    }

    public LongFilter getNotasPeriodicaDisciplinaId() {
        return notasPeriodicaDisciplinaId;
    }

    public LongFilter notasPeriodicaDisciplinaId() {
        if (notasPeriodicaDisciplinaId == null) {
            notasPeriodicaDisciplinaId = new LongFilter();
        }
        return notasPeriodicaDisciplinaId;
    }

    public void setNotasPeriodicaDisciplinaId(LongFilter notasPeriodicaDisciplinaId) {
        this.notasPeriodicaDisciplinaId = notasPeriodicaDisciplinaId;
    }

    public LongFilter getNotasGeralDisciplinaId() {
        return notasGeralDisciplinaId;
    }

    public LongFilter notasGeralDisciplinaId() {
        if (notasGeralDisciplinaId == null) {
            notasGeralDisciplinaId = new LongFilter();
        }
        return notasGeralDisciplinaId;
    }

    public void setNotasGeralDisciplinaId(LongFilter notasGeralDisciplinaId) {
        this.notasGeralDisciplinaId = notasGeralDisciplinaId;
    }

    public LongFilter getTransferenciaTurmaId() {
        return transferenciaTurmaId;
    }

    public LongFilter transferenciaTurmaId() {
        if (transferenciaTurmaId == null) {
            transferenciaTurmaId = new LongFilter();
        }
        return transferenciaTurmaId;
    }

    public void setTransferenciaTurmaId(LongFilter transferenciaTurmaId) {
        this.transferenciaTurmaId = transferenciaTurmaId;
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

    public LongFilter getCategoriasMatriculasId() {
        return categoriasMatriculasId;
    }

    public LongFilter categoriasMatriculasId() {
        if (categoriasMatriculasId == null) {
            categoriasMatriculasId = new LongFilter();
        }
        return categoriasMatriculasId;
    }

    public void setCategoriasMatriculasId(LongFilter categoriasMatriculasId) {
        this.categoriasMatriculasId = categoriasMatriculasId;
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

    public LongFilter getResponsavelFinanceiroId() {
        return responsavelFinanceiroId;
    }

    public LongFilter responsavelFinanceiroId() {
        if (responsavelFinanceiroId == null) {
            responsavelFinanceiroId = new LongFilter();
        }
        return responsavelFinanceiroId;
    }

    public void setResponsavelFinanceiroId(LongFilter responsavelFinanceiroId) {
        this.responsavelFinanceiroId = responsavelFinanceiroId;
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
        final MatriculaCriteria that = (MatriculaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(chaveComposta1, that.chaveComposta1) &&
            Objects.equals(chaveComposta2, that.chaveComposta2) &&
            Objects.equals(numeroMatricula, that.numeroMatricula) &&
            Objects.equals(numeroChamada, that.numeroChamada) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(isAceiteTermosCompromisso, that.isAceiteTermosCompromisso) &&
            Objects.equals(matriculaId, that.matriculaId) &&
            Objects.equals(facturasId, that.facturasId) &&
            Objects.equals(transacoesId, that.transacoesId) &&
            Objects.equals(recibosId, that.recibosId) &&
            Objects.equals(notasPeriodicaDisciplinaId, that.notasPeriodicaDisciplinaId) &&
            Objects.equals(notasGeralDisciplinaId, that.notasGeralDisciplinaId) &&
            Objects.equals(transferenciaTurmaId, that.transferenciaTurmaId) &&
            Objects.equals(ocorrenciaId, that.ocorrenciaId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(categoriasMatriculasId, that.categoriasMatriculasId) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(responsavelFinanceiroId, that.responsavelFinanceiroId) &&
            Objects.equals(discenteId, that.discenteId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            chaveComposta1,
            chaveComposta2,
            numeroMatricula,
            numeroChamada,
            estado,
            timestamp,
            isAceiteTermosCompromisso,
            matriculaId,
            facturasId,
            transacoesId,
            recibosId,
            notasPeriodicaDisciplinaId,
            notasGeralDisciplinaId,
            transferenciaTurmaId,
            ocorrenciaId,
            utilizadorId,
            categoriasMatriculasId,
            turmaId,
            responsavelFinanceiroId,
            discenteId,
            referenciaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatriculaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (chaveComposta1 != null ? "chaveComposta1=" + chaveComposta1 + ", " : "") +
            (chaveComposta2 != null ? "chaveComposta2=" + chaveComposta2 + ", " : "") +
            (numeroMatricula != null ? "numeroMatricula=" + numeroMatricula + ", " : "") +
            (numeroChamada != null ? "numeroChamada=" + numeroChamada + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (timestamp != null ? "timestamp=" + timestamp + ", " : "") +
            (isAceiteTermosCompromisso != null ? "isAceiteTermosCompromisso=" + isAceiteTermosCompromisso + ", " : "") +
            (matriculaId != null ? "matriculaId=" + matriculaId + ", " : "") +
            (facturasId != null ? "facturasId=" + facturasId + ", " : "") +
            (transacoesId != null ? "transacoesId=" + transacoesId + ", " : "") +
            (recibosId != null ? "recibosId=" + recibosId + ", " : "") +
            (notasPeriodicaDisciplinaId != null ? "notasPeriodicaDisciplinaId=" + notasPeriodicaDisciplinaId + ", " : "") +
            (notasGeralDisciplinaId != null ? "notasGeralDisciplinaId=" + notasGeralDisciplinaId + ", " : "") +
            (transferenciaTurmaId != null ? "transferenciaTurmaId=" + transferenciaTurmaId + ", " : "") +
            (ocorrenciaId != null ? "ocorrenciaId=" + ocorrenciaId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (categoriasMatriculasId != null ? "categoriasMatriculasId=" + categoriasMatriculasId + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (responsavelFinanceiroId != null ? "responsavelFinanceiroId=" + responsavelFinanceiroId + ", " : "") +
            (discenteId != null ? "discenteId=" + discenteId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
