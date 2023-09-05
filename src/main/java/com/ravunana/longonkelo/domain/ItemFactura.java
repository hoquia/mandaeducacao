package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ItemFactura.
 */
@Entity
@Table(name = "item_factura")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemFactura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "quantidade", nullable = false)
    private Double quantidade;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "preco_unitario", precision = 21, scale = 2, nullable = false)
    private BigDecimal precoUnitario;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "desconto", precision = 21, scale = 2, nullable = false)
    private BigDecimal desconto;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "multa", precision = 21, scale = 2, nullable = false)
    private BigDecimal multa;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "juro", precision = 21, scale = 2, nullable = false)
    private BigDecimal juro;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "preco_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal precoTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoItemFactura estado;

    @NotNull
    @Size(max = 3)
    @Column(name = "tax_type", length = 3, nullable = false)
    private String taxType;

    @NotNull
    @Size(max = 6)
    @Column(name = "tax_country_region", length = 6, nullable = false)
    private String taxCountryRegion;

    @NotNull
    @Size(max = 10)
    @Column(name = "tax_code", length = 10, nullable = false)
    private String taxCode;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "tax_percentage")
    private Double taxPercentage;

    @Size(max = 60)
    @Column(name = "tax_exemption_reason", length = 60)
    private String taxExemptionReason;

    @Size(max = 3)
    @Column(name = "tax_exemption_code", length = 3)
    private String taxExemptionCode;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "facturas",
            "itemsFacturas",
            "aplicacoesFacturas",
            "resumosImpostos",
            "anoLectivos",
            "utilizador",
            "motivoAnulacao",
            "matricula",
            "referencia",
            "documentoComercial",
        },
        allowSetters = true
    )
    private Factura factura;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "itemFacturas", "emolumentos", "precosEmolumentos", "categoria", "imposto", "referencia", "planoMulta" },
        allowSetters = true
    )
    private Emolumento emolumento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemFactura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return this.quantidade;
    }

    public ItemFactura quantidade(Double quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return this.precoUnitario;
    }

    public ItemFactura precoUnitario(BigDecimal precoUnitario) {
        this.setPrecoUnitario(precoUnitario);
        return this;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getDesconto() {
        return this.desconto;
    }

    public ItemFactura desconto(BigDecimal desconto) {
        this.setDesconto(desconto);
        return this;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getMulta() {
        return this.multa;
    }

    public ItemFactura multa(BigDecimal multa) {
        this.setMulta(multa);
        return this;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public BigDecimal getJuro() {
        return this.juro;
    }

    public ItemFactura juro(BigDecimal juro) {
        this.setJuro(juro);
        return this;
    }

    public void setJuro(BigDecimal juro) {
        this.juro = juro;
    }

    public BigDecimal getPrecoTotal() {
        return this.precoTotal;
    }

    public ItemFactura precoTotal(BigDecimal precoTotal) {
        this.setPrecoTotal(precoTotal);
        return this;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public EstadoItemFactura getEstado() {
        return this.estado;
    }

    public ItemFactura estado(EstadoItemFactura estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoItemFactura estado) {
        this.estado = estado;
    }

    public String getTaxType() {
        return this.taxType;
    }

    public ItemFactura taxType(String taxType) {
        this.setTaxType(taxType);
        return this;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxCountryRegion() {
        return this.taxCountryRegion;
    }

    public ItemFactura taxCountryRegion(String taxCountryRegion) {
        this.setTaxCountryRegion(taxCountryRegion);
        return this;
    }

    public void setTaxCountryRegion(String taxCountryRegion) {
        this.taxCountryRegion = taxCountryRegion;
    }

    public String getTaxCode() {
        return this.taxCode;
    }

    public ItemFactura taxCode(String taxCode) {
        this.setTaxCode(taxCode);
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Double getTaxPercentage() {
        return this.taxPercentage;
    }

    public ItemFactura taxPercentage(Double taxPercentage) {
        this.setTaxPercentage(taxPercentage);
        return this;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getTaxExemptionReason() {
        return this.taxExemptionReason;
    }

    public ItemFactura taxExemptionReason(String taxExemptionReason) {
        this.setTaxExemptionReason(taxExemptionReason);
        return this;
    }

    public void setTaxExemptionReason(String taxExemptionReason) {
        this.taxExemptionReason = taxExemptionReason;
    }

    public String getTaxExemptionCode() {
        return this.taxExemptionCode;
    }

    public ItemFactura taxExemptionCode(String taxExemptionCode) {
        this.setTaxExemptionCode(taxExemptionCode);
        return this;
    }

    public void setTaxExemptionCode(String taxExemptionCode) {
        this.taxExemptionCode = taxExemptionCode;
    }

    public Factura getFactura() {
        return this.factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public ItemFactura factura(Factura factura) {
        this.setFactura(factura);
        return this;
    }

    public Emolumento getEmolumento() {
        return this.emolumento;
    }

    public void setEmolumento(Emolumento emolumento) {
        this.emolumento = emolumento;
    }

    public ItemFactura emolumento(Emolumento emolumento) {
        this.setEmolumento(emolumento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemFactura)) {
            return false;
        }
        return id != null && id.equals(((ItemFactura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemFactura{" +
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
            "}";
    }
}
