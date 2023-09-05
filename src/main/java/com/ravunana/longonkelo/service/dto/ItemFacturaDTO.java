package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.ItemFactura} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemFacturaDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private Double quantidade;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precoUnitario;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal desconto;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal multa;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal juro;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precoTotal;

    @NotNull
    private EstadoItemFactura estado;

    @NotNull
    @Size(max = 3)
    private String taxType;

    @NotNull
    @Size(max = 6)
    private String taxCountryRegion;

    @NotNull
    @Size(max = 10)
    private String taxCode;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double taxPercentage;

    @Size(max = 60)
    private String taxExemptionReason;

    @Size(max = 3)
    private String taxExemptionCode;

    private LocalDate emissao;

    private LocalDate expiracao;

    private Integer periodo;

    private String descricao;

    private FacturaDTO factura;

    private EmolumentoDTO emolumento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public BigDecimal getJuro() {
        return juro;
    }

    public void setJuro(BigDecimal juro) {
        this.juro = juro;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public EstadoItemFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoItemFactura estado) {
        this.estado = estado;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxCountryRegion() {
        return taxCountryRegion;
    }

    public void setTaxCountryRegion(String taxCountryRegion) {
        this.taxCountryRegion = taxCountryRegion;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getTaxExemptionReason() {
        return taxExemptionReason;
    }

    public void setTaxExemptionReason(String taxExemptionReason) {
        this.taxExemptionReason = taxExemptionReason;
    }

    public String getTaxExemptionCode() {
        return taxExemptionCode;
    }

    public void setTaxExemptionCode(String taxExemptionCode) {
        this.taxExemptionCode = taxExemptionCode;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public LocalDate getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(LocalDate expiracao) {
        this.expiracao = expiracao;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    public EmolumentoDTO getEmolumento() {
        return emolumento;
    }

    public void setEmolumento(EmolumentoDTO emolumento) {
        this.emolumento = emolumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemFacturaDTO)) {
            return false;
        }

        ItemFacturaDTO itemFacturaDTO = (ItemFacturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemFacturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemFacturaDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", precoUnitario=" + getPrecoUnitario() +
            ", desconto=" + getDesconto() +
            ", multa=" + getMulta() +
            ", juro=" + getJuro() +
            ", precoTotal=" + getPrecoTotal() +
            ", estado='" + getEstado() + "'" +
            ", taxType='" + getTaxType() + "'" +
            ", taxCountryRegion='" + getTaxCountryRegion() + "'" +
            ", taxCode='" + getTaxCode() + "'" +
            ", taxPercentage=" + getTaxPercentage() +
            ", taxExemptionReason='" + getTaxExemptionReason() + "'" +
            ", taxExemptionCode='" + getTaxExemptionCode() + "'" +
            ", emissao='" + getEmissao() + "'" +
            ", expiracao='" + getExpiracao() + "'" +
            ", periodo=" + getPeriodo() +
            ", descricao='" + getDescricao() + "'" +
            ", factura=" + getFactura() +
            ", emolumento=" + getEmolumento() +
            "}";
    }
}
