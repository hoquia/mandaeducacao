package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.ResumoImpostoFactura} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResumoImpostoFacturaDTO implements Serializable {

    private Long id;

    private Boolean isRetencao;

    @NotNull
    private String descricao;

    @NotNull
    private String tipo;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal taxa;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal incidencia;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal montante;

    private FacturaDTO factura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsRetencao() {
        return isRetencao;
    }

    public void setIsRetencao(Boolean isRetencao) {
        this.isRetencao = isRetencao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public BigDecimal getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(BigDecimal incidencia) {
        this.incidencia = incidencia;
    }

    public BigDecimal getMontante() {
        return montante;
    }

    public void setMontante(BigDecimal montante) {
        this.montante = montante;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumoImpostoFacturaDTO)) {
            return false;
        }

        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO = (ResumoImpostoFacturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resumoImpostoFacturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumoImpostoFacturaDTO{" +
            "id=" + getId() +
            ", isRetencao='" + getIsRetencao() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", taxa=" + getTaxa() +
            ", incidencia=" + getIncidencia() +
            ", montante=" + getMontante() +
            ", factura=" + getFactura() +
            "}";
    }
}
