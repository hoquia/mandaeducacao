package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import com.ravunana.longonkelo.domain.enumeration.TipoAula;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.PlanoAula} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.PlanoAulaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-aulas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoAulaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoAula
     */
    public static class TipoAulaFilter extends Filter<TipoAula> {

        public TipoAulaFilter() {}

        public TipoAulaFilter(TipoAulaFilter filter) {
            super(filter);
        }

        @Override
        public TipoAulaFilter copy() {
            return new TipoAulaFilter(this);
        }
    }

    /**
     * Class for filtering EstadoLicao
     */
    public static class EstadoLicaoFilter extends Filter<EstadoLicao> {

        public EstadoLicaoFilter() {}

        public EstadoLicaoFilter(EstadoLicaoFilter filter) {
            super(filter);
        }

        @Override
        public EstadoLicaoFilter copy() {
            return new EstadoLicaoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoAulaFilter tipoAula;

    private IntegerFilter semanaLectiva;

    private StringFilter assunto;

    private IntegerFilter tempoTotalLicao;

    private EstadoLicaoFilter estado;

    private LongFilter detalhesId;

    private LongFilter licaoId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter unidadeTematicaId;

    private LongFilter subUnidadeTematicaId;

    private LongFilter turmaId;

    private LongFilter docenteId;

    private LongFilter disciplinaCurricularId;

    private Boolean distinct;

    public PlanoAulaCriteria() {}

    public PlanoAulaCriteria(PlanoAulaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipoAula = other.tipoAula == null ? null : other.tipoAula.copy();
        this.semanaLectiva = other.semanaLectiva == null ? null : other.semanaLectiva.copy();
        this.assunto = other.assunto == null ? null : other.assunto.copy();
        this.tempoTotalLicao = other.tempoTotalLicao == null ? null : other.tempoTotalLicao.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.detalhesId = other.detalhesId == null ? null : other.detalhesId.copy();
        this.licaoId = other.licaoId == null ? null : other.licaoId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.unidadeTematicaId = other.unidadeTematicaId == null ? null : other.unidadeTematicaId.copy();
        this.subUnidadeTematicaId = other.subUnidadeTematicaId == null ? null : other.subUnidadeTematicaId.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.docenteId = other.docenteId == null ? null : other.docenteId.copy();
        this.disciplinaCurricularId = other.disciplinaCurricularId == null ? null : other.disciplinaCurricularId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PlanoAulaCriteria copy() {
        return new PlanoAulaCriteria(this);
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

    public TipoAulaFilter getTipoAula() {
        return tipoAula;
    }

    public TipoAulaFilter tipoAula() {
        if (tipoAula == null) {
            tipoAula = new TipoAulaFilter();
        }
        return tipoAula;
    }

    public void setTipoAula(TipoAulaFilter tipoAula) {
        this.tipoAula = tipoAula;
    }

    public IntegerFilter getSemanaLectiva() {
        return semanaLectiva;
    }

    public IntegerFilter semanaLectiva() {
        if (semanaLectiva == null) {
            semanaLectiva = new IntegerFilter();
        }
        return semanaLectiva;
    }

    public void setSemanaLectiva(IntegerFilter semanaLectiva) {
        this.semanaLectiva = semanaLectiva;
    }

    public StringFilter getAssunto() {
        return assunto;
    }

    public StringFilter assunto() {
        if (assunto == null) {
            assunto = new StringFilter();
        }
        return assunto;
    }

    public void setAssunto(StringFilter assunto) {
        this.assunto = assunto;
    }

    public IntegerFilter getTempoTotalLicao() {
        return tempoTotalLicao;
    }

    public IntegerFilter tempoTotalLicao() {
        if (tempoTotalLicao == null) {
            tempoTotalLicao = new IntegerFilter();
        }
        return tempoTotalLicao;
    }

    public void setTempoTotalLicao(IntegerFilter tempoTotalLicao) {
        this.tempoTotalLicao = tempoTotalLicao;
    }

    public EstadoLicaoFilter getEstado() {
        return estado;
    }

    public EstadoLicaoFilter estado() {
        if (estado == null) {
            estado = new EstadoLicaoFilter();
        }
        return estado;
    }

    public void setEstado(EstadoLicaoFilter estado) {
        this.estado = estado;
    }

    public LongFilter getDetalhesId() {
        return detalhesId;
    }

    public LongFilter detalhesId() {
        if (detalhesId == null) {
            detalhesId = new LongFilter();
        }
        return detalhesId;
    }

    public void setDetalhesId(LongFilter detalhesId) {
        this.detalhesId = detalhesId;
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

    public LongFilter getUnidadeTematicaId() {
        return unidadeTematicaId;
    }

    public LongFilter unidadeTematicaId() {
        if (unidadeTematicaId == null) {
            unidadeTematicaId = new LongFilter();
        }
        return unidadeTematicaId;
    }

    public void setUnidadeTematicaId(LongFilter unidadeTematicaId) {
        this.unidadeTematicaId = unidadeTematicaId;
    }

    public LongFilter getSubUnidadeTematicaId() {
        return subUnidadeTematicaId;
    }

    public LongFilter subUnidadeTematicaId() {
        if (subUnidadeTematicaId == null) {
            subUnidadeTematicaId = new LongFilter();
        }
        return subUnidadeTematicaId;
    }

    public void setSubUnidadeTematicaId(LongFilter subUnidadeTematicaId) {
        this.subUnidadeTematicaId = subUnidadeTematicaId;
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
        final PlanoAulaCriteria that = (PlanoAulaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipoAula, that.tipoAula) &&
            Objects.equals(semanaLectiva, that.semanaLectiva) &&
            Objects.equals(assunto, that.assunto) &&
            Objects.equals(tempoTotalLicao, that.tempoTotalLicao) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(detalhesId, that.detalhesId) &&
            Objects.equals(licaoId, that.licaoId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(unidadeTematicaId, that.unidadeTematicaId) &&
            Objects.equals(subUnidadeTematicaId, that.subUnidadeTematicaId) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(docenteId, that.docenteId) &&
            Objects.equals(disciplinaCurricularId, that.disciplinaCurricularId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tipoAula,
            semanaLectiva,
            assunto,
            tempoTotalLicao,
            estado,
            detalhesId,
            licaoId,
            anoLectivoId,
            utilizadorId,
            unidadeTematicaId,
            subUnidadeTematicaId,
            turmaId,
            docenteId,
            disciplinaCurricularId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoAulaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipoAula != null ? "tipoAula=" + tipoAula + ", " : "") +
            (semanaLectiva != null ? "semanaLectiva=" + semanaLectiva + ", " : "") +
            (assunto != null ? "assunto=" + assunto + ", " : "") +
            (tempoTotalLicao != null ? "tempoTotalLicao=" + tempoTotalLicao + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (detalhesId != null ? "detalhesId=" + detalhesId + ", " : "") +
            (licaoId != null ? "licaoId=" + licaoId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (unidadeTematicaId != null ? "unidadeTematicaId=" + unidadeTematicaId + ", " : "") +
            (subUnidadeTematicaId != null ? "subUnidadeTematicaId=" + subUnidadeTematicaId + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (docenteId != null ? "docenteId=" + docenteId + ", " : "") +
            (disciplinaCurricularId != null ? "disciplinaCurricularId=" + disciplinaCurricularId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
