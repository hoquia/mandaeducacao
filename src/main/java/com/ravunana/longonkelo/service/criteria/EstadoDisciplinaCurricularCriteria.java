package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.CategoriaClassificacao;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.EstadoDisciplinaCurricularResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estado-disciplina-curriculars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoDisciplinaCurricularCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CategoriaClassificacao
     */
    public static class CategoriaClassificacaoFilter extends Filter<CategoriaClassificacao> {

        public CategoriaClassificacaoFilter() {}

        public CategoriaClassificacaoFilter(CategoriaClassificacaoFilter filter) {
            super(filter);
        }

        @Override
        public CategoriaClassificacaoFilter copy() {
            return new CategoriaClassificacaoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uniqueSituacaoDisciplina;

    private CategoriaClassificacaoFilter classificacao;

    private StringFilter codigo;

    private StringFilter descricao;

    private StringFilter cor;

    private DoubleFilter valor;

    private LongFilter estadoDisciplinaCurricularId;

    private LongFilter notasPeriodicaDisciplinaId;

    private LongFilter notasGeralDisciplinaId;

    private LongFilter resumoAcademicoId;

    private LongFilter disciplinasCurricularsId;

    private LongFilter referenciaId;

    private Boolean distinct;

    public EstadoDisciplinaCurricularCriteria() {}

    public EstadoDisciplinaCurricularCriteria(EstadoDisciplinaCurricularCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uniqueSituacaoDisciplina = other.uniqueSituacaoDisciplina == null ? null : other.uniqueSituacaoDisciplina.copy();
        this.classificacao = other.classificacao == null ? null : other.classificacao.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.cor = other.cor == null ? null : other.cor.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.estadoDisciplinaCurricularId = other.estadoDisciplinaCurricularId == null ? null : other.estadoDisciplinaCurricularId.copy();
        this.notasPeriodicaDisciplinaId = other.notasPeriodicaDisciplinaId == null ? null : other.notasPeriodicaDisciplinaId.copy();
        this.notasGeralDisciplinaId = other.notasGeralDisciplinaId == null ? null : other.notasGeralDisciplinaId.copy();
        this.resumoAcademicoId = other.resumoAcademicoId == null ? null : other.resumoAcademicoId.copy();
        this.disciplinasCurricularsId = other.disciplinasCurricularsId == null ? null : other.disciplinasCurricularsId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EstadoDisciplinaCurricularCriteria copy() {
        return new EstadoDisciplinaCurricularCriteria(this);
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

    public StringFilter getUniqueSituacaoDisciplina() {
        return uniqueSituacaoDisciplina;
    }

    public StringFilter uniqueSituacaoDisciplina() {
        if (uniqueSituacaoDisciplina == null) {
            uniqueSituacaoDisciplina = new StringFilter();
        }
        return uniqueSituacaoDisciplina;
    }

    public void setUniqueSituacaoDisciplina(StringFilter uniqueSituacaoDisciplina) {
        this.uniqueSituacaoDisciplina = uniqueSituacaoDisciplina;
    }

    public CategoriaClassificacaoFilter getClassificacao() {
        return classificacao;
    }

    public CategoriaClassificacaoFilter classificacao() {
        if (classificacao == null) {
            classificacao = new CategoriaClassificacaoFilter();
        }
        return classificacao;
    }

    public void setClassificacao(CategoriaClassificacaoFilter classificacao) {
        this.classificacao = classificacao;
    }

    public StringFilter getCodigo() {
        return codigo;
    }

    public StringFilter codigo() {
        if (codigo == null) {
            codigo = new StringFilter();
        }
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
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

    public StringFilter getCor() {
        return cor;
    }

    public StringFilter cor() {
        if (cor == null) {
            cor = new StringFilter();
        }
        return cor;
    }

    public void setCor(StringFilter cor) {
        this.cor = cor;
    }

    public DoubleFilter getValor() {
        return valor;
    }

    public DoubleFilter valor() {
        if (valor == null) {
            valor = new DoubleFilter();
        }
        return valor;
    }

    public void setValor(DoubleFilter valor) {
        this.valor = valor;
    }

    public LongFilter getEstadoDisciplinaCurricularId() {
        return estadoDisciplinaCurricularId;
    }

    public LongFilter estadoDisciplinaCurricularId() {
        if (estadoDisciplinaCurricularId == null) {
            estadoDisciplinaCurricularId = new LongFilter();
        }
        return estadoDisciplinaCurricularId;
    }

    public void setEstadoDisciplinaCurricularId(LongFilter estadoDisciplinaCurricularId) {
        this.estadoDisciplinaCurricularId = estadoDisciplinaCurricularId;
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

    public LongFilter getDisciplinasCurricularsId() {
        return disciplinasCurricularsId;
    }

    public LongFilter disciplinasCurricularsId() {
        if (disciplinasCurricularsId == null) {
            disciplinasCurricularsId = new LongFilter();
        }
        return disciplinasCurricularsId;
    }

    public void setDisciplinasCurricularsId(LongFilter disciplinasCurricularsId) {
        this.disciplinasCurricularsId = disciplinasCurricularsId;
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
        final EstadoDisciplinaCurricularCriteria that = (EstadoDisciplinaCurricularCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uniqueSituacaoDisciplina, that.uniqueSituacaoDisciplina) &&
            Objects.equals(classificacao, that.classificacao) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(cor, that.cor) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(estadoDisciplinaCurricularId, that.estadoDisciplinaCurricularId) &&
            Objects.equals(notasPeriodicaDisciplinaId, that.notasPeriodicaDisciplinaId) &&
            Objects.equals(notasGeralDisciplinaId, that.notasGeralDisciplinaId) &&
            Objects.equals(resumoAcademicoId, that.resumoAcademicoId) &&
            Objects.equals(disciplinasCurricularsId, that.disciplinasCurricularsId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            uniqueSituacaoDisciplina,
            classificacao,
            codigo,
            descricao,
            cor,
            valor,
            estadoDisciplinaCurricularId,
            notasPeriodicaDisciplinaId,
            notasGeralDisciplinaId,
            resumoAcademicoId,
            disciplinasCurricularsId,
            referenciaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoDisciplinaCurricularCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uniqueSituacaoDisciplina != null ? "uniqueSituacaoDisciplina=" + uniqueSituacaoDisciplina + ", " : "") +
            (classificacao != null ? "classificacao=" + classificacao + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (cor != null ? "cor=" + cor + ", " : "") +
            (valor != null ? "valor=" + valor + ", " : "") +
            (estadoDisciplinaCurricularId != null ? "estadoDisciplinaCurricularId=" + estadoDisciplinaCurricularId + ", " : "") +
            (notasPeriodicaDisciplinaId != null ? "notasPeriodicaDisciplinaId=" + notasPeriodicaDisciplinaId + ", " : "") +
            (notasGeralDisciplinaId != null ? "notasGeralDisciplinaId=" + notasGeralDisciplinaId + ", " : "") +
            (resumoAcademicoId != null ? "resumoAcademicoId=" + resumoAcademicoId + ", " : "") +
            (disciplinasCurricularsId != null ? "disciplinasCurricularsId=" + disciplinasCurricularsId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
