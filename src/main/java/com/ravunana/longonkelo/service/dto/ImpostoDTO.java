package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.Imposto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpostoDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    private String pais;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double taxa;

    private Boolean isRetencao;

    private String motivoDescricao;

    private String motivoCodigo;

    private LookupItemDTO tipoImposto;

    private LookupItemDTO codigoImposto;

    private LookupItemDTO motivoIsencaoCodigo;

    private LookupItemDTO motivoIsencaoDescricao;

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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Boolean getIsRetencao() {
        return isRetencao;
    }

    public void setIsRetencao(Boolean isRetencao) {
        this.isRetencao = isRetencao;
    }

    public String getMotivoDescricao() {
        return motivoDescricao;
    }

    public void setMotivoDescricao(String motivoDescricao) {
        this.motivoDescricao = motivoDescricao;
    }

    public String getMotivoCodigo() {
        return motivoCodigo;
    }

    public void setMotivoCodigo(String motivoCodigo) {
        this.motivoCodigo = motivoCodigo;
    }

    public LookupItemDTO getTipoImposto() {
        return tipoImposto;
    }

    public void setTipoImposto(LookupItemDTO tipoImposto) {
        this.tipoImposto = tipoImposto;
    }

    public LookupItemDTO getCodigoImposto() {
        return codigoImposto;
    }

    public void setCodigoImposto(LookupItemDTO codigoImposto) {
        this.codigoImposto = codigoImposto;
    }

    public LookupItemDTO getMotivoIsencaoCodigo() {
        return motivoIsencaoCodigo;
    }

    public void setMotivoIsencaoCodigo(LookupItemDTO motivoIsencaoCodigo) {
        this.motivoIsencaoCodigo = motivoIsencaoCodigo;
    }

    public LookupItemDTO getMotivoIsencaoDescricao() {
        return motivoIsencaoDescricao;
    }

    public void setMotivoIsencaoDescricao(LookupItemDTO motivoIsencaoDescricao) {
        this.motivoIsencaoDescricao = motivoIsencaoDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpostoDTO)) {
            return false;
        }

        ImpostoDTO impostoDTO = (ImpostoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, impostoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpostoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", pais='" + getPais() + "'" +
            ", taxa=" + getTaxa() +
            ", isRetencao='" + getIsRetencao() + "'" +
            ", motivoDescricao='" + getMotivoDescricao() + "'" +
            ", motivoCodigo='" + getMotivoCodigo() + "'" +
            ", tipoImposto=" + getTipoImposto() +
            ", codigoImposto=" + getCodigoImposto() +
            ", motivoIsencaoCodigo=" + getMotivoIsencaoCodigo() +
            ", motivoIsencaoDescricao=" + getMotivoIsencaoDescricao() +
            "}";
    }
}
