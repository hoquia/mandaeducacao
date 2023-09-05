package com.ravunana.longonkelo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AplicacaoRecibo.
 */
@Entity
@Table(name = "aplicacao_recibo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AplicacaoRecibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_factura", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalFactura;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "total_pago", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPago;

    @NotNull
    @Column(name = "total_diferenca", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDiferenca;

    @Column(name = "timestamp")
    private ZonedDateTime timestamp;

    @JsonIgnoreProperties(value = { "factura", "emolumento" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ItemFactura itemFactura;

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
        value = { "aplicacoesRecibos", "anoLectivos", "utilizador", "matricula", "documentoComercial", "transacao" },
        allowSetters = true
    )
    private Recibo recibo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AplicacaoRecibo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalFactura() {
        return this.totalFactura;
    }

    public AplicacaoRecibo totalFactura(BigDecimal totalFactura) {
        this.setTotalFactura(totalFactura);
        return this;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public BigDecimal getTotalPago() {
        return this.totalPago;
    }

    public AplicacaoRecibo totalPago(BigDecimal totalPago) {
        this.setTotalPago(totalPago);
        return this;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimal getTotalDiferenca() {
        return this.totalDiferenca;
    }

    public AplicacaoRecibo totalDiferenca(BigDecimal totalDiferenca) {
        this.setTotalDiferenca(totalDiferenca);
        return this;
    }

    public void setTotalDiferenca(BigDecimal totalDiferenca) {
        this.totalDiferenca = totalDiferenca;
    }

    public ZonedDateTime getTimestamp() {
        return this.timestamp;
    }

    public AplicacaoRecibo timestamp(ZonedDateTime timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ItemFactura getItemFactura() {
        return this.itemFactura;
    }

    public void setItemFactura(ItemFactura itemFactura) {
        this.itemFactura = itemFactura;
    }

    public AplicacaoRecibo itemFactura(ItemFactura itemFactura) {
        this.setItemFactura(itemFactura);
        return this;
    }

    public Factura getFactura() {
        return this.factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public AplicacaoRecibo factura(Factura factura) {
        this.setFactura(factura);
        return this;
    }

    public Recibo getRecibo() {
        return this.recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public AplicacaoRecibo recibo(Recibo recibo) {
        this.setRecibo(recibo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AplicacaoRecibo)) {
            return false;
        }
        return id != null && id.equals(((AplicacaoRecibo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AplicacaoRecibo{" +
            "id=" + getId() +
            ", totalFactura=" + getTotalFactura() +
            ", totalPago=" + getTotalPago() +
            ", totalDiferenca=" + getTotalDiferenca() +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
