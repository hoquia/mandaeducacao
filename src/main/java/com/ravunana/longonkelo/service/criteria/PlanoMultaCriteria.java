package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.MetodoAplicacaoMulta;
import com.ravunana.longonkelo.domain.enumeration.MetodoAplicacaoMulta;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.PlanoMulta} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.PlanoMultaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-multas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoMultaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MetodoAplicacaoMulta
     */
    public static class MetodoAplicacaoMultaFilter extends Filter<MetodoAplicacaoMulta> {

        public MetodoAplicacaoMultaFilter() {}

        public MetodoAplicacaoMultaFilter(MetodoAplicacaoMultaFilter filter) {
            super(filter);
        }

        @Override
        public MetodoAplicacaoMultaFilter copy() {
            return new MetodoAplicacaoMultaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descricao;

    private IntegerFilter diaAplicacaoMulta;

    private MetodoAplicacaoMultaFilter metodoAplicacaoMulta;

    private BigDecimalFilter taxaMulta;

    private BooleanFilter isTaxaMultaPercentual;

    private IntegerFilter diaAplicacaoJuro;

    private MetodoAplicacaoMultaFilter metodoAplicacaoJuro;

    private BigDecimalFilter taxaJuro;

    private BooleanFilter isTaxaJuroPercentual;

    private IntegerFilter aumentarJuroEmDias;

    private BooleanFilter isAtivo;

    private LongFilter categoriaEmolumentoId;

    private LongFilter emolumentoId;

    private LongFilter precoEmolumentoId;

    private LongFilter utilizadorId;

    private Boolean distinct;

    public PlanoMultaCriteria() {}

    public PlanoMultaCriteria(PlanoMultaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.diaAplicacaoMulta = other.diaAplicacaoMulta == null ? null : other.diaAplicacaoMulta.copy();
        this.metodoAplicacaoMulta = other.metodoAplicacaoMulta == null ? null : other.metodoAplicacaoMulta.copy();
        this.taxaMulta = other.taxaMulta == null ? null : other.taxaMulta.copy();
        this.isTaxaMultaPercentual = other.isTaxaMultaPercentual == null ? null : other.isTaxaMultaPercentual.copy();
        this.diaAplicacaoJuro = other.diaAplicacaoJuro == null ? null : other.diaAplicacaoJuro.copy();
        this.metodoAplicacaoJuro = other.metodoAplicacaoJuro == null ? null : other.metodoAplicacaoJuro.copy();
        this.taxaJuro = other.taxaJuro == null ? null : other.taxaJuro.copy();
        this.isTaxaJuroPercentual = other.isTaxaJuroPercentual == null ? null : other.isTaxaJuroPercentual.copy();
        this.aumentarJuroEmDias = other.aumentarJuroEmDias == null ? null : other.aumentarJuroEmDias.copy();
        this.isAtivo = other.isAtivo == null ? null : other.isAtivo.copy();
        this.categoriaEmolumentoId = other.categoriaEmolumentoId == null ? null : other.categoriaEmolumentoId.copy();
        this.emolumentoId = other.emolumentoId == null ? null : other.emolumentoId.copy();
        this.precoEmolumentoId = other.precoEmolumentoId == null ? null : other.precoEmolumentoId.copy();
        this.utilizadorId = other.utilizadorId == null ? null : other.utilizadorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PlanoMultaCriteria copy() {
        return new PlanoMultaCriteria(this);
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

    public IntegerFilter getDiaAplicacaoMulta() {
        return diaAplicacaoMulta;
    }

    public IntegerFilter diaAplicacaoMulta() {
        if (diaAplicacaoMulta == null) {
            diaAplicacaoMulta = new IntegerFilter();
        }
        return diaAplicacaoMulta;
    }

    public void setDiaAplicacaoMulta(IntegerFilter diaAplicacaoMulta) {
        this.diaAplicacaoMulta = diaAplicacaoMulta;
    }

    public MetodoAplicacaoMultaFilter getMetodoAplicacaoMulta() {
        return metodoAplicacaoMulta;
    }

    public MetodoAplicacaoMultaFilter metodoAplicacaoMulta() {
        if (metodoAplicacaoMulta == null) {
            metodoAplicacaoMulta = new MetodoAplicacaoMultaFilter();
        }
        return metodoAplicacaoMulta;
    }

    public void setMetodoAplicacaoMulta(MetodoAplicacaoMultaFilter metodoAplicacaoMulta) {
        this.metodoAplicacaoMulta = metodoAplicacaoMulta;
    }

    public BigDecimalFilter getTaxaMulta() {
        return taxaMulta;
    }

    public BigDecimalFilter taxaMulta() {
        if (taxaMulta == null) {
            taxaMulta = new BigDecimalFilter();
        }
        return taxaMulta;
    }

    public void setTaxaMulta(BigDecimalFilter taxaMulta) {
        this.taxaMulta = taxaMulta;
    }

    public BooleanFilter getIsTaxaMultaPercentual() {
        return isTaxaMultaPercentual;
    }

    public BooleanFilter isTaxaMultaPercentual() {
        if (isTaxaMultaPercentual == null) {
            isTaxaMultaPercentual = new BooleanFilter();
        }
        return isTaxaMultaPercentual;
    }

    public void setIsTaxaMultaPercentual(BooleanFilter isTaxaMultaPercentual) {
        this.isTaxaMultaPercentual = isTaxaMultaPercentual;
    }

    public IntegerFilter getDiaAplicacaoJuro() {
        return diaAplicacaoJuro;
    }

    public IntegerFilter diaAplicacaoJuro() {
        if (diaAplicacaoJuro == null) {
            diaAplicacaoJuro = new IntegerFilter();
        }
        return diaAplicacaoJuro;
    }

    public void setDiaAplicacaoJuro(IntegerFilter diaAplicacaoJuro) {
        this.diaAplicacaoJuro = diaAplicacaoJuro;
    }

    public MetodoAplicacaoMultaFilter getMetodoAplicacaoJuro() {
        return metodoAplicacaoJuro;
    }

    public MetodoAplicacaoMultaFilter metodoAplicacaoJuro() {
        if (metodoAplicacaoJuro == null) {
            metodoAplicacaoJuro = new MetodoAplicacaoMultaFilter();
        }
        return metodoAplicacaoJuro;
    }

    public void setMetodoAplicacaoJuro(MetodoAplicacaoMultaFilter metodoAplicacaoJuro) {
        this.metodoAplicacaoJuro = metodoAplicacaoJuro;
    }

    public BigDecimalFilter getTaxaJuro() {
        return taxaJuro;
    }

    public BigDecimalFilter taxaJuro() {
        if (taxaJuro == null) {
            taxaJuro = new BigDecimalFilter();
        }
        return taxaJuro;
    }

    public void setTaxaJuro(BigDecimalFilter taxaJuro) {
        this.taxaJuro = taxaJuro;
    }

    public BooleanFilter getIsTaxaJuroPercentual() {
        return isTaxaJuroPercentual;
    }

    public BooleanFilter isTaxaJuroPercentual() {
        if (isTaxaJuroPercentual == null) {
            isTaxaJuroPercentual = new BooleanFilter();
        }
        return isTaxaJuroPercentual;
    }

    public void setIsTaxaJuroPercentual(BooleanFilter isTaxaJuroPercentual) {
        this.isTaxaJuroPercentual = isTaxaJuroPercentual;
    }

    public IntegerFilter getAumentarJuroEmDias() {
        return aumentarJuroEmDias;
    }

    public IntegerFilter aumentarJuroEmDias() {
        if (aumentarJuroEmDias == null) {
            aumentarJuroEmDias = new IntegerFilter();
        }
        return aumentarJuroEmDias;
    }

    public void setAumentarJuroEmDias(IntegerFilter aumentarJuroEmDias) {
        this.aumentarJuroEmDias = aumentarJuroEmDias;
    }

    public BooleanFilter getIsAtivo() {
        return isAtivo;
    }

    public BooleanFilter isAtivo() {
        if (isAtivo == null) {
            isAtivo = new BooleanFilter();
        }
        return isAtivo;
    }

    public void setIsAtivo(BooleanFilter isAtivo) {
        this.isAtivo = isAtivo;
    }

    public LongFilter getCategoriaEmolumentoId() {
        return categoriaEmolumentoId;
    }

    public LongFilter categoriaEmolumentoId() {
        if (categoriaEmolumentoId == null) {
            categoriaEmolumentoId = new LongFilter();
        }
        return categoriaEmolumentoId;
    }

    public void setCategoriaEmolumentoId(LongFilter categoriaEmolumentoId) {
        this.categoriaEmolumentoId = categoriaEmolumentoId;
    }

    public LongFilter getEmolumentoId() {
        return emolumentoId;
    }

    public LongFilter emolumentoId() {
        if (emolumentoId == null) {
            emolumentoId = new LongFilter();
        }
        return emolumentoId;
    }

    public void setEmolumentoId(LongFilter emolumentoId) {
        this.emolumentoId = emolumentoId;
    }

    public LongFilter getPrecoEmolumentoId() {
        return precoEmolumentoId;
    }

    public LongFilter precoEmolumentoId() {
        if (precoEmolumentoId == null) {
            precoEmolumentoId = new LongFilter();
        }
        return precoEmolumentoId;
    }

    public void setPrecoEmolumentoId(LongFilter precoEmolumentoId) {
        this.precoEmolumentoId = precoEmolumentoId;
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
        final PlanoMultaCriteria that = (PlanoMultaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(diaAplicacaoMulta, that.diaAplicacaoMulta) &&
            Objects.equals(metodoAplicacaoMulta, that.metodoAplicacaoMulta) &&
            Objects.equals(taxaMulta, that.taxaMulta) &&
            Objects.equals(isTaxaMultaPercentual, that.isTaxaMultaPercentual) &&
            Objects.equals(diaAplicacaoJuro, that.diaAplicacaoJuro) &&
            Objects.equals(metodoAplicacaoJuro, that.metodoAplicacaoJuro) &&
            Objects.equals(taxaJuro, that.taxaJuro) &&
            Objects.equals(isTaxaJuroPercentual, that.isTaxaJuroPercentual) &&
            Objects.equals(aumentarJuroEmDias, that.aumentarJuroEmDias) &&
            Objects.equals(isAtivo, that.isAtivo) &&
            Objects.equals(categoriaEmolumentoId, that.categoriaEmolumentoId) &&
            Objects.equals(emolumentoId, that.emolumentoId) &&
            Objects.equals(precoEmolumentoId, that.precoEmolumentoId) &&
            Objects.equals(utilizadorId, that.utilizadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            descricao,
            diaAplicacaoMulta,
            metodoAplicacaoMulta,
            taxaMulta,
            isTaxaMultaPercentual,
            diaAplicacaoJuro,
            metodoAplicacaoJuro,
            taxaJuro,
            isTaxaJuroPercentual,
            aumentarJuroEmDias,
            isAtivo,
            categoriaEmolumentoId,
            emolumentoId,
            precoEmolumentoId,
            utilizadorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoMultaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (diaAplicacaoMulta != null ? "diaAplicacaoMulta=" + diaAplicacaoMulta + ", " : "") +
            (metodoAplicacaoMulta != null ? "metodoAplicacaoMulta=" + metodoAplicacaoMulta + ", " : "") +
            (taxaMulta != null ? "taxaMulta=" + taxaMulta + ", " : "") +
            (isTaxaMultaPercentual != null ? "isTaxaMultaPercentual=" + isTaxaMultaPercentual + ", " : "") +
            (diaAplicacaoJuro != null ? "diaAplicacaoJuro=" + diaAplicacaoJuro + ", " : "") +
            (metodoAplicacaoJuro != null ? "metodoAplicacaoJuro=" + metodoAplicacaoJuro + ", " : "") +
            (taxaJuro != null ? "taxaJuro=" + taxaJuro + ", " : "") +
            (isTaxaJuroPercentual != null ? "isTaxaJuroPercentual=" + isTaxaJuroPercentual + ", " : "") +
            (aumentarJuroEmDias != null ? "aumentarJuroEmDias=" + aumentarJuroEmDias + ", " : "") +
            (isAtivo != null ? "isAtivo=" + isAtivo + ", " : "") +
            (categoriaEmolumentoId != null ? "categoriaEmolumentoId=" + categoriaEmolumentoId + ", " : "") +
            (emolumentoId != null ? "emolumentoId=" + emolumentoId + ", " : "") +
            (precoEmolumentoId != null ? "precoEmolumentoId=" + precoEmolumentoId + ", " : "") +
            (utilizadorId != null ? "utilizadorId=" + utilizadorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
