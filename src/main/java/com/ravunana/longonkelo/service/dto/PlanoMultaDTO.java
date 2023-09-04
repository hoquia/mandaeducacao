package com.ravunana.longonkelo.service.dto;

import com.ravunana.longonkelo.domain.enumeration.MetodoAplicacaoMulta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.PlanoMulta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoMultaDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    @Min(value = 1)
    @Max(value = 31)
    private Integer diaAplicacaoMulta;

    @NotNull
    private MetodoAplicacaoMulta metodoAplicacaoMulta;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal taxaMulta;

    private Boolean isTaxaMultaPercentual;

    @Min(value = 1)
    @Max(value = 31)
    private Integer diaAplicacaoJuro;

    private MetodoAplicacaoMulta metodoAplicacaoJuro;

    @DecimalMin(value = "0")
    private BigDecimal taxaJuro;

    private Boolean isTaxaJuroPercentual;

    @Min(value = 0)
    private Integer aumentarJuroEmDias;

    private Boolean isAtivo;

    private UserDTO utilizador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDiaAplicacaoMulta() {
        return diaAplicacaoMulta;
    }

    public void setDiaAplicacaoMulta(Integer diaAplicacaoMulta) {
        this.diaAplicacaoMulta = diaAplicacaoMulta;
    }

    public MetodoAplicacaoMulta getMetodoAplicacaoMulta() {
        return metodoAplicacaoMulta;
    }

    public void setMetodoAplicacaoMulta(MetodoAplicacaoMulta metodoAplicacaoMulta) {
        this.metodoAplicacaoMulta = metodoAplicacaoMulta;
    }

    public BigDecimal getTaxaMulta() {
        return taxaMulta;
    }

    public void setTaxaMulta(BigDecimal taxaMulta) {
        this.taxaMulta = taxaMulta;
    }

    public Boolean getIsTaxaMultaPercentual() {
        return isTaxaMultaPercentual;
    }

    public void setIsTaxaMultaPercentual(Boolean isTaxaMultaPercentual) {
        this.isTaxaMultaPercentual = isTaxaMultaPercentual;
    }

    public Integer getDiaAplicacaoJuro() {
        return diaAplicacaoJuro;
    }

    public void setDiaAplicacaoJuro(Integer diaAplicacaoJuro) {
        this.diaAplicacaoJuro = diaAplicacaoJuro;
    }

    public MetodoAplicacaoMulta getMetodoAplicacaoJuro() {
        return metodoAplicacaoJuro;
    }

    public void setMetodoAplicacaoJuro(MetodoAplicacaoMulta metodoAplicacaoJuro) {
        this.metodoAplicacaoJuro = metodoAplicacaoJuro;
    }

    public BigDecimal getTaxaJuro() {
        return taxaJuro;
    }

    public void setTaxaJuro(BigDecimal taxaJuro) {
        this.taxaJuro = taxaJuro;
    }

    public Boolean getIsTaxaJuroPercentual() {
        return isTaxaJuroPercentual;
    }

    public void setIsTaxaJuroPercentual(Boolean isTaxaJuroPercentual) {
        this.isTaxaJuroPercentual = isTaxaJuroPercentual;
    }

    public Integer getAumentarJuroEmDias() {
        return aumentarJuroEmDias;
    }

    public void setAumentarJuroEmDias(Integer aumentarJuroEmDias) {
        this.aumentarJuroEmDias = aumentarJuroEmDias;
    }

    public Boolean getIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(Boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public UserDTO getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(UserDTO utilizador) {
        this.utilizador = utilizador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoMultaDTO)) {
            return false;
        }

        PlanoMultaDTO planoMultaDTO = (PlanoMultaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planoMultaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoMultaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", diaAplicacaoMulta=" + getDiaAplicacaoMulta() +
            ", metodoAplicacaoMulta='" + getMetodoAplicacaoMulta() + "'" +
            ", taxaMulta=" + getTaxaMulta() +
            ", isTaxaMultaPercentual='" + getIsTaxaMultaPercentual() + "'" +
            ", diaAplicacaoJuro=" + getDiaAplicacaoJuro() +
            ", metodoAplicacaoJuro='" + getMetodoAplicacaoJuro() + "'" +
            ", taxaJuro=" + getTaxaJuro() +
            ", isTaxaJuroPercentual='" + getIsTaxaJuroPercentual() + "'" +
            ", aumentarJuroEmDias=" + getAumentarJuroEmDias() +
            ", isAtivo='" + getIsAtivo() + "'" +
            ", utilizador=" + getUtilizador() +
            "}";
    }
}
