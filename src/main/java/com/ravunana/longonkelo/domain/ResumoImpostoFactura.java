package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResumoImpostoFactura.
 */
@Entity
@Table(name = "vnd_resumo_imposto_venda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResumoImpostoFactura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_retencao")
    private Boolean isRetencao;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "taxa", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxa;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "incidencia", precision = 21, scale = 2, nullable = false)
    private BigDecimal incidencia;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "montante", precision = 21, scale = 2, nullable = false)
    private BigDecimal montante;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumoImpostoFactura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsRetencao() {
        return this.isRetencao;
    }

    public ResumoImpostoFactura isRetencao(Boolean isRetencao) {
        this.setIsRetencao(isRetencao);
        return this;
    }

    public void setIsRetencao(Boolean isRetencao) {
        this.isRetencao = isRetencao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ResumoImpostoFactura descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return this.tipo;
    }

    public ResumoImpostoFactura tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getTaxa() {
        return this.taxa;
    }

    public ResumoImpostoFactura taxa(BigDecimal taxa) {
        this.setTaxa(taxa);
        return this;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public BigDecimal getIncidencia() {
        return this.incidencia;
    }

    public ResumoImpostoFactura incidencia(BigDecimal incidencia) {
        this.setIncidencia(incidencia);
        return this;
    }

    public void setIncidencia(BigDecimal incidencia) {
        this.incidencia = incidencia;
    }

    public BigDecimal getMontante() {
        return this.montante;
    }

    public ResumoImpostoFactura montante(BigDecimal montante) {
        this.setMontante(montante);
        return this;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public Factura getFactura() {
        return this.factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public ResumoImpostoFactura factura(Factura factura) {
        this.setFactura(factura);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumoImpostoFactura)) {
            return false;
        }
        return id != null && id.equals(((ResumoImpostoFactura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumoImpostoFactura{" +
            "id=" + getId() +
            ", isRetencao='" + getIsRetencao() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", taxa=" + getTaxa() +
            ", incidencia=" + getIncidencia() +
            ", montante=" + getMontante() +
            "}";
    }
}
