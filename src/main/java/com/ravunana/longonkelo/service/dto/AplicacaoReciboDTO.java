package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.AplicacaoRecibo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AplicacaoReciboDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalFactura;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal totalPago;

    @NotNull
    private BigDecimal totalDiferenca;

    private ZonedDateTime timestamp;

    private ItemFacturaDTO itemFactura;

    private FacturaDTO factura;

    private ReciboDTO recibo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public BigDecimal getTotalDiferenca() {
        return totalDiferenca;
    }

    public void setTotalDiferenca(BigDecimal totalDiferenca) {
        this.totalDiferenca = totalDiferenca;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ItemFacturaDTO getItemFactura() {
        return itemFactura;
    }

    public void setItemFactura(ItemFacturaDTO itemFactura) {
        this.itemFactura = itemFactura;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    public ReciboDTO getRecibo() {
        return recibo;
    }

    public void setRecibo(ReciboDTO recibo) {
        this.recibo = recibo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AplicacaoReciboDTO)) {
            return false;
        }

        AplicacaoReciboDTO aplicacaoReciboDTO = (AplicacaoReciboDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, aplicacaoReciboDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AplicacaoReciboDTO{" +
            "id=" + getId() +
            ", totalFactura=" + getTotalFactura() +
            ", totalPago=" + getTotalPago() +
            ", totalDiferenca=" + getTotalDiferenca() +
            ", timestamp='" + getTimestamp() + "'" +
            ", itemFactura=" + getItemFactura() +
            ", factura=" + getFactura() +
            ", recibo=" + getRecibo() +
            "}";
    }
}
