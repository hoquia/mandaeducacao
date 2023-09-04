package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.NivelEnsino} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.NivelEnsinoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nivel-ensinos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NivelEnsinoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering UnidadeDuracao
     */
    public static class UnidadeDuracaoFilter extends Filter<UnidadeDuracao> {

        public UnidadeDuracaoFilter() {}

        public UnidadeDuracaoFilter(UnidadeDuracaoFilter filter) {
            super(filter);
        }

        @Override
        public UnidadeDuracaoFilter copy() {
            return new UnidadeDuracaoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter nome;

    private IntegerFilter idadeMinima;

    private IntegerFilter idadeMaxima;

    private DoubleFilter duracao;

    private UnidadeDuracaoFilter unidadeDuracao;

    private IntegerFilter classeInicial;

    private IntegerFilter classeFinal;

    private IntegerFilter classeExame;

    private IntegerFilter totalDisciplina;

    private StringFilter responsavelTurno;

    private StringFilter responsavelAreaFormacao;

    private StringFilter responsavelCurso;

    private StringFilter responsavelDisciplina;

    private StringFilter responsavelTurma;

    private StringFilter responsavelGeral;

    private StringFilter responsavelPedagogico;

    private StringFilter responsavelAdministrativo;

    private StringFilter responsavelSecretariaGeral;

    private StringFilter responsavelSecretariaPedagogico;

    private StringFilter descricaoDocente;

    private StringFilter descricaoDiscente;

    private LongFilter nivelEnsinoId;

    private LongFilter areaFormacaoId;

    private LongFilter referenciaId;

    private LongFilter anoLectivosId;

    private LongFilter classesId;

    private Boolean distinct;

    public NivelEnsinoCriteria() {}

    public NivelEnsinoCriteria(NivelEnsinoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.idadeMinima = other.idadeMinima == null ? null : other.idadeMinima.copy();
        this.idadeMaxima = other.idadeMaxima == null ? null : other.idadeMaxima.copy();
        this.duracao = other.duracao == null ? null : other.duracao.copy();
        this.unidadeDuracao = other.unidadeDuracao == null ? null : other.unidadeDuracao.copy();
        this.classeInicial = other.classeInicial == null ? null : other.classeInicial.copy();
        this.classeFinal = other.classeFinal == null ? null : other.classeFinal.copy();
        this.classeExame = other.classeExame == null ? null : other.classeExame.copy();
        this.totalDisciplina = other.totalDisciplina == null ? null : other.totalDisciplina.copy();
        this.responsavelTurno = other.responsavelTurno == null ? null : other.responsavelTurno.copy();
        this.responsavelAreaFormacao = other.responsavelAreaFormacao == null ? null : other.responsavelAreaFormacao.copy();
        this.responsavelCurso = other.responsavelCurso == null ? null : other.responsavelCurso.copy();
        this.responsavelDisciplina = other.responsavelDisciplina == null ? null : other.responsavelDisciplina.copy();
        this.responsavelTurma = other.responsavelTurma == null ? null : other.responsavelTurma.copy();
        this.responsavelGeral = other.responsavelGeral == null ? null : other.responsavelGeral.copy();
        this.responsavelPedagogico = other.responsavelPedagogico == null ? null : other.responsavelPedagogico.copy();
        this.responsavelAdministrativo = other.responsavelAdministrativo == null ? null : other.responsavelAdministrativo.copy();
        this.responsavelSecretariaGeral = other.responsavelSecretariaGeral == null ? null : other.responsavelSecretariaGeral.copy();
        this.responsavelSecretariaPedagogico =
            other.responsavelSecretariaPedagogico == null ? null : other.responsavelSecretariaPedagogico.copy();
        this.descricaoDocente = other.descricaoDocente == null ? null : other.descricaoDocente.copy();
        this.descricaoDiscente = other.descricaoDiscente == null ? null : other.descricaoDiscente.copy();
        this.nivelEnsinoId = other.nivelEnsinoId == null ? null : other.nivelEnsinoId.copy();
        this.areaFormacaoId = other.areaFormacaoId == null ? null : other.areaFormacaoId.copy();
        this.referenciaId = other.referenciaId == null ? null : other.referenciaId.copy();
        this.anoLectivosId = other.anoLectivosId == null ? null : other.anoLectivosId.copy();
        this.classesId = other.classesId == null ? null : other.classesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NivelEnsinoCriteria copy() {
        return new NivelEnsinoCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public IntegerFilter getIdadeMinima() {
        return idadeMinima;
    }

    public IntegerFilter idadeMinima() {
        if (idadeMinima == null) {
            idadeMinima = new IntegerFilter();
        }
        return idadeMinima;
    }

    public void setIdadeMinima(IntegerFilter idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public IntegerFilter getIdadeMaxima() {
        return idadeMaxima;
    }

    public IntegerFilter idadeMaxima() {
        if (idadeMaxima == null) {
            idadeMaxima = new IntegerFilter();
        }
        return idadeMaxima;
    }

    public void setIdadeMaxima(IntegerFilter idadeMaxima) {
        this.idadeMaxima = idadeMaxima;
    }

    public DoubleFilter getDuracao() {
        return duracao;
    }

    public DoubleFilter duracao() {
        if (duracao == null) {
            duracao = new DoubleFilter();
        }
        return duracao;
    }

    public void setDuracao(DoubleFilter duracao) {
        this.duracao = duracao;
    }

    public UnidadeDuracaoFilter getUnidadeDuracao() {
        return unidadeDuracao;
    }

    public UnidadeDuracaoFilter unidadeDuracao() {
        if (unidadeDuracao == null) {
            unidadeDuracao = new UnidadeDuracaoFilter();
        }
        return unidadeDuracao;
    }

    public void setUnidadeDuracao(UnidadeDuracaoFilter unidadeDuracao) {
        this.unidadeDuracao = unidadeDuracao;
    }

    public IntegerFilter getClasseInicial() {
        return classeInicial;
    }

    public IntegerFilter classeInicial() {
        if (classeInicial == null) {
            classeInicial = new IntegerFilter();
        }
        return classeInicial;
    }

    public void setClasseInicial(IntegerFilter classeInicial) {
        this.classeInicial = classeInicial;
    }

    public IntegerFilter getClasseFinal() {
        return classeFinal;
    }

    public IntegerFilter classeFinal() {
        if (classeFinal == null) {
            classeFinal = new IntegerFilter();
        }
        return classeFinal;
    }

    public void setClasseFinal(IntegerFilter classeFinal) {
        this.classeFinal = classeFinal;
    }

    public IntegerFilter getClasseExame() {
        return classeExame;
    }

    public IntegerFilter classeExame() {
        if (classeExame == null) {
            classeExame = new IntegerFilter();
        }
        return classeExame;
    }

    public void setClasseExame(IntegerFilter classeExame) {
        this.classeExame = classeExame;
    }

    public IntegerFilter getTotalDisciplina() {
        return totalDisciplina;
    }

    public IntegerFilter totalDisciplina() {
        if (totalDisciplina == null) {
            totalDisciplina = new IntegerFilter();
        }
        return totalDisciplina;
    }

    public void setTotalDisciplina(IntegerFilter totalDisciplina) {
        this.totalDisciplina = totalDisciplina;
    }

    public StringFilter getResponsavelTurno() {
        return responsavelTurno;
    }

    public StringFilter responsavelTurno() {
        if (responsavelTurno == null) {
            responsavelTurno = new StringFilter();
        }
        return responsavelTurno;
    }

    public void setResponsavelTurno(StringFilter responsavelTurno) {
        this.responsavelTurno = responsavelTurno;
    }

    public StringFilter getResponsavelAreaFormacao() {
        return responsavelAreaFormacao;
    }

    public StringFilter responsavelAreaFormacao() {
        if (responsavelAreaFormacao == null) {
            responsavelAreaFormacao = new StringFilter();
        }
        return responsavelAreaFormacao;
    }

    public void setResponsavelAreaFormacao(StringFilter responsavelAreaFormacao) {
        this.responsavelAreaFormacao = responsavelAreaFormacao;
    }

    public StringFilter getResponsavelCurso() {
        return responsavelCurso;
    }

    public StringFilter responsavelCurso() {
        if (responsavelCurso == null) {
            responsavelCurso = new StringFilter();
        }
        return responsavelCurso;
    }

    public void setResponsavelCurso(StringFilter responsavelCurso) {
        this.responsavelCurso = responsavelCurso;
    }

    public StringFilter getResponsavelDisciplina() {
        return responsavelDisciplina;
    }

    public StringFilter responsavelDisciplina() {
        if (responsavelDisciplina == null) {
            responsavelDisciplina = new StringFilter();
        }
        return responsavelDisciplina;
    }

    public void setResponsavelDisciplina(StringFilter responsavelDisciplina) {
        this.responsavelDisciplina = responsavelDisciplina;
    }

    public StringFilter getResponsavelTurma() {
        return responsavelTurma;
    }

    public StringFilter responsavelTurma() {
        if (responsavelTurma == null) {
            responsavelTurma = new StringFilter();
        }
        return responsavelTurma;
    }

    public void setResponsavelTurma(StringFilter responsavelTurma) {
        this.responsavelTurma = responsavelTurma;
    }

    public StringFilter getResponsavelGeral() {
        return responsavelGeral;
    }

    public StringFilter responsavelGeral() {
        if (responsavelGeral == null) {
            responsavelGeral = new StringFilter();
        }
        return responsavelGeral;
    }

    public void setResponsavelGeral(StringFilter responsavelGeral) {
        this.responsavelGeral = responsavelGeral;
    }

    public StringFilter getResponsavelPedagogico() {
        return responsavelPedagogico;
    }

    public StringFilter responsavelPedagogico() {
        if (responsavelPedagogico == null) {
            responsavelPedagogico = new StringFilter();
        }
        return responsavelPedagogico;
    }

    public void setResponsavelPedagogico(StringFilter responsavelPedagogico) {
        this.responsavelPedagogico = responsavelPedagogico;
    }

    public StringFilter getResponsavelAdministrativo() {
        return responsavelAdministrativo;
    }

    public StringFilter responsavelAdministrativo() {
        if (responsavelAdministrativo == null) {
            responsavelAdministrativo = new StringFilter();
        }
        return responsavelAdministrativo;
    }

    public void setResponsavelAdministrativo(StringFilter responsavelAdministrativo) {
        this.responsavelAdministrativo = responsavelAdministrativo;
    }

    public StringFilter getResponsavelSecretariaGeral() {
        return responsavelSecretariaGeral;
    }

    public StringFilter responsavelSecretariaGeral() {
        if (responsavelSecretariaGeral == null) {
            responsavelSecretariaGeral = new StringFilter();
        }
        return responsavelSecretariaGeral;
    }

    public void setResponsavelSecretariaGeral(StringFilter responsavelSecretariaGeral) {
        this.responsavelSecretariaGeral = responsavelSecretariaGeral;
    }

    public StringFilter getResponsavelSecretariaPedagogico() {
        return responsavelSecretariaPedagogico;
    }

    public StringFilter responsavelSecretariaPedagogico() {
        if (responsavelSecretariaPedagogico == null) {
            responsavelSecretariaPedagogico = new StringFilter();
        }
        return responsavelSecretariaPedagogico;
    }

    public void setResponsavelSecretariaPedagogico(StringFilter responsavelSecretariaPedagogico) {
        this.responsavelSecretariaPedagogico = responsavelSecretariaPedagogico;
    }

    public StringFilter getDescricaoDocente() {
        return descricaoDocente;
    }

    public StringFilter descricaoDocente() {
        if (descricaoDocente == null) {
            descricaoDocente = new StringFilter();
        }
        return descricaoDocente;
    }

    public void setDescricaoDocente(StringFilter descricaoDocente) {
        this.descricaoDocente = descricaoDocente;
    }

    public StringFilter getDescricaoDiscente() {
        return descricaoDiscente;
    }

    public StringFilter descricaoDiscente() {
        if (descricaoDiscente == null) {
            descricaoDiscente = new StringFilter();
        }
        return descricaoDiscente;
    }

    public void setDescricaoDiscente(StringFilter descricaoDiscente) {
        this.descricaoDiscente = descricaoDiscente;
    }

    public LongFilter getNivelEnsinoId() {
        return nivelEnsinoId;
    }

    public LongFilter nivelEnsinoId() {
        if (nivelEnsinoId == null) {
            nivelEnsinoId = new LongFilter();
        }
        return nivelEnsinoId;
    }

    public void setNivelEnsinoId(LongFilter nivelEnsinoId) {
        this.nivelEnsinoId = nivelEnsinoId;
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

    public LongFilter getAnoLectivosId() {
        return anoLectivosId;
    }

    public LongFilter anoLectivosId() {
        if (anoLectivosId == null) {
            anoLectivosId = new LongFilter();
        }
        return anoLectivosId;
    }

    public void setAnoLectivosId(LongFilter anoLectivosId) {
        this.anoLectivosId = anoLectivosId;
    }

    public LongFilter getClassesId() {
        return classesId;
    }

    public LongFilter classesId() {
        if (classesId == null) {
            classesId = new LongFilter();
        }
        return classesId;
    }

    public void setClassesId(LongFilter classesId) {
        this.classesId = classesId;
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
        final NivelEnsinoCriteria that = (NivelEnsinoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(idadeMinima, that.idadeMinima) &&
            Objects.equals(idadeMaxima, that.idadeMaxima) &&
            Objects.equals(duracao, that.duracao) &&
            Objects.equals(unidadeDuracao, that.unidadeDuracao) &&
            Objects.equals(classeInicial, that.classeInicial) &&
            Objects.equals(classeFinal, that.classeFinal) &&
            Objects.equals(classeExame, that.classeExame) &&
            Objects.equals(totalDisciplina, that.totalDisciplina) &&
            Objects.equals(responsavelTurno, that.responsavelTurno) &&
            Objects.equals(responsavelAreaFormacao, that.responsavelAreaFormacao) &&
            Objects.equals(responsavelCurso, that.responsavelCurso) &&
            Objects.equals(responsavelDisciplina, that.responsavelDisciplina) &&
            Objects.equals(responsavelTurma, that.responsavelTurma) &&
            Objects.equals(responsavelGeral, that.responsavelGeral) &&
            Objects.equals(responsavelPedagogico, that.responsavelPedagogico) &&
            Objects.equals(responsavelAdministrativo, that.responsavelAdministrativo) &&
            Objects.equals(responsavelSecretariaGeral, that.responsavelSecretariaGeral) &&
            Objects.equals(responsavelSecretariaPedagogico, that.responsavelSecretariaPedagogico) &&
            Objects.equals(descricaoDocente, that.descricaoDocente) &&
            Objects.equals(descricaoDiscente, that.descricaoDiscente) &&
            Objects.equals(nivelEnsinoId, that.nivelEnsinoId) &&
            Objects.equals(areaFormacaoId, that.areaFormacaoId) &&
            Objects.equals(referenciaId, that.referenciaId) &&
            Objects.equals(anoLectivosId, that.anoLectivosId) &&
            Objects.equals(classesId, that.classesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codigo,
            nome,
            idadeMinima,
            idadeMaxima,
            duracao,
            unidadeDuracao,
            classeInicial,
            classeFinal,
            classeExame,
            totalDisciplina,
            responsavelTurno,
            responsavelAreaFormacao,
            responsavelCurso,
            responsavelDisciplina,
            responsavelTurma,
            responsavelGeral,
            responsavelPedagogico,
            responsavelAdministrativo,
            responsavelSecretariaGeral,
            responsavelSecretariaPedagogico,
            descricaoDocente,
            descricaoDiscente,
            nivelEnsinoId,
            areaFormacaoId,
            referenciaId,
            anoLectivosId,
            classesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NivelEnsinoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (idadeMinima != null ? "idadeMinima=" + idadeMinima + ", " : "") +
            (idadeMaxima != null ? "idadeMaxima=" + idadeMaxima + ", " : "") +
            (duracao != null ? "duracao=" + duracao + ", " : "") +
            (unidadeDuracao != null ? "unidadeDuracao=" + unidadeDuracao + ", " : "") +
            (classeInicial != null ? "classeInicial=" + classeInicial + ", " : "") +
            (classeFinal != null ? "classeFinal=" + classeFinal + ", " : "") +
            (classeExame != null ? "classeExame=" + classeExame + ", " : "") +
            (totalDisciplina != null ? "totalDisciplina=" + totalDisciplina + ", " : "") +
            (responsavelTurno != null ? "responsavelTurno=" + responsavelTurno + ", " : "") +
            (responsavelAreaFormacao != null ? "responsavelAreaFormacao=" + responsavelAreaFormacao + ", " : "") +
            (responsavelCurso != null ? "responsavelCurso=" + responsavelCurso + ", " : "") +
            (responsavelDisciplina != null ? "responsavelDisciplina=" + responsavelDisciplina + ", " : "") +
            (responsavelTurma != null ? "responsavelTurma=" + responsavelTurma + ", " : "") +
            (responsavelGeral != null ? "responsavelGeral=" + responsavelGeral + ", " : "") +
            (responsavelPedagogico != null ? "responsavelPedagogico=" + responsavelPedagogico + ", " : "") +
            (responsavelAdministrativo != null ? "responsavelAdministrativo=" + responsavelAdministrativo + ", " : "") +
            (responsavelSecretariaGeral != null ? "responsavelSecretariaGeral=" + responsavelSecretariaGeral + ", " : "") +
            (responsavelSecretariaPedagogico != null ? "responsavelSecretariaPedagogico=" + responsavelSecretariaPedagogico + ", " : "") +
            (descricaoDocente != null ? "descricaoDocente=" + descricaoDocente + ", " : "") +
            (descricaoDiscente != null ? "descricaoDiscente=" + descricaoDiscente + ", " : "") +
            (nivelEnsinoId != null ? "nivelEnsinoId=" + nivelEnsinoId + ", " : "") +
            (areaFormacaoId != null ? "areaFormacaoId=" + areaFormacaoId + ", " : "") +
            (referenciaId != null ? "referenciaId=" + referenciaId + ", " : "") +
            (anoLectivosId != null ? "anoLectivosId=" + anoLectivosId + ", " : "") +
            (classesId != null ? "classesId=" + classesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
