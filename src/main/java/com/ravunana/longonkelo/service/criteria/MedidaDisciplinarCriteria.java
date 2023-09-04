package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.Suspensao;
import com.ravunana.longonkelo.domain.enumeration.UnidadeDuracao;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.MedidaDisciplinar} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.MedidaDisciplinarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /medida-disciplinars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedidaDisciplinarCriteria implements Serializable, Criteria {

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

    /**
     * Class for filtering Suspensao
     */
    public static class SuspensaoFilter extends Filter<Suspensao> {

        public SuspensaoFilter() {}

        public SuspensaoFilter(SuspensaoFilter filter) {
            super(filter);
        }

        @Override
        public SuspensaoFilter copy() {
            return new SuspensaoFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private UnidadeDuracaoFilter periodo;

    private SuspensaoFilter suspensao;

    private IntegerFilter tempo;

    private LongFilter categoriaOcorrenciaId;

    private Boolean distinct;

    public MedidaDisciplinarCriteria() {}

    public MedidaDisciplinarCriteria(MedidaDisciplinarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.periodo = other.periodo == null ? null : other.periodo.copy();
        this.suspensao = other.suspensao == null ? null : other.suspensao.copy();
        this.tempo = other.tempo == null ? null : other.tempo.copy();
        this.categoriaOcorrenciaId = other.categoriaOcorrenciaId == null ? null : other.categoriaOcorrenciaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MedidaDisciplinarCriteria copy() {
        return new MedidaDisciplinarCriteria(this);
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

    public UnidadeDuracaoFilter getPeriodo() {
        return periodo;
    }

    public UnidadeDuracaoFilter periodo() {
        if (periodo == null) {
            periodo = new UnidadeDuracaoFilter();
        }
        return periodo;
    }

    public void setPeriodo(UnidadeDuracaoFilter periodo) {
        this.periodo = periodo;
    }

    public SuspensaoFilter getSuspensao() {
        return suspensao;
    }

    public SuspensaoFilter suspensao() {
        if (suspensao == null) {
            suspensao = new SuspensaoFilter();
        }
        return suspensao;
    }

    public void setSuspensao(SuspensaoFilter suspensao) {
        this.suspensao = suspensao;
    }

    public IntegerFilter getTempo() {
        return tempo;
    }

    public IntegerFilter tempo() {
        if (tempo == null) {
            tempo = new IntegerFilter();
        }
        return tempo;
    }

    public void setTempo(IntegerFilter tempo) {
        this.tempo = tempo;
    }

    public LongFilter getCategoriaOcorrenciaId() {
        return categoriaOcorrenciaId;
    }

    public LongFilter categoriaOcorrenciaId() {
        if (categoriaOcorrenciaId == null) {
            categoriaOcorrenciaId = new LongFilter();
        }
        return categoriaOcorrenciaId;
    }

    public void setCategoriaOcorrenciaId(LongFilter categoriaOcorrenciaId) {
        this.categoriaOcorrenciaId = categoriaOcorrenciaId;
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
        final MedidaDisciplinarCriteria that = (MedidaDisciplinarCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(periodo, that.periodo) &&
            Objects.equals(suspensao, that.suspensao) &&
            Objects.equals(tempo, that.tempo) &&
            Objects.equals(categoriaOcorrenciaId, that.categoriaOcorrenciaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, periodo, suspensao, tempo, categoriaOcorrenciaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedidaDisciplinarCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (periodo != null ? "periodo=" + periodo + ", " : "") +
            (suspensao != null ? "suspensao=" + suspensao + ", " : "") +
            (tempo != null ? "tempo=" + tempo + ", " : "") +
            (categoriaOcorrenciaId != null ? "categoriaOcorrenciaId=" + categoriaOcorrenciaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
