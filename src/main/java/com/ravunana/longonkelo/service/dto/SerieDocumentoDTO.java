package com.ravunana.longonkelo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ravunana.longonkelo.domain.SerieDocumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SerieDocumentoDTO implements Serializable {

    private Long id;

    private Integer anoFiscal;

    @NotNull
    @Min(value = 0)
    private Integer versao;

    @NotNull
    private String serie;

    private Boolean isAtivo;

    private Boolean isPadrao;

    private DocumentoComercialDTO tipoDocumento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnoFiscal() {
        return anoFiscal;
    }

    public void setAnoFiscal(Integer anoFiscal) {
        this.anoFiscal = anoFiscal;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Boolean getIsAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(Boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public Boolean getIsPadrao() {
        return isPadrao;
    }

    public void setIsPadrao(Boolean isPadrao) {
        this.isPadrao = isPadrao;
    }

    public DocumentoComercialDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(DocumentoComercialDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SerieDocumentoDTO)) {
            return false;
        }

        SerieDocumentoDTO serieDocumentoDTO = (SerieDocumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serieDocumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SerieDocumentoDTO{" +
            "id=" + getId() +
            ", anoFiscal=" + getAnoFiscal() +
            ", versao=" + getVersao() +
            ", serie='" + getSerie() + "'" +
            ", isAtivo='" + getIsAtivo() + "'" +
            ", isPadrao='" + getIsPadrao() + "'" +
            ", tipoDocumento=" + getTipoDocumento() +
            "}";
    }
}
