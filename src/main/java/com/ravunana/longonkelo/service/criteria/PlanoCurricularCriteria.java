package com.ravunana.longonkelo.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.PlanoCurricular} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.PlanoCurricularResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-curriculars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoCurricularCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private StringFilter formulaClassificacaoFinal;

    private IntegerFilter numeroDisciplinaAprova;

    private IntegerFilter numeroDisciplinaReprova;

    private IntegerFilter numeroDisciplinaRecurso;

    private IntegerFilter numeroDisciplinaExame;

    private IntegerFilter numeroDisciplinaExameEspecial;

    private IntegerFilter numeroFaltaReprova;

    private DoubleFilter pesoMedia1;

    private DoubleFilter pesoMedia2;

    private DoubleFilter pesoMedia3;

    private DoubleFilter pesoRecurso;

    private DoubleFilter pesoExame;

    private DoubleFilter pesoExameEspecial;

    private DoubleFilter pesoNotaCoselho;

    private StringFilter siglaProva1;

    private StringFilter siglaProva2;

    private StringFilter siglaProva3;

    private StringFilter siglaMedia1;

    private StringFilter siglaMedia2;

    private StringFilter siglaMedia3;

    private StringFilter formulaMedia;

    private StringFilter formulaDispensa;

    private StringFilter formulaExame;

    private StringFilter formulaRecurso;

    private StringFilter formulaExameEspecial;

    private LongFilter turmaId;

    private LongFilter utilizadorId;

    private LongFilter classeId;

    private LongFilter cursoId;

    private LongFilter disciplinasCurricularId;

    private Boolean distinct;

    public PlanoCurricularCriteria() {}

    public PlanoCurricularCriteria(PlanoCurricularCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.formulaClassificacaoFinal = other.formulaClassificacaoFinal == null ? null : other.formulaClassificacaoFinal.copy();
        this.numeroDisciplinaAprova = other.numeroDisciplinaAprova == null ? null : other.numeroDisciplinaAprova.copy();
        this.numeroDisciplinaReprova = other.numeroDisciplinaReprova == null ? null : other.numeroDisciplinaReprova.copy();
        this.numeroDisciplinaRecurso = other.numeroDisciplinaRecurso == null ? null : other.numeroDisciplinaRecurso.copy();
        this.numeroDisciplinaExame = other.numeroDisciplinaExame == null ? null : other.numeroDisciplinaExame.copy();
        this.numeroDisciplinaExameEspecial =
            other.numeroDisciplinaExameEspecial == null ? null : other.numeroDisciplinaExameEspecial.copy();
        this.numeroFaltaReprova = other.numeroFaltaReprova == null ? null : other.numeroFaltaReprova.copy();
        this.pesoMedia1 = other.pesoMedia1 == null ? null : other.pesoMedia1.copy();
        this.pesoMedia2 = other.pesoMedia2 == null ? null : other.pesoMedia2.copy();
        this.pesoMedia3 = other.pesoMedia3 == null ? null : other.pesoMedia3.copy();
        this.pesoRecurso = other.pesoRecurso == null ? null : other.pesoRecurso.copy();
        this.pesoExame = other.pesoExame == null ? null : other.pesoExame.copy();
        this.pesoExameEspecial = other.pesoExameEspecial == null ? null : other.pesoExameEspecial.copy();
        this.pesoNotaCoselho = other.pesoNotaCoselho == null ? null : other.pesoNotaCoselho.copy();
        this.siglaProva1 = other.siglaProva1 == null ? null : other.siglaProva1.copy();
        this.siglaProva2 = other.siglaProva2 == null ? null : other.siglaProva2.copy();
        this.siglaProva3 = other.siglaProva3 == null ? null : other.siglaProva3.copy();
        this.siglaMedia1 = other.siglaMedia1 == null ? null : other.siglaMedia1.copy();
        this.siglaMedia2 = other.siglaMedia2 == null ? null : other.siglaMedia2.copy();
        this.siglaMedia3 = other.siglaMedia3 == null ? null : other.siglaMedia3.copy();
        this.formulaMedia = other.formulaMedia == null ? null : other.formulaMedia.copy();
        this.formulaDispensa = other.formulaDispensa == null ? null : other.formulaDispensa.copy();
        this.formulaExame = other.formulaExame == null ? null : other.formulaExame.copy();
        this.formulaRecurso = other.formulaRecurso == null ? null : other.formulaRecurso.copy();
        this.formulaExameEspecial = other.formulaExameEspecial == null ? null : other.formulaExameEspecial.copy();
        this.turmaId = other.turmaId == null ? null : other.turmaId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.classeId = other.classeId == null ? null : other.classeId.copy();
        this.cursoId = other.cursoId == null ? null : other.cursoId.copy();
        this.disciplinasCurricularId = other.disciplinasCurricularId == null ? null : other.disciplinasCurricularId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PlanoCurricularCriteria copy() {
        return new PlanoCurricularCriteria(this);
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

    public StringFilter getFormulaClassificacaoFinal() {
        return formulaClassificacaoFinal;
    }

    public StringFilter formulaClassificacaoFinal() {
        if (formulaClassificacaoFinal == null) {
            formulaClassificacaoFinal = new StringFilter();
        }
        return formulaClassificacaoFinal;
    }

    public void setFormulaClassificacaoFinal(StringFilter formulaClassificacaoFinal) {
        this.formulaClassificacaoFinal = formulaClassificacaoFinal;
    }

    public IntegerFilter getNumeroDisciplinaAprova() {
        return numeroDisciplinaAprova;
    }

    public IntegerFilter numeroDisciplinaAprova() {
        if (numeroDisciplinaAprova == null) {
            numeroDisciplinaAprova = new IntegerFilter();
        }
        return numeroDisciplinaAprova;
    }

    public void setNumeroDisciplinaAprova(IntegerFilter numeroDisciplinaAprova) {
        this.numeroDisciplinaAprova = numeroDisciplinaAprova;
    }

    public IntegerFilter getNumeroDisciplinaReprova() {
        return numeroDisciplinaReprova;
    }

    public IntegerFilter numeroDisciplinaReprova() {
        if (numeroDisciplinaReprova == null) {
            numeroDisciplinaReprova = new IntegerFilter();
        }
        return numeroDisciplinaReprova;
    }

    public void setNumeroDisciplinaReprova(IntegerFilter numeroDisciplinaReprova) {
        this.numeroDisciplinaReprova = numeroDisciplinaReprova;
    }

    public IntegerFilter getNumeroDisciplinaRecurso() {
        return numeroDisciplinaRecurso;
    }

    public IntegerFilter numeroDisciplinaRecurso() {
        if (numeroDisciplinaRecurso == null) {
            numeroDisciplinaRecurso = new IntegerFilter();
        }
        return numeroDisciplinaRecurso;
    }

    public void setNumeroDisciplinaRecurso(IntegerFilter numeroDisciplinaRecurso) {
        this.numeroDisciplinaRecurso = numeroDisciplinaRecurso;
    }

    public IntegerFilter getNumeroDisciplinaExame() {
        return numeroDisciplinaExame;
    }

    public IntegerFilter numeroDisciplinaExame() {
        if (numeroDisciplinaExame == null) {
            numeroDisciplinaExame = new IntegerFilter();
        }
        return numeroDisciplinaExame;
    }

    public void setNumeroDisciplinaExame(IntegerFilter numeroDisciplinaExame) {
        this.numeroDisciplinaExame = numeroDisciplinaExame;
    }

    public IntegerFilter getNumeroDisciplinaExameEspecial() {
        return numeroDisciplinaExameEspecial;
    }

    public IntegerFilter numeroDisciplinaExameEspecial() {
        if (numeroDisciplinaExameEspecial == null) {
            numeroDisciplinaExameEspecial = new IntegerFilter();
        }
        return numeroDisciplinaExameEspecial;
    }

    public void setNumeroDisciplinaExameEspecial(IntegerFilter numeroDisciplinaExameEspecial) {
        this.numeroDisciplinaExameEspecial = numeroDisciplinaExameEspecial;
    }

    public IntegerFilter getNumeroFaltaReprova() {
        return numeroFaltaReprova;
    }

    public IntegerFilter numeroFaltaReprova() {
        if (numeroFaltaReprova == null) {
            numeroFaltaReprova = new IntegerFilter();
        }
        return numeroFaltaReprova;
    }

    public void setNumeroFaltaReprova(IntegerFilter numeroFaltaReprova) {
        this.numeroFaltaReprova = numeroFaltaReprova;
    }

    public DoubleFilter getPesoMedia1() {
        return pesoMedia1;
    }

    public DoubleFilter pesoMedia1() {
        if (pesoMedia1 == null) {
            pesoMedia1 = new DoubleFilter();
        }
        return pesoMedia1;
    }

    public void setPesoMedia1(DoubleFilter pesoMedia1) {
        this.pesoMedia1 = pesoMedia1;
    }

    public DoubleFilter getPesoMedia2() {
        return pesoMedia2;
    }

    public DoubleFilter pesoMedia2() {
        if (pesoMedia2 == null) {
            pesoMedia2 = new DoubleFilter();
        }
        return pesoMedia2;
    }

    public void setPesoMedia2(DoubleFilter pesoMedia2) {
        this.pesoMedia2 = pesoMedia2;
    }

    public DoubleFilter getPesoMedia3() {
        return pesoMedia3;
    }

    public DoubleFilter pesoMedia3() {
        if (pesoMedia3 == null) {
            pesoMedia3 = new DoubleFilter();
        }
        return pesoMedia3;
    }

    public void setPesoMedia3(DoubleFilter pesoMedia3) {
        this.pesoMedia3 = pesoMedia3;
    }

    public DoubleFilter getPesoRecurso() {
        return pesoRecurso;
    }

    public DoubleFilter pesoRecurso() {
        if (pesoRecurso == null) {
            pesoRecurso = new DoubleFilter();
        }
        return pesoRecurso;
    }

    public void setPesoRecurso(DoubleFilter pesoRecurso) {
        this.pesoRecurso = pesoRecurso;
    }

    public DoubleFilter getPesoExame() {
        return pesoExame;
    }

    public DoubleFilter pesoExame() {
        if (pesoExame == null) {
            pesoExame = new DoubleFilter();
        }
        return pesoExame;
    }

    public void setPesoExame(DoubleFilter pesoExame) {
        this.pesoExame = pesoExame;
    }

    public DoubleFilter getPesoExameEspecial() {
        return pesoExameEspecial;
    }

    public DoubleFilter pesoExameEspecial() {
        if (pesoExameEspecial == null) {
            pesoExameEspecial = new DoubleFilter();
        }
        return pesoExameEspecial;
    }

    public void setPesoExameEspecial(DoubleFilter pesoExameEspecial) {
        this.pesoExameEspecial = pesoExameEspecial;
    }

    public DoubleFilter getPesoNotaCoselho() {
        return pesoNotaCoselho;
    }

    public DoubleFilter pesoNotaCoselho() {
        if (pesoNotaCoselho == null) {
            pesoNotaCoselho = new DoubleFilter();
        }
        return pesoNotaCoselho;
    }

    public void setPesoNotaCoselho(DoubleFilter pesoNotaCoselho) {
        this.pesoNotaCoselho = pesoNotaCoselho;
    }

    public StringFilter getSiglaProva1() {
        return siglaProva1;
    }

    public StringFilter siglaProva1() {
        if (siglaProva1 == null) {
            siglaProva1 = new StringFilter();
        }
        return siglaProva1;
    }

    public void setSiglaProva1(StringFilter siglaProva1) {
        this.siglaProva1 = siglaProva1;
    }

    public StringFilter getSiglaProva2() {
        return siglaProva2;
    }

    public StringFilter siglaProva2() {
        if (siglaProva2 == null) {
            siglaProva2 = new StringFilter();
        }
        return siglaProva2;
    }

    public void setSiglaProva2(StringFilter siglaProva2) {
        this.siglaProva2 = siglaProva2;
    }

    public StringFilter getSiglaProva3() {
        return siglaProva3;
    }

    public StringFilter siglaProva3() {
        if (siglaProva3 == null) {
            siglaProva3 = new StringFilter();
        }
        return siglaProva3;
    }

    public void setSiglaProva3(StringFilter siglaProva3) {
        this.siglaProva3 = siglaProva3;
    }

    public StringFilter getSiglaMedia1() {
        return siglaMedia1;
    }

    public StringFilter siglaMedia1() {
        if (siglaMedia1 == null) {
            siglaMedia1 = new StringFilter();
        }
        return siglaMedia1;
    }

    public void setSiglaMedia1(StringFilter siglaMedia1) {
        this.siglaMedia1 = siglaMedia1;
    }

    public StringFilter getSiglaMedia2() {
        return siglaMedia2;
    }

    public StringFilter siglaMedia2() {
        if (siglaMedia2 == null) {
            siglaMedia2 = new StringFilter();
        }
        return siglaMedia2;
    }

    public void setSiglaMedia2(StringFilter siglaMedia2) {
        this.siglaMedia2 = siglaMedia2;
    }

    public StringFilter getSiglaMedia3() {
        return siglaMedia3;
    }

    public StringFilter siglaMedia3() {
        if (siglaMedia3 == null) {
            siglaMedia3 = new StringFilter();
        }
        return siglaMedia3;
    }

    public void setSiglaMedia3(StringFilter siglaMedia3) {
        this.siglaMedia3 = siglaMedia3;
    }

    public StringFilter getFormulaMedia() {
        return formulaMedia;
    }

    public StringFilter formulaMedia() {
        if (formulaMedia == null) {
            formulaMedia = new StringFilter();
        }
        return formulaMedia;
    }

    public void setFormulaMedia(StringFilter formulaMedia) {
        this.formulaMedia = formulaMedia;
    }

    public StringFilter getFormulaDispensa() {
        return formulaDispensa;
    }

    public StringFilter formulaDispensa() {
        if (formulaDispensa == null) {
            formulaDispensa = new StringFilter();
        }
        return formulaDispensa;
    }

    public void setFormulaDispensa(StringFilter formulaDispensa) {
        this.formulaDispensa = formulaDispensa;
    }

    public StringFilter getFormulaExame() {
        return formulaExame;
    }

    public StringFilter formulaExame() {
        if (formulaExame == null) {
            formulaExame = new StringFilter();
        }
        return formulaExame;
    }

    public void setFormulaExame(StringFilter formulaExame) {
        this.formulaExame = formulaExame;
    }

    public StringFilter getFormulaRecurso() {
        return formulaRecurso;
    }

    public StringFilter formulaRecurso() {
        if (formulaRecurso == null) {
            formulaRecurso = new StringFilter();
        }
        return formulaRecurso;
    }

    public void setFormulaRecurso(StringFilter formulaRecurso) {
        this.formulaRecurso = formulaRecurso;
    }

    public StringFilter getFormulaExameEspecial() {
        return formulaExameEspecial;
    }

    public StringFilter formulaExameEspecial() {
        if (formulaExameEspecial == null) {
            formulaExameEspecial = new StringFilter();
        }
        return formulaExameEspecial;
    }

    public void setFormulaExameEspecial(StringFilter formulaExameEspecial) {
        this.formulaExameEspecial = formulaExameEspecial;
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

    public LongFilter getClasseId() {
        return classeId;
    }

    public LongFilter classeId() {
        if (classeId == null) {
            classeId = new LongFilter();
        }
        return classeId;
    }

    public void setClasseId(LongFilter classeId) {
        this.classeId = classeId;
    }

    public LongFilter getCursoId() {
        return cursoId;
    }

    public LongFilter cursoId() {
        if (cursoId == null) {
            cursoId = new LongFilter();
        }
        return cursoId;
    }

    public void setCursoId(LongFilter cursoId) {
        this.cursoId = cursoId;
    }

    public LongFilter getDisciplinasCurricularId() {
        return disciplinasCurricularId;
    }

    public LongFilter disciplinasCurricularId() {
        if (disciplinasCurricularId == null) {
            disciplinasCurricularId = new LongFilter();
        }
        return disciplinasCurricularId;
    }

    public void setDisciplinasCurricularId(LongFilter disciplinasCurricularId) {
        this.disciplinasCurricularId = disciplinasCurricularId;
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
        final PlanoCurricularCriteria that = (PlanoCurricularCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(formulaClassificacaoFinal, that.formulaClassificacaoFinal) &&
            Objects.equals(numeroDisciplinaAprova, that.numeroDisciplinaAprova) &&
            Objects.equals(numeroDisciplinaReprova, that.numeroDisciplinaReprova) &&
            Objects.equals(numeroDisciplinaRecurso, that.numeroDisciplinaRecurso) &&
            Objects.equals(numeroDisciplinaExame, that.numeroDisciplinaExame) &&
            Objects.equals(numeroDisciplinaExameEspecial, that.numeroDisciplinaExameEspecial) &&
            Objects.equals(numeroFaltaReprova, that.numeroFaltaReprova) &&
            Objects.equals(pesoMedia1, that.pesoMedia1) &&
            Objects.equals(pesoMedia2, that.pesoMedia2) &&
            Objects.equals(pesoMedia3, that.pesoMedia3) &&
            Objects.equals(pesoRecurso, that.pesoRecurso) &&
            Objects.equals(pesoExame, that.pesoExame) &&
            Objects.equals(pesoExameEspecial, that.pesoExameEspecial) &&
            Objects.equals(pesoNotaCoselho, that.pesoNotaCoselho) &&
            Objects.equals(siglaProva1, that.siglaProva1) &&
            Objects.equals(siglaProva2, that.siglaProva2) &&
            Objects.equals(siglaProva3, that.siglaProva3) &&
            Objects.equals(siglaMedia1, that.siglaMedia1) &&
            Objects.equals(siglaMedia2, that.siglaMedia2) &&
            Objects.equals(siglaMedia3, that.siglaMedia3) &&
            Objects.equals(formulaMedia, that.formulaMedia) &&
            Objects.equals(formulaDispensa, that.formulaDispensa) &&
            Objects.equals(formulaExame, that.formulaExame) &&
            Objects.equals(formulaRecurso, that.formulaRecurso) &&
            Objects.equals(formulaExameEspecial, that.formulaExameEspecial) &&
            Objects.equals(turmaId, that.turmaId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(classeId, that.classeId) &&
            Objects.equals(cursoId, that.cursoId) &&
            Objects.equals(disciplinasCurricularId, that.disciplinasCurricularId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            descricao,
            formulaClassificacaoFinal,
            numeroDisciplinaAprova,
            numeroDisciplinaReprova,
            numeroDisciplinaRecurso,
            numeroDisciplinaExame,
            numeroDisciplinaExameEspecial,
            numeroFaltaReprova,
            pesoMedia1,
            pesoMedia2,
            pesoMedia3,
            pesoRecurso,
            pesoExame,
            pesoExameEspecial,
            pesoNotaCoselho,
            siglaProva1,
            siglaProva2,
            siglaProva3,
            siglaMedia1,
            siglaMedia2,
            siglaMedia3,
            formulaMedia,
            formulaDispensa,
            formulaExame,
            formulaRecurso,
            formulaExameEspecial,
            turmaId,
            utilizadorId,
            classeId,
            cursoId,
            disciplinasCurricularId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoCurricularCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (formulaClassificacaoFinal != null ? "formulaClassificacaoFinal=" + formulaClassificacaoFinal + ", " : "") +
            (numeroDisciplinaAprova != null ? "numeroDisciplinaAprova=" + numeroDisciplinaAprova + ", " : "") +
            (numeroDisciplinaReprova != null ? "numeroDisciplinaReprova=" + numeroDisciplinaReprova + ", " : "") +
            (numeroDisciplinaRecurso != null ? "numeroDisciplinaRecurso=" + numeroDisciplinaRecurso + ", " : "") +
            (numeroDisciplinaExame != null ? "numeroDisciplinaExame=" + numeroDisciplinaExame + ", " : "") +
            (numeroDisciplinaExameEspecial != null ? "numeroDisciplinaExameEspecial=" + numeroDisciplinaExameEspecial + ", " : "") +
            (numeroFaltaReprova != null ? "numeroFaltaReprova=" + numeroFaltaReprova + ", " : "") +
            (pesoMedia1 != null ? "pesoMedia1=" + pesoMedia1 + ", " : "") +
            (pesoMedia2 != null ? "pesoMedia2=" + pesoMedia2 + ", " : "") +
            (pesoMedia3 != null ? "pesoMedia3=" + pesoMedia3 + ", " : "") +
            (pesoRecurso != null ? "pesoRecurso=" + pesoRecurso + ", " : "") +
            (pesoExame != null ? "pesoExame=" + pesoExame + ", " : "") +
            (pesoExameEspecial != null ? "pesoExameEspecial=" + pesoExameEspecial + ", " : "") +
            (pesoNotaCoselho != null ? "pesoNotaCoselho=" + pesoNotaCoselho + ", " : "") +
            (siglaProva1 != null ? "siglaProva1=" + siglaProva1 + ", " : "") +
            (siglaProva2 != null ? "siglaProva2=" + siglaProva2 + ", " : "") +
            (siglaProva3 != null ? "siglaProva3=" + siglaProva3 + ", " : "") +
            (siglaMedia1 != null ? "siglaMedia1=" + siglaMedia1 + ", " : "") +
            (siglaMedia2 != null ? "siglaMedia2=" + siglaMedia2 + ", " : "") +
            (siglaMedia3 != null ? "siglaMedia3=" + siglaMedia3 + ", " : "") +
            (formulaMedia != null ? "formulaMedia=" + formulaMedia + ", " : "") +
            (formulaDispensa != null ? "formulaDispensa=" + formulaDispensa + ", " : "") +
            (formulaExame != null ? "formulaExame=" + formulaExame + ", " : "") +
            (formulaRecurso != null ? "formulaRecurso=" + formulaRecurso + ", " : "") +
            (formulaExameEspecial != null ? "formulaExameEspecial=" + formulaExameEspecial + ", " : "") +
            (turmaId != null ? "turmaId=" + turmaId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (classeId != null ? "classeId=" + classeId + ", " : "") +
            (cursoId != null ? "cursoId=" + cursoId + ", " : "") +
            (disciplinasCurricularId != null ? "disciplinasCurricularId=" + disciplinasCurricularId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
