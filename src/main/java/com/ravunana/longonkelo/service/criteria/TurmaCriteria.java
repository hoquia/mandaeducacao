package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.CriterioDescricaoTurma;
import com.ravunana.longonkelo.domain.enumeration.CriterioNumeroChamada;
import com.ravunana.longonkelo.domain.enumeration.TipoTurma;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.Turma} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.TurmaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /turmas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TurmaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoTurma
     */
    public static class TipoTurmaFilter extends Filter<TipoTurma> {

        public TipoTurmaFilter() {}

        public TipoTurmaFilter(TipoTurmaFilter filter) {
            super(filter);
        }

        @Override
        public TipoTurmaFilter copy() {
            return new TipoTurmaFilter(this);
        }
    }

    /**
     * Class for filtering CriterioDescricaoTurma
     */
    public static class CriterioDescricaoTurmaFilter extends Filter<CriterioDescricaoTurma> {

        public CriterioDescricaoTurmaFilter() {}

        public CriterioDescricaoTurmaFilter(CriterioDescricaoTurmaFilter filter) {
            super(filter);
        }

        @Override
        public CriterioDescricaoTurmaFilter copy() {
            return new CriterioDescricaoTurmaFilter(this);
        }
    }

    /**
     * Class for filtering CriterioNumeroChamada
     */
    public static class CriterioNumeroChamadaFilter extends Filter<CriterioNumeroChamada> {

        public CriterioNumeroChamadaFilter() {}

        public CriterioNumeroChamadaFilter(CriterioNumeroChamadaFilter filter) {
            super(filter);
        }

        @Override
        public CriterioNumeroChamadaFilter copy() {
            return new CriterioNumeroChamadaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chaveComposta;

    private TipoTurmaFilter tipoTurma;

    private IntegerFilter sala;

    private StringFilter descricao;

    private IntegerFilter lotacao;

    private IntegerFilter confirmado;

    private LocalDateFilter abertura;

    private LocalDateFilter encerramento;

    private CriterioDescricaoTurmaFilter criterioDescricao;

    private CriterioNumeroChamadaFilter criterioOrdenacaoNumero;

    private BooleanFilter fazInscricaoDepoisMatricula;

    private BooleanFilter isDisponivel;

    private LongFilter turmaId;

    private LongFilter horariosId;

    private LongFilter notasPeriodicaDisciplinaId;

    private LongFilter processoSelectivoMatriculaId;

    private LongFilter planoAulaId;

    private LongFilter matriculasId;

    private LongFilter resumoAcademicoId;

    private LongFilter responsaveisId;

    private LongFilter dissertacaoFinalCursoId;

    private LongFilter anoLectivoId;

    private LongFilter utilizadorId;

    private LongFilter referenciaId;

    private LongFilter planoCurricularId;

    private LongFilter turnoId;

    private Boolean distinct;

    public TurmaCriteria() {}

    public TurmaCriteria(TurmaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chaveComposta = other.chaveComposta == null ? null : other.chaveComposta.copy();
        this.tipoTurma = other.tipoTurma == null ? null : other.tipoTurma.copy();
        this.sala = other.sala == null ? null : other.sala.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.lotacao = other.lotacao == null ? null : other.lotacao.copy();
        this.confirmado = other.confirmado == null ? null : other.confirmado.copy();
        this.abertura = other.abertura == null ? null : other.abertura.copy();
        this.encerramento = other.encerramento == null ? null : other.encerramento.copy();
        this.criterioDescricao = other.criterioDescricao == null ? null : other.criterioDescricao.copy();
        this.criterioOrdenacaoNumero = other.criterioOrdenacaoNumero == null ? null : other.criterioOrdenacaoNumero.copy();
        this.fazInscricaoDepoisMatricula = other.fazInscricaoDepoisMatricula == null ? null : other.fazInscricaoDepoisMatricula.copy();
        this.isDisponivel = other.isDisponivel == null ? null : other.isDisponivel.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.horariosId = other.horariosId == null ? null : other.horariosId.copy();
        this.notasPeriodicaDisciplinaId = other.notasPeriodicaDisciplinaId == null ? null : other.notasPeriodicaDisciplinaId.copy();
        this.processoSelectivoMatriculaId = other.processoSelectivoMatriculaId == null ? null : other.processoSelectivoMatriculaId.copy();
        this.planoAulaId = other.planoAulaId == null ? null : other.planoAulaId.copy();
        this.matriculasId = other.matriculasId == null ? null : other.matriculasId.copy();
        this.resumoAcademicoId = other.resumoAcademicoId == null ? null : other.resumoAcademicoId.copy();
        this.responsaveisId = other.responsaveisId == null ? null : other.responsaveisId.copy();
        this.dissertacaoFinalCursoId = other.dissertacaoFinalCursoId == null ? null : other.dissertacaoFinalCursoId.copy();
        this.anoLectivoId = other.anoLectivoId == null ? null : other.anoLectivoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.planoCurricularId = other.planoCurricularId == null ? null : other.planoCurricularId.copy();
        this.turnoId = other.turnoId == null ? null : other.turnoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TurmaCriteria copy() {
        return new TurmaCriteria(this);
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

    public TipoTurmaFilter getTipoTurma() {
        return tipoTurma;
    }

    public TipoTurmaFilter tipoTurma() {
        if (tipoTurma == null) {
            tipoTurma = new TipoTurmaFilter();
        }
        return tipoTurma;
    }

    public void setTipoTurma(TipoTurmaFilter tipoTurma) {
        this.tipoTurma = tipoTurma;
    }

    public IntegerFilter getSala() {
        return sala;
    }

    public IntegerFilter sala() {
        if (sala == null) {
            sala = new IntegerFilter();
        }
        return sala;
    }

    public void setSala(IntegerFilter sala) {
        this.sala = sala;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public IntegerFilter getLotacao() {
        return lotacao;
    }

    public IntegerFilter lotacao() {
        if (lotacao == null) {
            lotacao = new IntegerFilter();
        }
        return lotacao;
    }

    public void setLotacao(IntegerFilter lotacao) {
        this.lotacao = lotacao;
    }

    public IntegerFilter getConfirmado() {
        return confirmado;
    }

    public IntegerFilter confirmado() {
        if (confirmado == null) {
            confirmado = new IntegerFilter();
        }
        return confirmado;
    }

    public void setConfirmado(IntegerFilter confirmado) {
        this.confirmado = confirmado;
    }

    public LocalDateFilter getAbertura() {
        return abertura;
    }

    public LocalDateFilter abertura() {
        if (abertura == null) {
            abertura = new LocalDateFilter();
        }
        return abertura;
    }

    public void setAbertura(LocalDateFilter abertura) {
        this.abertura = abertura;
    }

    public LocalDateFilter getEncerramento() {
        return encerramento;
    }

    public LocalDateFilter encerramento() {
        if (encerramento == null) {
            encerramento = new LocalDateFilter();
        }
        return encerramento;
    }

    public void setEncerramento(LocalDateFilter encerramento) {
        this.encerramento = encerramento;
    }

    public CriterioDescricaoTurmaFilter getCriterioDescricao() {
        return criterioDescricao;
    }

    public CriterioDescricaoTurmaFilter criterioDescricao() {
        if (criterioDescricao == null) {
            criterioDescricao = new CriterioDescricaoTurmaFilter();
        }
        return criterioDescricao;
    }

    public void setCriterioDescricao(CriterioDescricaoTurmaFilter criterioDescricao) {
        this.criterioDescricao = criterioDescricao;
    }

    public CriterioNumeroChamadaFilter getCriterioOrdenacaoNumero() {
        return criterioOrdenacaoNumero;
    }

    public CriterioNumeroChamadaFilter criterioOrdenacaoNumero() {
        if (criterioOrdenacaoNumero == null) {
            criterioOrdenacaoNumero = new CriterioNumeroChamadaFilter();
        }
        return criterioOrdenacaoNumero;
    }

    public void setCriterioOrdenacaoNumero(CriterioNumeroChamadaFilter criterioOrdenacaoNumero) {
        this.criterioOrdenacaoNumero = criterioOrdenacaoNumero;
    }

    public BooleanFilter getFazInscricaoDepoisMatricula() {
        return fazInscricaoDepoisMatricula;
    }

    public BooleanFilter fazInscricaoDepoisMatricula() {
        if (fazInscricaoDepoisMatricula == null) {
            fazInscricaoDepoisMatricula = new BooleanFilter();
        }
        return fazInscricaoDepoisMatricula;
    }

    public void setFazInscricaoDepoisMatricula(BooleanFilter fazInscricaoDepoisMatricula) {
        this.fazInscricaoDepoisMatricula = fazInscricaoDepoisMatricula;
    }

    public BooleanFilter getIsDisponivel() {
        return isDisponivel;
    }

    public BooleanFilter isDisponivel() {
        if (isDisponivel == null) {
            isDisponivel = new BooleanFilter();
        }
        return isDisponivel;
    }

    public void setIsDisponivel(BooleanFilter isDisponivel) {
        this.isDisponivel = isDisponivel;
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

    public LongFilter getHorariosId() {
        return horariosId;
    }

    public LongFilter horariosId() {
        if (horariosId == null) {
            horariosId = new LongFilter();
        }
        return horariosId;
    }

    public void setHorariosId(LongFilter horariosId) {
        this.horariosId = horariosId;
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

    public LongFilter getProcessoSelectivoMatriculaId() {
        return processoSelectivoMatriculaId;
    }

    public LongFilter processoSelectivoMatriculaId() {
        if (processoSelectivoMatriculaId == null) {
            processoSelectivoMatriculaId = new LongFilter();
        }
        return processoSelectivoMatriculaId;
    }

    public void setProcessoSelectivoMatriculaId(LongFilter processoSelectivoMatriculaId) {
        this.processoSelectivoMatriculaId = processoSelectivoMatriculaId;
    }

    public LongFilter getPlanoAulaId() {
        return planoAulaId;
    }

    public LongFilter planoAulaId() {
        if (planoAulaId == null) {
            planoAulaId = new LongFilter();
        }
        return planoAulaId;
    }

    public void setPlanoAulaId(LongFilter planoAulaId) {
        this.planoAulaId = planoAulaId;
    }

    public LongFilter getMatriculasId() {
        return matriculasId;
    }

    public LongFilter matriculasId() {
        if (matriculasId == null) {
            matriculasId = new LongFilter();
        }
        return matriculasId;
    }

    public void setMatriculasId(LongFilter matriculasId) {
        this.matriculasId = matriculasId;
    }

    public LongFilter getResumoAcademicoId() {
        return resumoAcademicoId;
    }

    public LongFilter resumoAcademicoId() {
        if (resumoAcademicoId == null) {
            resumoAcademicoId = new LongFilter();
        }
        return resumoAcademicoId;
    }

    public void setResumoAcademicoId(LongFilter resumoAcademicoId) {
        this.resumoAcademicoId = resumoAcademicoId;
    }

    public LongFilter getResponsaveisId() {
        return responsaveisId;
    }

    public LongFilter responsaveisId() {
        if (responsaveisId == null) {
            responsaveisId = new LongFilter();
        }
        return responsaveisId;
    }

    public void setResponsaveisId(LongFilter responsaveisId) {
        this.responsaveisId = responsaveisId;
    }

    public LongFilter getDissertacaoFinalCursoId() {
        return dissertacaoFinalCursoId;
    }

    public LongFilter dissertacaoFinalCursoId() {
        if (dissertacaoFinalCursoId == null) {
            dissertacaoFinalCursoId = new LongFilter();
        }
        return dissertacaoFinalCursoId;
    }

    public void setDissertacaoFinalCursoId(LongFilter dissertacaoFinalCursoId) {
        this.dissertacaoFinalCursoId = dissertacaoFinalCursoId;
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

    public LongFilter getPlanoCurricularId() {
        return planoCurricularId;
    }

    public LongFilter planoCurricularId() {
        if (planoCurricularId == null) {
            planoCurricularId = new LongFilter();
        }
        return planoCurricularId;
    }

    public void setPlanoCurricularId(LongFilter planoCurricularId) {
        this.planoCurricularId = planoCurricularId;
    }

    public LongFilter getTurnoId() {
        return turnoId;
    }

    public LongFilter turnoId() {
        if (turnoId == null) {
            turnoId = new LongFilter();
        }
        return turnoId;
    }

    public void setTurnoId(LongFilter turnoId) {
        this.turnoId = turnoId;
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
        final TurmaCriteria that = (TurmaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(chaveComposta, that.chaveComposta) &&
            Objects.equals(tipoTurma, that.tipoTurma) &&
            Objects.equals(sala, that.sala) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(lotacao, that.lotacao) &&
            Objects.equals(confirmado, that.confirmado) &&
            Objects.equals(abertura, that.abertura) &&
            Objects.equals(encerramento, that.encerramento) &&
            Objects.equals(criterioDescricao, that.criterioDescricao) &&
            Objects.equals(criterioOrdenacaoNumero, that.criterioOrdenacaoNumero) &&
            Objects.equals(fazInscricaoDepoisMatricula, that.fazInscricaoDepoisMatricula) &&
            Objects.equals(isDisponivel, that.isDisponivel) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(horariosId, that.horariosId) &&
            Objects.equals(notasPeriodicaDisciplinaId, that.notasPeriodicaDisciplinaId) &&
            Objects.equals(processoSelectivoMatriculaId, that.processoSelectivoMatriculaId) &&
            Objects.equals(planoAulaId, that.planoAulaId) &&
            Objects.equals(matriculasId, that.matriculasId) &&
            Objects.equals(resumoAcademicoId, that.resumoAcademicoId) &&
            Objects.equals(responsaveisId, that.responsaveisId) &&
            Objects.equals(dissertacaoFinalCursoId, that.dissertacaoFinalCursoId) &&
            Objects.equals(anoLectivoId, that.anoLectivoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(planoCurricularId, that.planoCurricularId) &&
            Objects.equals(turnoId, that.turnoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            chaveComposta,
            tipoTurma,
            sala,
            descricao,
            lotacao,
            confirmado,
            abertura,
            encerramento,
            criterioDescricao,
            criterioOrdenacaoNumero,
            fazInscricaoDepoisMatricula,
            isDisponivel,
            turmaId,
            horariosId,
            notasPeriodicaDisciplinaId,
            processoSelectivoMatriculaId,
            planoAulaId,
            matriculasId,
            resumoAcademicoId,
            responsaveisId,
            dissertacaoFinalCursoId,
            anoLectivoId,
            utilizadorId,
            referenciaId,
            planoCurricularId,
            turnoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TurmaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (chaveComposta != null ? "chaveComposta=" + chaveComposta + ", " : "") +
            (tipoTurma != null ? "tipoTurma=" + tipoTurma + ", " : "") +
            (sala != null ? "sala=" + sala + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (lotacao != null ? "lotacao=" + lotacao + ", " : "") +
            (confirmado != null ? "confirmado=" + confirmado + ", " : "") +
            (abertura != null ? "abertura=" + abertura + ", " : "") +
            (encerramento != null ? "encerramento=" + encerramento + ", " : "") +
            (criterioDescricao != null ? "criterioDescricao=" + criterioDescricao + ", " : "") +
            (criterioOrdenacaoNumero != null ? "criterioOrdenacaoNumero=" + criterioOrdenacaoNumero + ", " : "") +
            (fazInscricaoDepoisMatricula != null ? "fazInscricaoDepoisMatricula=" + fazInscricaoDepoisMatricula + ", " : "") +
            (isDisponivel != null ? "isDisponivel=" + isDisponivel + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (horariosId != null ? "horariosId=" + horariosId + ", " : "") +
            (notasPeriodicaDisciplinaId != null ? "notasPeriodicaDisciplinaId=" + notasPeriodicaDisciplinaId + ", " : "") +
            (processoSelectivoMatriculaId != null ? "processoSelectivoMatriculaId=" + processoSelectivoMatriculaId + ", " : "") +
            (planoAulaId != null ? "planoAulaId=" + planoAulaId + ", " : "") +
            (matriculasId != null ? "matriculasId=" + matriculasId + ", " : "") +
            (resumoAcademicoId != null ? "resumoAcademicoId=" + resumoAcademicoId + ", " : "") +
            (responsaveisId != null ? "responsaveisId=" + responsaveisId + ", " : "") +
            (dissertacaoFinalCursoId != null ? "dissertacaoFinalCursoId=" + dissertacaoFinalCursoId + ", " : "") +
            (anoLectivoId != null ? "anoLectivoId=" + anoLectivoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (planoCurricularId != null ? "planoCurricularId=" + planoCurricularId + ", " : "") +
            (turnoId != null ? "turnoId=" + turnoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
