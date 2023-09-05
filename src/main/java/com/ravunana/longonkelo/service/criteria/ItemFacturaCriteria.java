package com.ravunana.longonkelo.service.criteria;

import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ravunana.longonkelo.domain.ItemFactura} entity. This class is used
 * in {@link com.ravunana.longonkelo.web.rest.ItemFacturaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /item-facturas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemFacturaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EstadoItemFactura
     */
    public static class EstadoItemFacturaFilter extends Filter<EstadoItemFactura> {

        public EstadoItemFacturaFilter() {}

        public EstadoItemFacturaFilter(EstadoItemFacturaFilter filter) {
            super(filter);
        }

        @Override
        public EstadoItemFacturaFilter copy() {
            return new EstadoItemFacturaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter quantidade;

    private BigDecimalFilter precoUnitario;

    private BigDecimalFilter desconto;

    private BigDecimalFilter multa;

    private BigDecimalFilter juro;

    private BigDecimalFilter precoTotal;

    private EstadoItemFacturaFilter estado;

    private StringFilter taxType;

    private StringFilter taxCountryRegion;

    private StringFilter taxCode;

    private DoubleFilter taxPercentage;

    private StringFilter taxExemptionReason;

    private StringFilter taxExemptionCode;

    private LocalDateFilter emissao;

    private LocalDateFilter expiracao;

    private IntegerFilter periodo;

    private StringFilter descricao;

    private LongFilter facturaId;

    private LongFilter emolumentoId;

    private Boolean distinct;

    public ItemFacturaCriteria() {}

    public ItemFacturaCriteria(ItemFacturaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.precoUnitario = other.precoUnitario == null ? null : other.precoUnitario.copy();
        this.desconto = other.desconto == null ? null : other.desconto.copy();
        this.multa = other.multa == null ? null : other.multa.copy();
        this.juro = other.juro == null ? null : other.juro.copy();
        this.precoTotal = other.precoTotal == null ? null : other.precoTotal.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.taxType = other.taxType == null ? null : other.taxType.copy();
        this.taxCountryRegion = other.taxCountryRegion == null ? null : other.taxCountryRegion.copy();
        this.taxCode = other.taxCode == null ? null : other.taxCode.copy();
        this.taxPercentage = other.taxPercentage == null ? null : other.taxPercentage.copy();
        this.taxExemptionReason = other.taxExemptionReason == null ? null : other.taxExemptionReason.copy();
        this.taxExemptionCode = other.taxExemptionCode == null ? null : other.taxExemptionCode.copy();
        this.emissao = other.emissao == null ? null : other.emissao.copy();
        this.expiracao = other.expiracao == null ? null : other.expiracao.copy();
        this.periodo = other.periodo == null ? null : other.periodo.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.facturaId = other.facturaId == null ? null : other.facturaId.copy();
        this.emolumentoId = other.emolumentoId == null ? null : other.emolumentoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ItemFacturaCriteria copy() {
        return new ItemFacturaCriteria(this);
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

    public DoubleFilter getQuantidade() {
        return quantidade;
    }

    public DoubleFilter quantidade() {
        if (quantidade == null) {
            quantidade = new DoubleFilter();
        }
        return quantidade;
    }

    public void setQuantidade(DoubleFilter quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimalFilter getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimalFilter precoUnitario() {
        if (precoUnitario == null) {
            precoUnitario = new BigDecimalFilter();
        }
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimalFilter precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimalFilter getDesconto() {
        return desconto;
    }

    public BigDecimalFilter desconto() {
        if (desconto == null) {
            desconto = new BigDecimalFilter();
        }
        return desconto;
    }

    public void setDesconto(BigDecimalFilter desconto) {
        this.desconto = desconto;
    }

    public BigDecimalFilter getMulta() {
        return multa;
    }

    public BigDecimalFilter multa() {
        if (multa == null) {
            multa = new BigDecimalFilter();
        }
        return multa;
    }

    public void setMulta(BigDecimalFilter multa) {
        this.multa = multa;
    }

    public BigDecimalFilter getJuro() {
        return juro;
    }

    public BigDecimalFilter juro() {
        if (juro == null) {
            juro = new BigDecimalFilter();
        }
        return juro;
    }

    public void setJuro(BigDecimalFilter juro) {
        this.juro = juro;
    }

    public BigDecimalFilter getPrecoTotal() {
        return precoTotal;
    }

    public BigDecimalFilter precoTotal() {
        if (precoTotal == null) {
            precoTotal = new BigDecimalFilter();
        }
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimalFilter precoTotal) {
        this.precoTotal = precoTotal;
    }

    public EstadoItemFacturaFilter getEstado() {
        return estado;
    }

    public EstadoItemFacturaFilter estado() {
        if (estado == null) {
            estado = new EstadoItemFacturaFilter();
        }
        return estado;
    }

    public void setEstado(EstadoItemFacturaFilter estado) {
        this.estado = estado;
    }

    public StringFilter getTaxType() {
        return taxType;
    }

    public StringFilter taxType() {
        if (taxType == null) {
            taxType = new StringFilter();
        }
        return taxType;
    }

    public void setTaxType(StringFilter taxType) {
        this.taxType = taxType;
    }

    public StringFilter getTaxCountryRegion() {
        return taxCountryRegion;
    }

    public StringFilter taxCountryRegion() {
        if (taxCountryRegion == null) {
            taxCountryRegion = new StringFilter();
        }
        return taxCountryRegion;
    }

    public void setTaxCountryRegion(StringFilter taxCountryRegion) {
        this.taxCountryRegion = taxCountryRegion;
    }

    public StringFilter getTaxCode() {
        return taxCode;
    }

    public StringFilter taxCode() {
        if (taxCode == null) {
            taxCode = new StringFilter();
        }
        return taxCode;
    }

    public void setTaxCode(StringFilter taxCode) {
        this.taxCode = taxCode;
    }

    public DoubleFilter getTaxPercentage() {
        return taxPercentage;
    }

    public DoubleFilter taxPercentage() {
        if (taxPercentage == null) {
            taxPercentage = new DoubleFilter();
        }
        return taxPercentage;
    }

    public void setTaxPercentage(DoubleFilter taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public StringFilter getTaxExemptionReason() {
        return taxExemptionReason;
    }

    public StringFilter taxExemptionReason() {
        if (taxExemptionReason == null) {
            taxExemptionReason = new StringFilter();
        }
        return taxExemptionReason;
    }

    public void setTaxExemptionReason(StringFilter taxExemptionReason) {
        this.taxExemptionReason = taxExemptionReason;
    }

    public StringFilter getTaxExemptionCode() {
        return taxExemptionCode;
    }

    public StringFilter taxExemptionCode() {
        if (taxExemptionCode == null) {
            taxExemptionCode = new StringFilter();
        }
        return taxExemptionCode;
    }

    public void setTaxExemptionCode(StringFilter taxExemptionCode) {
        this.taxExemptionCode = taxExemptionCode;
    }

    public LocalDateFilter getEmissao() {
        return emissao;
    }

    public LocalDateFilter emissao() {
        if (emissao == null) {
            emissao = new LocalDateFilter();
        }
        return emissao;
    }

    public void setEmissao(LocalDateFilter emissao) {
        this.emissao = emissao;
    }

    public LocalDateFilter getExpiracao() {
        return expiracao;
    }

    public LocalDateFilter expiracao() {
        if (expiracao == null) {
            expiracao = new LocalDateFilter();
        }
        return expiracao;
    }

    public void setExpiracao(LocalDateFilter expiracao) {
        this.expiracao = expiracao;
    }

    public IntegerFilter getPeriodo() {
        return periodo;
    }

    public IntegerFilter periodo() {
        if (periodo == null) {
            periodo = new IntegerFilter();
        }
        return periodo;
    }

    public void setPeriodo(IntegerFilter periodo) {
        this.periodo = periodo;
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

    public LongFilter getFacturaId() {
        return facturaId;
    }

    public LongFilter facturaId() {
        if (facturaId == null) {
            facturaId = new LongFilter();
        }
        return facturaId;
    }

    public void setFacturaId(LongFilter facturaId) {
        this.facturaId = facturaId;
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
        final ItemFacturaCriteria that = (ItemFacturaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(precoUnitario, that.precoUnitario) &&
            Objects.equals(desconto, that.desconto) &&
            Objects.equals(multa, that.multa) &&
            Objects.equals(juro, that.juro) &&
            Objects.equals(precoTotal, that.precoTotal) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(taxType, that.taxType) &&
            Objects.equals(taxCountryRegion, that.taxCountryRegion) &&
            Objects.equals(taxCode, that.taxCode) &&
            Objects.equals(taxPercentage, that.taxPercentage) &&
            Objects.equals(taxExemptionReason, that.taxExemptionReason) &&
            Objects.equals(taxExemptionCode, that.taxExemptionCode) &&
            Objects.equals(emissao, that.emissao) &&
            Objects.equals(expiracao, that.expiracao) &&
            Objects.equals(periodo, that.periodo) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(facturaId, that.facturaId) &&
            Objects.equals(emolumentoId, that.emolumentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            quantidade,
            precoUnitario,
            desconto,
            multa,
            juro,
            precoTotal,
            estado,
            taxType,
            taxCountryRegion,
            taxCode,
            taxPercentage,
            taxExemptionReason,
            taxExemptionCode,
            emissao,
            expiracao,
            periodo,
            descricao,
            facturaId,
            emolumentoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemFacturaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
            (precoUnitario != null ? "precoUnitario=" + precoUnitario + ", " : "") +
            (desconto != null ? "desconto=" + desconto + ", " : "") +
            (multa != null ? "multa=" + multa + ", " : "") +
            (juro != null ? "juro=" + juro + ", " : "") +
            (precoTotal != null ? "precoTotal=" + precoTotal + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (taxType != null ? "taxType=" + taxType + ", " : "") +
            (taxCountryRegion != null ? "taxCountryRegion=" + taxCountryRegion + ", " : "") +
            (taxCode != null ? "taxCode=" + taxCode + ", " : "") +
            (taxPercentage != null ? "taxPercentage=" + taxPercentage + ", " : "") +
            (taxExemptionReason != null ? "taxExemptionReason=" + taxExemptionReason + ", " : "") +
            (taxExemptionCode != null ? "taxExemptionCode=" + taxExemptionCode + ", " : "") +
            (emissao != null ? "emissao=" + emissao + ", " : "") +
            (expiracao != null ? "expiracao=" + expiracao + ", " : "") +
            (periodo != null ? "periodo=" + periodo + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (facturaId != null ? "facturaId=" + facturaId + ", " : "") +
            (emolumentoId != null ? "emolumentoId=" + emolumentoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
